/*
package test.laba.server.workwithfile;



import test.laba.common.responses.ResponseWithError;
import test.laba.server.mycommands.commands.AbstractCommand;
import test.laba.server.mycommands.Root;
import test.laba.common.exception.ParsException;
import test.laba.common.responses.Response;
import test.laba.server.mycommands.UsersHandler;

import java.io.IOException;

*/
/**
 * save command
 *//*

public class Save extends AbstractCommand {
    private final FileManager fileManager;
    public Save(FileManager fileManager) {
        super("Save", "сохранить коллекцию в файл");
        this.fileManager = fileManager;
    }

    */
/**
     * save collection in file
     * @param root object contained collection values
     *//*

    public Response execute(String arg, Root root) {
        try {
            fileManager.save(root);
        } catch (IOException e) {
            return new ResponseWithError("error, collection wasn't saved");
        } catch (ParsException e) {
            return new ResponseWithError("error, collection wasn't saved, because of pars mistake");
        }
        return new Response("collection was saved");
    }
    public void save(UsersHandler usersHandler) throws IOException, ParsException {
        fileManager.saveUsers(usersHandler);
    }


}
*/
