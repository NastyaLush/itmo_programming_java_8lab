package test.laba.server;


import test.laba.common.IO.Colors;
import test.laba.common.exception.CreateError;
import test.laba.common.exception.VariableException;
import test.laba.common.util.Util;
import test.laba.common.util.ValidInputDate;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Server {
    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    static {
        LOGGER.setLevel(Level.ALL);
    }

    private Server() {
        LOGGER.warning("the private constructor starts");
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        LOGGER.info("the main method starts");
        if (args.length < 1) {
            LOGGER.warning(Util.giveColor(Colors.RED, "you should write port to the start"));
        } else {
            if (ValidInputDate.checkPort(args[0])) {
                Integer port = ValidInputDate.getPort(args[0]);
                ServerApp serverApp = new ServerApp(port);
                try {
                    serverApp.run();
                } catch (IOException e) {
                    LOGGER.warning(Util.giveColor(Colors.RED, "impossible to run server: " + e.getMessage()));
                } catch (VariableException | CreateError e) {
                    LOGGER.warning(Util.giveColor(Colors.RED, "impossible to run server because of mistake while downland products: " + e.getMessage()));
                } catch (SQLException e) {
                    e.printStackTrace();
                    LOGGER.warning(Util.giveColor(Colors.RED, "impossible to run server because of troubles with BD: " + e.getMessage()
                    + "please check environment variables: host, login, password and name(it's meaning name of bd)"));
                } catch (NoSuchAlgorithmException e) {
                    LOGGER.warning(Util.giveColor(Colors.RED, "impossible to run server because of impossible to execute an encryption : " + e.getMessage()));
                }
            } else {
                LOGGER.warning(Util.giveColor(Colors.RED, "impossible to parse port, you should write number between 1 and 65535"));
            }
        }
    }
}
