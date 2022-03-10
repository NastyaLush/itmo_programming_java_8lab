package test.laba.client.commands;

import test.laba.client.dataClasses.Root;

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
    public String execute(Root root) {
        return root.showCollection();
    }
}
