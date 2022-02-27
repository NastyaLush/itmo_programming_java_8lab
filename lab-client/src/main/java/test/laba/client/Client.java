package test.laba.client;

import test.laba.client.console.CommandsManager;
import test.laba.client.console.Console;
import test.laba.client.console.ConsoleParsing;
import test.laba.client.console.SaveCollection;
import test.laba.client.console.FileManager;
import test.laba.client.mainClasses.Root;

public final class Client {
    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        Root root = new Root();
        Console console = new Console();
        SaveCollection saveCollection = new SaveCollection();
        ConsoleParsing parsingInterface = new ConsoleParsing();
        CommandsManager commandsManager = new CommandsManager(saveCollection);

        FileManager fileManager = new FileManager();
        if (fileManager.read(root, console)) {
            console.interactivelyMode(root, commandsManager, fileManager, console, parsingInterface);
        }

    }
}
