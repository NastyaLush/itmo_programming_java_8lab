package test.laba.client.commands;


import test.laba.client.console.Console;
import test.laba.client.mainClasses.Root;

public class Show extends AbstractCommand {
    public Show() {
        super("Show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
    }
    public void execute(Root root, Console console) {
        root.toString(console);
    }
}
