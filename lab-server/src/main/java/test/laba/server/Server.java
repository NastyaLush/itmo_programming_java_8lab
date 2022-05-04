package test.laba.server;


import test.laba.common.IO.Colors;
import test.laba.common.util.Util;
import test.laba.server.mycommands.Root;
import test.laba.common.exception.VariableException;
import test.laba.common.util.ValidInputDate;
import test.laba.server.mycommands.CommandsManager;
import test.laba.server.workwithfile.FileManager;
import test.laba.server.workwithfile.Save;

import javax.xml.bind.JAXBException;
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

    public static void main(String[] args) {
        LOGGER.info("the main method starts");
        FileManager fileManager = new FileManager();
        Root root;
        try {
            if (args.length < 1) {
                LOGGER.warning(Util.giveColor(Colors.RED, "you should write port to the start"));
            } else {
                root = fileManager.read();
                CommandsManager commandsManager = new CommandsManager(root, new Save(fileManager));
                if (ValidInputDate.checkPort(args[0])) {
                    Integer port = ValidInputDate.getPort(args[0]);
                    ServerApp serverApp = new ServerApp(port, commandsManager);
                    try {
                        serverApp.run();
                    } catch (IOException e) {
                        LOGGER.warning(Util.giveColor(Colors.RED, "impossible to run server: " + e.getMessage()));
                    }
                } else {
                    LOGGER.warning(Util.giveColor(Colors.RED, "impossible to parse port, you should write number between 1 and 65535"));
                }
            }
        } catch (VariableException e) {
            LOGGER.warning(Util.giveColor(Colors.RED, "check environment variable LABA"));
        } catch (JAXBException e) {
            LOGGER.warning(Util.giveColor(Colors.RED, "Mistake while parsing"));
        } catch (IOException e) {
            LOGGER.warning(Util.giveColor(Colors.RED, "Mistake while reading file, check environment variable LABA: " + e.getMessage()));
        }


    }
}
