package test.laba.client;

import test.laba.client.console.CommandsManager;
import test.laba.client.console.Console;
import test.laba.client.console.ConsoleParsing;
import test.laba.client.console.SaveCollection;
import test.laba.client.console.FileManager;
import test.laba.client.dataClasses.Root;


public final class Client {
    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    /**
     * the main class create for run
     * @param args
     */
    public static void main(String[] args) {
        Root root;
        Console console = new Console();
        SaveCollection saveCollection = new SaveCollection();
        ConsoleParsing parsingInterface = new ConsoleParsing(console);
        CommandsManager commandsManager = new CommandsManager(saveCollection, console);

        FileManager fileManager = new FileManager();
        root = fileManager.read(console);
        if (root != null) {
            console.interactivelyMode(root, commandsManager, fileManager, console, parsingInterface);
        }

    }
}
