package test.laba.server.mycommands;

import test.laba.common.commands.AbstractCommand;
import test.laba.common.commands.Root;
import test.laba.common.dataClasses.Product;
import test.laba.common.util.Response;
import test.laba.common.util.ResponseWithError;


/**
 * remove lower command
 */
public class RemoveLower extends AbstractCommand {
    public RemoveLower() {
        super("Remove_Lower", "удалить из коллекции все элементы, меньшие, чем заданный");
    }



    /**
     *
     * @param root object contained collection values
     */
    public Response execute(String arg, Root root) {
        return new ResponseWithError("command can't be executed");
    }
    public Response execute(Product product, Root root) {
        root.removeIfLess(product);
        return new Response("command was executed");
    }
}
