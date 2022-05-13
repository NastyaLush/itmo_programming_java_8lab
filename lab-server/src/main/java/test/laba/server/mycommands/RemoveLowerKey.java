package test.laba.server.mycommands;

import test.laba.server.BD.BDManager;
import test.laba.server.BD.BDUsersManager;
import test.laba.server.mycommands.commands.AbstractCommand;
import test.laba.common.responses.Response;
import test.laba.common.responses.ResponseWithError;

import java.sql.SQLException;

/**
 * remove lower key command
 */
public class RemoveLowerKey extends AbstractCommand {
    public RemoveLowerKey() {
        super("Remove_Lower_Key", "удалить из коллекции все элементы, ключ которых меньше, чем заданный");
    }


    /**
     * remove from the collection all items whose key is less than the specified one
     *
     * @param root object contained collection values
     */
    public Response execute(Response response, BDUsersManager bdUsersManager, BDManager bdManager, Root root) {
        try {
            if (bdManager.removeLowerKey(response, bdUsersManager)) {
                root.removeIfKeyLess(response.getKey(), bdUsersManager.getId(response.getLogin()));
                return new Response("the remove lower key was executed");
            } else {
                return new Response("there is no so products");
            }
        } catch (SQLException e) {
            return new ResponseWithError("the command wasn't executed because of " + e.getMessage());
        }
    }

    @Override
    public Response execute(String arguments, Root root) {
        return new ResponseWithError("the remove lower key can't execute");
    }
}
