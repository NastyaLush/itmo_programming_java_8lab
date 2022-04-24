package test.laba.server;

import test.laba.common.util.Response;
import test.laba.common.util.Serealize;
import test.laba.server.mycommands.CommandsManager;

import java.io.IOException;
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
    int port;
    private CommandsManager commandsManager;

    public ServerApp(int port, CommandsManager commandsManager){
        this.commandsManager = commandsManager;
        this.port = port;
    }
    public void run() throws IOException {
        byte[] bytes = new byte[2000];
        ByteBuffer buf = ByteBuffer.wrap(bytes);
        SocketAddress addr = new InetSocketAddress(port);
        ServerSocketChannel serverSocketChannel;
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(addr);
        serverSocketChannel.configureBlocking(false);

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("server works");
            while (true) {
                int count = selector.select();
                if (count == 0) continue;

                Set keySet = selector.selectedKeys();
                Iterator iterator = keySet.iterator();

                while (iterator.hasNext()) {
                    SelectionKey selectionKey = (SelectionKey) iterator.next();
                    iterator.remove();

                    if (selectionKey.isAcceptable()) {
                        System.out.println("Got acceptable key");
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
                    if(selectionKey.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        socketChannel.read(buf);
                        if (new String(buf.array()).trim().equals("POISON_PILL")) {
                            socketChannel.close();
                            System.out.println("Not accepting client messages anymore");
                        }
                        else {
                            ByteBuffer byteBuffer = Serealize.serialize(executeCommand(buf));
                            socketChannel.write(byteBuffer);
                            buf.clear();
                            byteBuffer.clear();
                        }
                    }
                }
            }
    }

    public Response executeCommand(ByteBuffer byteBuffer) {
        Response response = Serealize.deserealize(byteBuffer);
        return new Response(commandsManager.chooseCommand(response.getMessage()));
    }

}
