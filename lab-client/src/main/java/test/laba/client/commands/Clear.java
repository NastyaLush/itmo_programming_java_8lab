package test.laba.client.commands;


import test.laba.client.mainClasses.Root;

public class Clear extends AbstractCommand {

    public Clear() {
        super("Clear", "очистить коллекцию");
    }
    public void execute(Root root) {
        root.clear();
    }
}
