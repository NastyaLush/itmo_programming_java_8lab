package test.laba.client.commands;

import test.laba.client.mainClasses.Root;

public class Show extends AbstractCommand {
    public Show() {
        super("Show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
    }
    public String execute(Root root) {
        return root.showCollection();
    }
}
