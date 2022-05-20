package test.laba.server.mycommands;

import test.laba.common.responses.ResponseWithError;
import test.laba.server.BD.BDManager;
import test.laba.server.mycommands.commands.AbstractCommand;
import test.laba.common.responses.Response;

import java.sql.SQLException;


/**
 * clear command
 */
public class Clear extends AbstractCommand {
    private final BDManager bdManager;

    public Clear(BDManager bdManager) {
        super("Clear", "очистить коллекцию");
        this.bdManager = bdManager;
    }

    /**
     * clear collection
     *
     * @param root object contained collection values
     */
    @Override
    public Response execute(String arg, Root root) {
        try {
            bdManager.clear();
            root.clear();
            return new Response("the bd was cleared");
        } catch (SQLException e) {
            return new ResponseWithError("the clear can not be executed because of " + e.getMessage());
        }
    }

}
