package test.laba.client.commands;

import test.laba.client.console.FileManager;
import test.laba.client.console.SaveCollection;
import test.laba.client.mainClasses.Root;

import java.io.IOException;

public class Save extends AbstractCommand {
    private final SaveCollection saveCollection;
    public Save(SaveCollection saveCollection) {
        super("Save", "сохранить коллекцию в файл");
        this.saveCollection = saveCollection;
    }
    public void execute(Root root, FileManager fileManager) throws IOException {
        fileManager.save(root, saveCollection);
    }
}
