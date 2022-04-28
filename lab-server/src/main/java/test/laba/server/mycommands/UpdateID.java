package test.laba.server.mycommands;

import test.laba.server.mycommands.commands.AbstractCommand;
import test.laba.server.Root;
import test.laba.common.responses.Response;
import test.laba.common.responses.ResponseWithError;

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
    public Response execute(Response response, Root root) {
        if (response.isFlag()) {
            Long key = root.getKeyOnID(response.getKey());
            root.updateProductWithKey(key, response.getProduct());
            return new Response("the object was update");
        } else {
            Long key = response.getKey();
            root.getProductByKey(key);
            if (root.containsID(key)) {
                return new Response("this id is exists, please update product ", root.getProductByKey(root.getKeyOnID(key)));
            } else {
                return new ResponseWithError("this id is not exists");
            }
        }
    }
}


