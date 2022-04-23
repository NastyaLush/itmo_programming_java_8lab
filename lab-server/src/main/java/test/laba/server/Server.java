package test.laba.server;

import test.laba.common.commands.ConsoleParsing;
import test.laba.common.commands.Root;
import test.laba.common.exception.VariableException;
import test.laba.server.mycommands.CommandsManager;
import test.laba.server.workwithfile.FileManager;

import javax.swing.*;
import java.io.IOException;

public final class Server {

    private Server() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        FileManager fileManager = new FileManager();
        ConsoleParsing consoleParsing = new ConsoleParsing();
        Root root = null;
        try {
            root = fileManager.read();
        } catch (VariableException e) {
            // TODO: 16.04.2022 error handling 
        }
        CommandsManager commandsManager = new CommandsManager(consoleParsing, root);

       // Integer port = Integer.valueOf(args[0]);
        Integer port = Integer.valueOf(1234);

        ServerApp serverApp = new ServerApp(port, commandsManager);
        try {
            serverApp.run();
        } catch (IOException e) {
            // TODO: 16.04.2022  
        }

    }
}
