package test.laba.client;


import test.laba.common.IO.Console;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClientApp {
    private final Console console = new Console();
    private final Scanner userScanner = new Scanner(System.in);
    private Wrapper wrapper;
    public void interactivelyMode(){
        console.print("Программа в интерактивном режиме, для получения информации о возможностях, введите help");
        String answer;
        while ((answer = console.read()) != null) {
            System.out.println("try to read");
            wrapper.sent(answer);
            answer = wrapper.read();
            console.print(answer );
            if ("Thank you for using".equals(answer)) {
                break;
            }
        }
    }

    public void run(String host, int port) throws IOException {

        try(Socket socket = new Socket(host, port)){
            wrapper = new Wrapper(socket);
            System.out.println("client was connected");
            interactivelyMode();
        }

    }
}
