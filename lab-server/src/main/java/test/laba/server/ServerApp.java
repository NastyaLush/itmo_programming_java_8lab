package test.laba.server;

import java.util.logging.Logger;
import test.laba.common.IO.Colors;
import test.laba.common.responses.Response;
import test.laba.common.responses.ResponseWithCollection;
import test.laba.common.IO.ObjectWrapper;
import test.laba.common.util.Util;
import test.laba.common.util.Values;
import test.laba.server.mycommands.CommandsManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;

public class ServerApp {
    public static final Logger LOGGER = Logger.getLogger(ServerApp.class.getName());
    private final int port;
    private final CommandsManager commandsManager;
    private final BufferedReader in;
    private final int capacity = 1000;


    public ServerApp(int port, CommandsManager commandsManager) {
        LOGGER.setLevel(Level.CONFIG);
        this.commandsManager = commandsManager;
        this.port = port;
        this.in = new BufferedReader(new InputStreamReader(System.in));
    }

    public void run() throws IOException {
        SocketAddress address = new InetSocketAddress(port);
        ServerSocketChannel serverSocketChannel;
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(address);
        serverSocketChannel.configureBlocking(false);

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        LOGGER.info("server works");
        interactivelyModule(selector, serverSocketChannel);
    }

    public Response executeCommand(ByteBuffer byteBuffer) throws IOException, ClassNotFoundException {
            Response response = ObjectWrapper.deserialize(byteBuffer);
            if (!response.getCommand().equals(Values.COLLECTION.toString())) {
                return commandsManager.chooseCommand(response);
            }
            response = new ResponseWithCollection(commandsManager.getCommandValues());
            return response;
    }

    public void read(SelectionKey selectionKey) throws IOException, ClassNotFoundException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer buf = readInBuf(socketChannel);
        Response response = executeCommand(buf);
        selectionKey.attach(response);
        selectionKey.interestOps(SelectionKey.OP_WRITE);
        buf.clear();
    }

    private ByteBuffer readInBuf(SocketChannel socketChannel) throws IOException {
        int newCapacity = capacity;
        ByteBuffer buf = ByteBuffer.allocate(capacity);
        while (socketChannel.read(buf) > 0) {
            newCapacity = newCapacity * 2;
            ByteBuffer byteBuffer = ByteBuffer.allocate(newCapacity);
            byteBuffer.put(buf.array());
            buf = byteBuffer.slice();
        }
        return buf;
    }

    public boolean write(SelectionKey selectionKey) throws IOException {
        Response response = (Response) selectionKey.attachment();
        String answer = response.getCommand();
        ByteBuffer byteBuffer = ObjectWrapper.serialize(selectionKey.attachment());
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        socketChannel.write(byteBuffer);
        selectionKey.interestOps(SelectionKey.OP_READ);
        byteBuffer.clear();
        return !"exit".equals(answer);
    }

    public void accept(ServerSocketChannel serverSocketChannel, Selector selector, SelectionKey selectionKey) {
        try {
            SocketChannel socket = serverSocketChannel.accept();
            socket.configureBlocking(false);
            socket.register(selector, SelectionKey.OP_READ);
            LOGGER.info("Connection from: " + socket);
        } catch (IOException e) {
            LOGGER.info("Unable to accept channel");
            //e.printStackTrace();
            selectionKey.cancel();
        }
    }

    private boolean consoleInput() throws IOException {
        boolean flag = false;
        String command;
        int isReadyConsole = System.in.available();
        if (isReadyConsole > 0) {
            try {
                command = in.readLine().trim().toLowerCase();
                switch (command) {
                    case "exit":
                        flag = true;
                        break;
                    case "save":
                        commandsManager.save();
                        LOGGER.info("Collection was saved");
                        break;

                    default:
                        LOGGER.config("There is no so command");
                }
            } catch (NullPointerException e) {
                LOGGER.warning("you write null, please repeat input ");
            }

        }
        return flag;
    }

    public boolean console(Selector selector) throws IOException {
        if (consoleInput()) {
            Set<SelectionKey> keySet = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keySet.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                selectionKey.cancel();
            }
            commandsManager.save();
            Util.toColor(Colors.GREEN, "Collection was saved\nThank you for using, goodbye");
            return false;
        }
        return true;
    }

    private void interactivelyModule(Selector selector, ServerSocketChannel serverSocketChannel) throws IOException {
        while (console(selector)) {
            int count = selector.select(1);
            if (count == 0) {
                continue;
            }
            Set<SelectionKey> keySet = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keySet.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                if (selectionKey.isAcceptable() && selectionKey.isValid()) {
                    accept(serverSocketChannel, selector, selectionKey);
                }
                if (selectionKey.isValid() && selectionKey.isReadable()) {
                    try {
                        read(selectionKey);
                    } catch (ClassNotFoundException | IOException e) {
                        LOGGER.info("The client was unconnected" + selectionKey.channel());
                        selectionKey.cancel();
                    }
                }
                if (selectionKey.isValid() && selectionKey.isWritable()) {
                    if (!write(selectionKey)) {
                        LOGGER.info("The client was unconnected" + selectionKey.channel());
                        selectionKey.cancel();
                    }
                }
                if (!selectionKey.isValid()) {
                    selectionKey.cancel();
                }
            }
        }
        selector.close();
        serverSocketChannel.close();
    }
}
