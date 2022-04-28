package test.laba.server.mycommands;

import test.laba.server.mycommands.commands.AbstractCommand;
import test.laba.server.Root;
import test.laba.common.dataClasses.Product;
import test.laba.common.responses.Response;
import test.laba.common.responses.ResponseWithError;


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
