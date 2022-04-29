package test.laba.server.mycommands;


import test.laba.server.mycommands.commands.AbstractCommand;
import test.laba.common.dataClasses.Product;
import test.laba.common.exception.AlreadyHaveTheseProduct;
import test.laba.common.responses.Response;
import test.laba.common.responses.ResponseWithError;

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
    public Response execute(Long key, Product product, Root root) {
        try {
            product.setId(root.createID());
            root.setProductWithKey(key, product);
        } catch (AlreadyHaveTheseProduct e) {
            System.out.println(".....");
            return new ResponseWithError(e.getMessage());
        }
        return new Response("insert null was executed");
    }
    public Response execute(String arg, Root root) {
        return new ResponseWithError("insert null can not be executed");
    }
}
