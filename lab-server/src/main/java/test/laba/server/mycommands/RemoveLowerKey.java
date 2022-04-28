package test.laba.server.mycommands;

import test.laba.server.mycommands.commands.AbstractCommand;
import test.laba.server.Root;
import test.laba.common.responses.Response;
import test.laba.common.responses.ResponseWithError;

/**
 * remove lower key command
 */
public class RemoveLowerKey extends AbstractCommand {
    public RemoveLowerKey() {
        super("Remove_Lower_Key", "удалить из коллекции все элементы, ключ которых меньше, чем заданный");
    }



    /**
     * remove from the collection all items whose key is less than the specified one
     * @param root object contained collection values
     */
    public Response execute(Long key, Root root) {
        root.removeIfKeyLess(key);
        return new Response("the remove lower key was executed");
    }

    @Override
    public Response execute(String arguments, Root root) {
        return new ResponseWithError("the remove lower key can't execute");
    }
}
