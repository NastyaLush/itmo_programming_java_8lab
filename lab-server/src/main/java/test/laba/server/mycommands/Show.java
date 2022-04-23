package test.laba.server.mycommands;

import test.laba.common.commands.AbstractCommand;
import test.laba.common.commands.Root;


/**
 * show command
 */
public class Show extends AbstractCommand {
    public Show() {
        super("Show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
    }

    /**
     * show collection values
     * @param root object contained collection values
     * @return string with info about values
     */
    public String execute(String arg, Root root) {
        return root.showCollection();
    }
}
