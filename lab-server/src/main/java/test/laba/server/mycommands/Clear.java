package test.laba.server.mycommands;

import test.laba.server.mycommands.commands.AbstractCommand;
import test.laba.common.responses.Response;


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
