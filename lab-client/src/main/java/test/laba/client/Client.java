package test.laba.client;

import test.laba.common.IO.Colors;
import test.laba.common.util.Util;

import java.util.logging.Logger;

import java.io.IOException;
import java.util.logging.Level;

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
        final Logger logger = Logger.getLogger(Client.class.getName());
        logger.setLevel(Level.CONFIG);
        logger.info(Util.giveColor(Colors.BlUE, "the main method starts"));
        ClientApp clientApp = new ClientApp();
        int length = args.length;
        String port = null;
        String host = "localhost";
        switch (length) {
            case 1:
                port = args[0];
                break;
            case 2:
                port = args[0];
                host = args[1];
                break;
            default:
                logger.severe(Util.giveColor(Colors.RED, "Please write port and host( default it's localhost)"));
        }
        if (port != null) {
            try {
                if (checkPort(port) && checkHost(host)) {
                    clientApp.run(host, getPort(port));
                } else {
                    logger.severe(Util.giveColor(Colors.RED, "Can't connect to the server, please check host address and port"));
                }
            } catch (IOException e) {
                logger.severe(Util.giveColor(Colors.RED, "Can't connect to the server, check host address and port"));
            } catch (NumberFormatException e) {
                logger.severe(Util.giveColor(Colors.RED, "impossible pars host address and port "));
            }
        }
    }
}
