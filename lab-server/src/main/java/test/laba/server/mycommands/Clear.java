package test.laba.server.mycommands;

import test.laba.common.responses.ResponseWithError;
import test.laba.server.BD.BDManager;
import test.laba.server.BD.BDUsersManager;
import test.laba.server.mycommands.commands.AbstractCommand;
import test.laba.common.responses.Response;

import java.sql.SQLException;


/**
 * clear command
 */
public class Clear extends AbstractCommand {

    public Clear() {
        super("Clear", "очистить коллекцию");
    }

    /**
     * clear collection
     *
     * @param root object contained collection values
     */
    @Override
    public Response execute(String arg, Root root) {
        return new ResponseWithError("clear can not be executed");
    }
    public Response execute(Response response, Root root, BDUsersManager bdUsersManager, BDManager bdManager) {
        try {
            bdManager.clear(bdUsersManager.getId(response.getLogin()));
            root.clear(bdUsersManager.getId(response.getLogin()));
            return new Response("the bd was cleared");
        } catch (SQLException e) {
            return new ResponseWithError("the clear can not be executed because of " + e.getMessage());
        }
    }

}
