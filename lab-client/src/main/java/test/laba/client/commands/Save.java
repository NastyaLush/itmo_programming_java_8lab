package test.laba.client.commands;


import test.laba.client.console.Console;
import test.laba.client.console.ConsoleParsing;
import test.laba.client.console.FileManager;
import test.laba.client.exception.exucuteError;
import test.laba.client.mainClasses.Root;

public class Save extends AbstractCommand {
    public Save(){
        super("Save","сохранить коллекцию в файл");
    }
    public void save(Root root, FileManager fileManager, Console console, ConsoleParsing consoleParsing) throws exucuteError {
        fileManager.save(root,console,consoleParsing);
    }
}
