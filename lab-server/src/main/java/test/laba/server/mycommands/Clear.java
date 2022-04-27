package test.laba.server.mycommands;

import test.laba.common.commands.AbstractCommand;
import test.laba.common.commands.Root;
import test.laba.common.util.Response;


/**
 * clear command
 */
public class Clear extends AbstractCommand {

    public Clear() {
        super("Clear", "очистить коллекцию");
    }

    /**
     * clear collection
     * @param root object contained collection values
     */
    public Response execute(String arg, Root root) {
        root.clear();
        return new Response("collection cleared");
    }
}
