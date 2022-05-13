package test.laba.server.mycommands;

import test.laba.common.responses.BasicResponse;
import test.laba.server.BD.BDManager;
import test.laba.server.BD.BDUsersManager;
import test.laba.server.mycommands.commands.AbstractCommand;
import test.laba.common.dataClasses.Product;
import test.laba.common.responses.Response;
import test.laba.common.responses.ResponseWithError;

import java.sql.SQLException;
import java.util.Set;


/**
 * remove lower command
 */
public class RemoveLower extends AbstractCommand {
    public RemoveLower() {
        super("Remove_Lower", "удалить из коллекции все элементы, меньшие, чем заданный");
    }


    /**
     * @param root object contained collection values
     */
    public Response execute(String arg, Root root) {
        return new ResponseWithError("command can't be executed");
    }

    public Response execute(BasicResponse basicResponse, BDUsersManager bdUsersManager, BDManager bdManager, Root root) {
        Product product = ((Response) basicResponse).getProduct();
        try {
            Long id = bdUsersManager.getId(basicResponse.getLogin());
            Set<Long> keys = root.getProductsKeysWhichLessAndBelongsUser(product, id);
            System.out.println(keys);
            bdManager.removeLower(basicResponse.getLogin(), keys, root, bdUsersManager);
            root.removeIfLess(product, id);
            return new Response("command was executed");
        } catch (SQLException e) {
            return new ResponseWithError("This login is not exist");
        }

    }
}
