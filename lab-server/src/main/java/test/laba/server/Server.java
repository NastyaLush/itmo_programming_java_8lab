package test.laba.server;


import test.laba.common.IO.Colors;
import test.laba.common.util.Util;
import test.laba.common.util.ValidInputDate;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Server {
    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    static {
        LOGGER.setLevel(Level.CONFIG);
    }

    private Server() {
        LOGGER.warning("the private constructor starts");
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) throws IOException {
        LOGGER.info("the main method starts");
        // try {
            if (args.length < 1) {
                LOGGER.warning(Util.giveColor(Colors.RED, "you should write port to the start"));
            } else {
                // TODOoo: 10.05.2022 change name users
                if (ValidInputDate.checkPort(args[0])) {
                    Integer port = ValidInputDate.getPort(args[0]);
                    ServerApp serverApp = new ServerApp(port);
                    try {
                        serverApp.run();
                    } catch (IOException e) {
                        LOGGER.warning(Util.giveColor(Colors.RED, "impossible to run server: " + e.getMessage()));
                    }
                } else {
                    LOGGER.warning(Util.giveColor(Colors.RED, "impossible to parse port, you should write number between 1 and 65535"));
                }
            }
        /*} *//*catch (VariableException e) {
            LOGGER.warning(Util.giveColor(Colors.RED, "check environment variable LABA"));
        } catch (JAXBException e) {
            e.printStackTrace();
            LOGGER.warning(Util.giveColor(Colors.RED, "Mistake while parsing"));
        } catch (IOException e) {
            LOGGER.warning(Util.giveColor(Colors.RED, "Mistake while reading file, check environment variable LABA: " + e.getMessage()));
        }*/


    }
}
