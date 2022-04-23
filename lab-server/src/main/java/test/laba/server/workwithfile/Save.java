package test.laba.server.workwithfile;



import test.laba.common.commands.AbstractCommand;
import test.laba.common.commands.Root;

import java.io.IOException;

/**
 * save command
 */
public class Save extends AbstractCommand {
    private FileManager fileManager;
    public Save(FileManager fileManager) {
        super("Save", "сохранить коллекцию в файл");
        this.fileManager = fileManager;
    }

    /**
     * save collection in file
     * @param root object contained collection values
     * @throws IOException throws if save is impossible
     */
    public String execute(String arg, Root root) {
        try {
            fileManager.save(root);
        } catch (IOException e) {
            return "error, collection wasn't saved";
        }
        return "collection was saved";
    }


}
