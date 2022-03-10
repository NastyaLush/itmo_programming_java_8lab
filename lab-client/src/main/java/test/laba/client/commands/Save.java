package test.laba.client.commands;

import test.laba.client.console.FileManager;
import test.laba.client.console.SaveCollection;
import test.laba.client.dataClasses.Root;

import java.io.IOException;

/**
 * save command
 */
public class Save extends AbstractCommand {
    private final SaveCollection saveCollection;
    public Save(SaveCollection saveCollection) {
        super("Save", "сохранить коллекцию в файл");
        this.saveCollection = saveCollection;
    }

    /**
     * save collection in file
     * @param root object contained collection values
     * @param fileManager object for file work
     * @throws IOException throws if save is impossible
     */
    public void execute(Root root, FileManager fileManager) throws IOException {
        fileManager.save(root, saveCollection);
    }
}
