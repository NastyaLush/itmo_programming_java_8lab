package test.laba.server;

import test.laba.common.responses.Response;
import test.laba.common.responses.ResponseWithCollection;
import test.laba.common.IO.ObjectWrapper;
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
        System.out.println("server works");
        ByteBuffer buf = ByteBuffer.allocate(capacity);
        interectiveModule(selector, serverSocketChannel, buf);
    }

    public Response executeCommand(ByteBuffer byteBuffer) throws IOException, ClassNotFoundException {

        Response response = ObjectWrapper.deserialize(byteBuffer);
        //System.out.println(response);
        if (!response.getCommand().equals(Values.COLLECTION.toString())) {
            return commandsManager.chooseCommand(response);
        }
        return new ResponseWithCollection(commandsManager.getCommandValues());
    }

    public void readAndWrite(SelectionKey selectionKey, ByteBuffer buf) throws IOException, ClassNotFoundException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        socketChannel.read(buf);
            /*if (new String(buf.array()).trim().equals("exit")) {
                selectionKey.cancel();
                System.out.println("Not accepting client messages anymore");
            } else {*/
        if (!write(socketChannel, buf)) {
            System.out.println("The client was unconnected" + socketChannel);
            socketChannel.close();
            selectionKey.cancel();
        }
        buf.clear();
    }

    public boolean write(SocketChannel socketChannel, ByteBuffer buf) throws IOException, ClassNotFoundException {
        Response response = executeCommand(buf);
        String answer = response.getCommand();
        ByteBuffer byteBuffer = ObjectWrapper.serialize(response);
        socketChannel.write(byteBuffer);

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
        int isReadyConsole = System.in.available();
        if (isReadyConsole > 0) {
            String command = in.readLine().trim().toLowerCase();
            switch (command) {
                case "exit":
                    flag = true;
                    break;
                case "save":
                    commandsManager.save();
                    System.out.println("Collection was saved");
                    break;

                default:
                    System.out.println("There is no so command");
            }
        }
        return flag;
    }

    private void interectiveModule(Selector selector, ServerSocketChannel serverSocketChannel, ByteBuffer buf) throws IOException {
        while (true) {
            if (consoleInput()) {
                Set<SelectionKey> keySet = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keySet.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    selectionKey.cancel();
                }
                commandsManager.save();
                System.out.println("Collection was saved\nThank you for using, goodbye");
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
                if (selectionKey.isAcceptable()) {
                    System.out.println("Got acceptable key");
                    accept(serverSocketChannel, selector, selectionKey);
                }
                if (selectionKey.isReadable()) {
                    try {
                        readAndWrite(selectionKey, buf);
                    } catch (ClassNotFoundException | IOException e) {
                        System.out.println("The client was unconnected" + selectionKey.channel());
                        selectionKey.cancel();
                    }
                }
                if(selectionKey.isWritable()){

                }
            }
        }
        selector.close();
        serverSocketChannel.close();
    }

}
