package test.laba.client;

import test.laba.common.IO.Colors;

import java.io.IOException;

import static test.laba.common.util.ValidInputDate.checkHost;
import static test.laba.common.util.ValidInputDate.checkPort;
import static test.laba.common.util.ValidInputDate.getPort;

public final class Client {
    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    /**
     * the main class create for run
     */
    public static void main(String[] args) {
        ClientApp clientApp = new ClientApp();
        try {
            if (checkPort(args[0]) && checkHost(args[1])) {
                clientApp.run(args[1], getPort(args[0]));
            } else {
                System.out.println(Colors.RED
                        + "Can't connect to the server, please check host address and port" + Colors.END);
            }
        } catch (IOException e) {
            System.out.println(Colors.RED
                    + "Can't connect to the server, check host address and port" + Colors.END);
            //e.printStackTrace();
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            System.out.println(Colors.RED
                    + ("impossible pars host address and port ") + Colors.END);
            //e.printStackTrace();
        }
    }
}
