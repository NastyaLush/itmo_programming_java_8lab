package test.laba.server.workwithfile;



import test.laba.server.mycommands.commands.AbstractCommand;
import test.laba.server.mycommands.Root;
import test.laba.common.exception.ParsException;
import test.laba.common.responses.Response;

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
            System.out.println("error, collection wasn't saved");
        } catch (ParsException e) {
            System.out.println(e.getMessage());
        }
        return new Response("collection was saved");
    }


}
