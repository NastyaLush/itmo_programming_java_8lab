package test.laba.server.mycommands;


import test.laba.common.responses.BasicResponse;
import test.laba.common.responses.RegisterResponse;
import test.laba.server.BD.BDManager;
import test.laba.server.BD.BDUsersManager;
import test.laba.server.mycommands.commands.AbstractCommand;
import test.laba.common.exception.AlreadyHaveTheseProduct;
import test.laba.common.responses.Response;
import test.laba.common.responses.ResponseWithError;

import java.sql.SQLException;

/**
 * insert null command
 */
public class InsertNull extends AbstractCommand {
    public InsertNull() {
        super("Insert_Null", "добавить новый элемент с заданным ключом");
    }

    /**
     * add new product with key to collection
     * @param root object contained collection values
     */
    public Response execute(Long key, BasicResponse basicResponse, Root root, BDManager bdManager, BDUsersManager bdUsersManager) {
        try {
            ((Response) basicResponse).getProduct().setOwnerID(bdUsersManager.getId((RegisterResponse) basicResponse));
            long id = bdManager.add(basicResponse, key);
            ((Response) basicResponse).getProduct().setId(id);
            root.setProductWithKey(key, ((Response) basicResponse).getProduct());
        } catch (AlreadyHaveTheseProduct | SQLException e) {
            System.out.println(".....");
            e.printStackTrace();
            return new ResponseWithError("impossible to execute because of: " + e.getMessage());
        }
        return new Response("insert null was executed");
    }
    public Response execute(String arg, Root root) {
        return new ResponseWithError("insert null can not be executed");
    }
}
