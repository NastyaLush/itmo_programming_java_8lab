package test.laba.server.mycommands;

import test.laba.common.commands.AbstractCommand;
import test.laba.common.commands.Root;
import test.laba.common.util.Response;
import test.laba.common.util.ResponseWithError;

/**
 * remove key object
 */
public class RemoveKey extends AbstractCommand {
    public RemoveKey() {
        super("Remove_Key", "удалить элемент из коллекции по его ключу");
    }


    /**
     *
     * @param arg key for searching
     * @param root object contained collection values
     */
    public Response execute(String arg, Root root) {
        return new ResponseWithError("Remove_Key can not be executed");
    }
    public Response execute(Long key, Root root)  {
        root.remove(key);
        return new Response("the element was deleted");
    }
}
