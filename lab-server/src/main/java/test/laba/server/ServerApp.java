package test.laba.server;

import test.laba.common.commands.Root;
import test.laba.common.util.Response;
import test.laba.common.util.ResponseWithCollection;
import test.laba.common.util.Serealize;
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
    private CommandsManager commandsManager;
    private final BufferedReader in;


    public ServerApp(int port, CommandsManager commandsManager){
        this.commandsManager = commandsManager;
        this.port = port;
        this.in = new BufferedReader(new InputStreamReader(System.in));
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
        int count;
            while (true) {
                count =0;
                if(consolInput()){
                    Set keySet = selector.selectedKeys();
                    Iterator iterator = keySet.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = (SelectionKey) iterator.next();
                        iterator.remove();
                        selectionKey.cancel();
                    }
                    commandsManager.save();
                    System.out.println("Collection was saved");
                    System.out.println("Thank you for using, goodbye");
                    break;
                }

                count = selector.select(1);
                if (count == 0) continue;

                Set keySet = selector.selectedKeys();
                Iterator iterator = keySet.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = (SelectionKey) iterator.next();
                    iterator.remove();

                    if (selectionKey.isAcceptable()) {
                        System.out.println("Got acceptable key");
                        accept(serverSocketChannel,selector,selectionKey);
                    }
                    if(selectionKey.isReadable()) {
                        readAndWrite(selectionKey,buf);
                    }
                }
            }
            //todo after close selector channel closed?
        selector.close();
        serverSocketChannel.close();
    }

    public Response executeCommand(ByteBuffer byteBuffer, SocketChannel socketChannel) {
        Response response = Serealize.deserealize(byteBuffer);
        if(!response.getCommand().equals(Values.COLLECTION.toString())) {
            return commandsManager.chooseCommand(response, socketChannel);
        }
        return new ResponseWithCollection(commandsManager.getCommandvalues());
    }
    public void readAndWrite(SelectionKey selectionKey, ByteBuffer buf){
        try{
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            socketChannel.read(buf);
            /*if (new String(buf.array()).trim().equals("exit")) {
                selectionKey.cancel();
                System.out.println("Not accepting client messages anymore");
            } else {*/
            if(!write(socketChannel, buf)) {
                System.out.println("The cleint was unconnected" + socketChannel );
                socketChannel.close();
                selectionKey.cancel();
            }
            buf.clear();
        } catch (IOException e ){
            // TODO: 24.04.2022
        }
    }
    public boolean write(SocketChannel socketChannel, ByteBuffer buf){
        Response response = executeCommand(buf, socketChannel);
        String answer = response.getCommand();
        System.out.println("write" + answer);
        ByteBuffer byteBuffer = Serealize.serialize(response);
        try {
            socketChannel.write(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: 24.04.2022
        }
        byteBuffer.clear();
        if(answer.equals("exit")){
            return false;
        }
        return true;
    }


    public void accept(ServerSocketChannel serverSocketChannel, Selector selector, SelectionKey selectionKey){
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
    private boolean consolInput() throws IOException {
        boolean flag = false;
        if(System.in.available()>0){
            String command = in.readLine().trim().toLowerCase();
            switch (command){
                case "exit":
                    flag = true;
                    break;
                case "save": {
                    commandsManager.save();
                    System.out.println("Collection was saved");
                    break;
                }
                default:
                    System.out.println("There is no so command");
            }
        }
        return flag;
    }

}
