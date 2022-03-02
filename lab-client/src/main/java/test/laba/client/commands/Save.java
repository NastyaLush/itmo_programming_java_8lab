package test.laba.client.commands;


import test.laba.client.console.Console;
import test.laba.client.console.FileManager;
import test.laba.client.console.SaveCollection;
import test.laba.client.mainClasses.Root;

public class Save extends AbstractCommand {
    private SaveCollection saveCollection;
    public Save(SaveCollection saveCollection) {
        super("Save", "сохранить коллекцию в файл");
        this.saveCollection = saveCollection;
    }
    public void execute(Root root, FileManager fileManager, Console console)  {
        fileManager.save(root, console, saveCollection);
    }
}
