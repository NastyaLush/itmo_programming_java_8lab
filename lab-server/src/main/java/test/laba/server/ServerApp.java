package test.laba.server;

import test.laba.common.util.Response;
import test.laba.common.util.Serealize;
import test.laba.server.mycommands.CommandsManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.Iterator;

public class ServerApp {
    int port;
    private CommandsManager commandsManager;
    private final HashSet<Clients> clients = new HashSet<>();
    private static final int SOCKET_TIMEOUT = 10;

    public ServerApp(int port, CommandsManager commandsManager){
        this.commandsManager = commandsManager;
        this.port = port;
    }
   /* public static void main(String[] args) throws IOException {
        byte arr[] = new byte[10];
        int len = arr.length;
        InetAddress host;
        int port = 6789;
        SocketAddress addr = new InetSocketAddress(port);
        SocketChannel sock;
        ServerSocketChannel serv;
        serv = ServerSocketChannel.open();
        serv.bind(addr);
        int c =0;
        while (c !=5) {
            sock = serv.accept();
            ByteBuffer buf = ByteBuffer.wrap(arr);
            sock.read(buf);
            for (int j = 0; j < len; j++) {
                arr[j] *= 2;
            }
            buf.flip();
            sock.write(buf);
            c++;
        }
    }*/
    public void run() throws IOException {
        byte[] bytes = new byte[2000];
        ByteBuffer buf = ByteBuffer.wrap(bytes);
        SocketAddress addr = new InetSocketAddress(port);
        ServerSocketChannel serverSocketChannel;
        SocketChannel clientSocket;
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(addr);
        serverSocketChannel.configureBlocking(false);
        System.out.println("server works");
        while (true){
            System.out.println("cycle");
            try {
                int count = 0;
                while (true) {
                    clientSocket = serverSocketChannel.accept();
                    if(clientSocket != null){
                        System.out.println("Получено новое соединение" + clientSocket.getRemoteAddress());
                        clientSocket.configureBlocking(false);
                        clients.add(new Clients(clientSocket));
                    } else {
                       // System.out.println("work with exist channels ");
                        work();
                    }
                    /*clientSocket.read(buf);
                    ByteBuffer byteBuffer = Serealize.serialize(executeCommand(buf));
                    byteBuffer.flip();
                    clientSocket.socket().getOutputStream().write(byteBuffer.array());
                    byteBuffer.flip();
                    System.out.println("response is  send");*/
                }
            } catch (SocketTimeoutException e) {
                System.out.println("no clients for accepting");
            }
            work();
        }

        //work();
          /*  clientSocket.read(buf);
            ByteBuffer byteBuffer = Serealize.serialize(executeCommand(buf));
            byteBuffer.flip();
            clientSocket.socket().getOutputStream().write(byteBuffer.array());
            byteBuffer.flip();*/
            //System.out.println("response is  send");
        }
    public Response executeCommand(ByteBuffer byteBuffer) {
        Response response = Serealize.deserealize(byteBuffer);
        Response r = new Response(commandsManager.chooseCommand(response.getMessage()));
        return r;
    }
    public void work() {
        Iterator<Clients> iterator = clients.iterator();

        while (iterator.hasNext()) {
            Clients clients = iterator.next();

            if(clients.isResponse()){
                clients.sendMessage(executeCommand(clients.getResponse()));
                System.out.println("open");
            } else {
                //System.out.println("block");
            }

        }
    }
}
