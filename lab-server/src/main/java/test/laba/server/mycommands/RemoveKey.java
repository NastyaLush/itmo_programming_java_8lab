package test.laba.server.mycommands;

import test.laba.common.IO.Colors;
import test.laba.common.exception.WrongUsersData;
import test.laba.common.responses.BasicResponse;
import test.laba.common.util.Util;
import test.laba.server.BD.BDManager;
import test.laba.server.BD.BDUsersManager;
import test.laba.server.mycommands.commands.AbstractCommand;
import test.laba.common.responses.Response;
import test.laba.common.responses.ResponseWithError;

import java.sql.SQLException;

/**
 * remove key object
 */
public class RemoveKey extends AbstractCommand {
    public RemoveKey() {
        super("Remove_Key", "удалить элемент из коллекции по его ключу");
    }


    /**
     * @param arg  key for searching
     * @param root object contained collection values
     */
    @Override
    public Response execute(String arg, Root root) {
        return new ResponseWithError("Remove_Key can not be executed");
    }

    public Response execute(BasicResponse basicResponse, Root root, BDManager bdManager, BDUsersManager bdUsersManager) {
        long key = ((Response) basicResponse).getKey();
        try {
            if (bdManager.removeKey(basicResponse.getLogin(), ((Response) basicResponse).getKey(), root, bdUsersManager)) {
                root.remove(key);
            } else {
                return new Response(Util.giveColor(Colors.BlUE, "the element wasn't deleted"));
            }
            return new Response(Util.giveColor(Colors.BlUE, "the element was deleted"));
        } catch (SQLException | WrongUsersData e) {
            return new ResponseWithError("the element wasn't deleted because of: " + e.getMessage());
        }
    }
}
