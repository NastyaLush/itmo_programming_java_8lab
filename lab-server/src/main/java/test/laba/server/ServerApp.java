package test.laba.server;

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

public class ServerApp {
    private int port;
    private final CommandsManager commandsManager;
    private final BufferedReader in;
    private final int capacity = 10000;


    public ServerApp(int port, CommandsManager commandsManager) {
        this.commandsManager = commandsManager;
        this.port = port;
        this.in = new BufferedReader(new InputStreamReader(System.in));
    }

    public void run() throws IOException {
        SocketAddress addr = new InetSocketAddress(port);
        ServerSocketChannel serverSocketChannel;
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(addr);
        serverSocketChannel.configureBlocking(false);

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        Util.toColor(Colors.GREEN, "server works");
        interectiveModule(selector, serverSocketChannel);
    }

    public Response executeCommand(ByteBuffer byteBuffer) {

        Response response = ObjectWrapper.deserialize(byteBuffer);
        System.out.println(response);
        if (!response.getCommand().equals(Values.COLLECTION.toString())) {
            return commandsManager.chooseCommand(response);
        }
        response = new ResponseWithCollection(commandsManager.getCommandValues());
        return response;
    }

    public void read(SelectionKey selectionKey) throws IOException, ClassNotFoundException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer buf = ByteBuffer.allocate(capacity);
        socketChannel.read(buf);
        Response response = executeCommand(buf);
        selectionKey.attach(response);
        selectionKey.interestOps(SelectionKey.OP_WRITE);
        buf.clear();
    }
    /*private void readInBuf(SocketChannel socketChannel) throws IOException {
        int newCapacity = capacity;
        ByteBuffer buf = ByteBuffer.allocate(capacity);
        while(socketChannel.read(buf)>0){
            newCapacity = newCapacity*2;
            ByteBuffer byteBuffer = ByteBuffer.allocate(newCapacity);

        }
    }*/

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
            System.out.println("Connection from: " + socket);
        } catch (IOException e) {
            System.err.println("Unable to accept channel");
            e.printStackTrace();
            selectionKey.cancel();
        }
    }

    private boolean consoleInput() throws IOException {
        boolean flag = false;
        String command = null;
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
                        Util.toColor(Colors.GREEN, "Collection was saved");
                        break;

                    default:
                        Util.toColor(Colors.GREEN, "There is no so command");
                }
            } catch (NullPointerException e) {
                Util.toColor(Colors.RED, "you write null, please repeat input ");
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

    private void interectiveModule(Selector selector, ServerSocketChannel serverSocketChannel) throws IOException {
        while (true) {
            if (!console(selector)) {
                break;
            }
            int count = selector.select(1);
            if (count == 0) {
                continue;
            }
            Set keySet = selector.selectedKeys();
            Iterator iterator = keySet.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = (SelectionKey) iterator.next();
                iterator.remove();
                if (selectionKey.isAcceptable() && selectionKey.isValid()) {
                    accept(serverSocketChannel, selector, selectionKey);
                }
                if (selectionKey.isValid() && selectionKey.isReadable()) {
                    try {
                        read(selectionKey);
                    } catch (ClassNotFoundException | IOException e) {
                        Util.toColor(Colors.GREEN, "The client was unconnected" + selectionKey.channel());
                        selectionKey.cancel();
                    }
                }
                if (selectionKey.isValid() && selectionKey.isWritable()) {
                    if (!write(selectionKey)) {
                        Util.toColor(Colors.GREEN, "The client was unconnected" + selectionKey.channel());
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
