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
        int lenght = args.length;
        String port = null;
        String host = "localhost";
        switch (lenght) {
            case 1:
                port = args[0];
                break;
            case 2:
                port = args[0];
                host = args[1];
                break;
            default:
                System.out.println(Colors.RED + "Please write port and host( default it's localhost)" + Colors.END);
        }
        if (port != null) {
            try {
                if (checkPort(port) && checkHost(host)) {
                    clientApp.run(host, getPort(port));
                } else {
                    System.out.println(Colors.RED
                            + "Can't connect to the server, please check host address and port" + Colors.END);
                }
            } catch (IOException e) {
                System.out.println(Colors.RED
                        + "Can't connect to the server, check host address and port" + Colors.END);
            } catch (NumberFormatException e) {
                System.out.println(Colors.RED
                        + ("impossible pars host address and port ") + Colors.END);
            }
        }
    }
}
