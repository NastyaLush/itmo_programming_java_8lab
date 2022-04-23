package test.laba.server.mycommands;

import test.laba.common.commands.AbstractCommand;
import test.laba.common.commands.Root;


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
    public String execute(String arg, Root root) {
        root.clear();
        return "collection cleared";
    }
}
