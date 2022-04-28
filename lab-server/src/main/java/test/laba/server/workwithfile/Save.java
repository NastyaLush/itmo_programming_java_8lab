package test.laba.server.workwithfile;



import test.laba.common.commands.AbstractCommand;
import test.laba.common.commands.Root;
import test.laba.common.exception.ParsException;
import test.laba.common.util.Response;
import test.laba.common.util.ResponseWithError;

import java.io.IOException;

/**
 * save command
 */
public class Save extends AbstractCommand {
    private final FileManager fileManager;
    public Save(FileManager fileManager) {
        super("Save", "сохранить коллекцию в файл");
        this.fileManager = fileManager;
    }

    /**
     * save collection in file
     * @param root object contained collection values
     */
    public Response execute(String arg, Root root) {
        try {
            fileManager.save(root);
        } catch (IOException e) {
            return new ResponseWithError("error, collection wasn't saved");
        } catch (ParsException e) {
            return new ResponseWithError(e.getMessage());
        }
        return new Response("collection was saved");
    }


}
