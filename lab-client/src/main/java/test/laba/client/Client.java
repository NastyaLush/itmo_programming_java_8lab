package test.laba.client;

import java.io.IOException;

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
            clientApp.run("localhost", 1234);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
