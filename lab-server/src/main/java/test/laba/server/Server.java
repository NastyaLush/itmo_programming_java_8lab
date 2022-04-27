package test.laba.server;


import test.laba.common.commands.Root;
import test.laba.common.exception.VariableException;
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
        Root root = null;
        try {
            root = fileManager.read();
            CommandsManager commandsManager = new CommandsManager( root, new Save(fileManager));

            Integer port = Integer.valueOf(1234);
            ServerApp serverApp = new ServerApp(port, commandsManager);
            try {
                serverApp.run();
            } catch (IOException e) {
                // TODO: 16.04.2022
            }
        } catch (VariableException e) {
            // TODO: 16.04.2022 error handling
        } catch (JAXBException e) {
            System.out.println("Ошибка при попытке парсинга");
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла");
        }


    }
}
