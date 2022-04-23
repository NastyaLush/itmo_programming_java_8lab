package test.laba.client;


import test.laba.common.IO.Console;
import test.laba.common.commands.ConsoleParsing;
import test.laba.server.workwithfile.FileManager;

import java.io.IOException;
import java.net.InetSocketAddress;

public final class Client {
    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    /**
     * the main class create for run
     * @param args
     */
    public static void main(String[] args) {
        ClientApp clientApp = new ClientApp();
        try{
            // InetSocketAddress addr = new InetSocketAddress(args[0], Integer.valueOf(args[1]));
            InetSocketAddress addr = new InetSocketAddress("localhost", 1234);
            clientApp.run(addr);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
