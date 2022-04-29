package test.laba.server;


import test.laba.common.IO.Colors;
import test.laba.common.exception.VariableException;
import test.laba.common.util.Util;
import test.laba.common.util.ValidInputDate;
import test.laba.server.mycommands.CommandsManager;
import test.laba.server.workwithfile.FileManager;
import test.laba.server.workwithfile.Save;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public final class Server {

    private Server() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        FileManager fileManager = new FileManager();
        Root root;
        try {
            if (args.length < 1) {
                Util.toColor(Colors.RED, "you should write port in the start");
            } else {
                root = fileManager.read();
                CommandsManager commandsManager = new CommandsManager(root, new Save(fileManager));
                if (ValidInputDate.checkPort(args[0])) {
                    Integer port = ValidInputDate.getPort(args[0]);
                    ServerApp serverApp = new ServerApp(port, commandsManager);
                    try {
                        serverApp.run();
                    } catch (IOException e) {
                        Util.toColor(Colors.RED, "impossible to run server: " + e.getMessage());
                    }
                } else {
                    Util.toColor(Colors.RED, "impossible to parse port, you should write number between 1 and 65535");
                }
            }
        } catch (VariableException e) {
            Util.toColor(Colors.RED, "check environment variable LABA");
        } catch (JAXBException e) {
            Util.toColor(Colors.RED, "Mistake while parsing");
        } catch (IOException e) {
           Util.toColor(Colors.RED, "Mistake while reading file, check environment variable LABA: " + e.getMessage());
        }


    }
}
