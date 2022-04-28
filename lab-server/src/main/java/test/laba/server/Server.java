package test.laba.server;


import test.laba.common.IO.Colors;
import test.laba.common.commands.Root;
import test.laba.common.exception.VariableException;
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
                System.out.println(Colors.RED + "you should write port, while beginning" + Colors.END);
            } else {
                root = fileManager.read();
                CommandsManager commandsManager = new CommandsManager(root, new Save(fileManager));
                if (ValidInputDate.checkPort(args[0])) {
                    Integer port = ValidInputDate.getPort(args[0]);
                    ServerApp serverApp = new ServerApp(port, commandsManager);
                    try {
                        serverApp.run();
                    } catch (IOException e) {
                        System.out.println(Colors.RED + "impossible to run server" + Colors.END);
                        e.printStackTrace();
                    }
                } else {
                    System.out.println(Colors.RED + "impossible to parse port, you should write number between 1 and 65535" + Colors.END);
                }
            }
        } catch (VariableException e) {
            System.out.println(Colors.RED + "check environment variable" + Colors.END);
        } catch (JAXBException e) {
            System.out.println(Colors.RED + "Mistake while parsing" + Colors.END);
        } catch (IOException e) {
            System.out.println(Colors.RED + "Mistake while reading file" + Colors.END + e);
        }


    }
}
