package test.laba.server.mycommands;

import test.laba.server.BD.BDManager;
import test.laba.server.BD.BDUsersManager;
import test.laba.server.mycommands.commands.AbstractCommand;
import test.laba.common.responses.Response;
import test.laba.common.responses.ResponseWithError;

import java.sql.SQLException;

/**
 * update id command
 */
public class UpdateID extends AbstractCommand {

    /**
     * the constructor, add description and console
     */
    public UpdateID() {
        super("Update_ID", "обновить значение элемента коллекции, id которого равен заданному");
    }

    @Override
    public Response execute(String arguments, Root root) {
        return null;
    }

    /**
     * @param root object contained collection
     */
    public Response execute(Response response, Root root, BDUsersManager bdUsersManager, String login, BDManager bdManager) {
        Response answer;
        if (response.isFlagUdateID()) {
            Long productID = response.getKeyOrID();
            try {
                Long id = bdUsersManager.getId(login);
                if (root.containsIDAndBelongsToUser(productID, id)) {
                    try {
                        Long key = root.getKeyOnIDIfBelongsToUser(response.getKeyOrID(), bdUsersManager.getId(login));
                        response.getProduct().setOwnerID(id);
                        bdManager.updateID(response.getProduct(), key);
                        root.updateProductWithKey(key, response.getProduct());
                        answer = new Response("the object was update");
                    } catch (SQLException e) {
                        return new ResponseWithError("the command cannot be execute because of " + e.getMessage());
                    }
                } else {
                    answer =  new ResponseWithError("this id is not exists or product not belongs to you");
                }
            } catch (SQLException e) {
                return new ResponseWithError("the command cannot be execute because of " + e.getMessage());
            }

        } else {
            Long productID = response.getKeyOrID();
            root.getProductByKey(productID);
            try {
                Long id = bdUsersManager.getId(login);
                if (root.containsIDAndBelongsToUser(productID, id)) {
                    answer =  new Response("this id is exists and product belongs to you, please update product ",
                            root.getProductByKey(root.getKeyOnIDIfBelongsToUser(productID, id)));
                } else {
                    answer =  new ResponseWithError("this id is not exists or product not belongs to you");
                }
            } catch (SQLException e) {
                return new ResponseWithError("the command cannot be execute because of " + e.getMessage());
            }
        }
        return answer;
    }
}


