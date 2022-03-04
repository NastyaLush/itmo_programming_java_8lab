package test.laba.client.commands;

import test.laba.client.console.ConsoleParsing;
import test.laba.client.mainClasses.Root;

public class RemoveKey extends AbstractCommand {
    public RemoveKey() {
        super("Remove_Key", "удалить элемент из коллекции по его ключу");
    }
    public void execute(String arg, Root root, ConsoleParsing consoleParsing)  {
        Long key = consoleParsing.toLongNumber(arg);
        root.remove(key);
    }
}
