package test.laba.client;


import test.laba.common.IO.Console;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class ClientApp {
    private final Console console = new Console();
    private final Scanner userScanner = new Scanner(System.in);
    private Wrapper wrapper;
    /*public void run(SocketAddress addr)  {

        SocketChannel sock;
        try {
            sock = SocketChannel.open();
            sock.connect(addr);
            ByteBuffer buf = ByteBuffer.wrap(arr);
            sock.write(buf);
            buf.clear();
            sock.read(buf);

            Scanner scanner = new Scanner(System.in);
            String s = scanner.nextLine();
            System.out.println(s);
        } catch (IOException e){
            // TODO: 15.04.2022
        }

    }*/

    public void interactivelyMode(){
        console.print("Программа в интерактивном режиме, для получения информации о возможностях, введите help");
        String answer;
        while ((answer = console.read()) != null) {
            // TODO: 16.04.2022 works bad you need to write the request twice
            System.out.println("try to read");
            wrapper.sent(answer);
            answer = wrapper.read();
            console.print(answer + "fghjk");
            if ("Thank you for using".equals(answer)) {
                break;
            }
        }
    }

    public void run(InetSocketAddress addr) throws IOException {
        try(SocketChannel socketChannel = SocketChannel.open()) {
            socketChannel.connect(addr);
            socketChannel.configureBlocking(false);
            wrapper = new Wrapper(socketChannel);
            System.out.println("client was connected");

            interactivelyMode();
        }

    }
}
