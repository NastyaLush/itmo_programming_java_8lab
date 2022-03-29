package test.laba.client.commands;

import test.laba.client.console.ConsoleParsing;
import test.laba.client.dataClasses.Root;

/**
 * remove lower key command
 */
public class RemoveLowerKey extends AbstractCommand {
    private ConsoleParsing consoleParsing;
    public RemoveLowerKey(ConsoleParsing consoleParsing) {
        super("Remove_Lower_Key", "удалить из коллекции все элементы, ключ которых меньше, чем заданный");
        this.consoleParsing = consoleParsing;
    }

    /**
     * remove from the collection all items whose key is less than the specified one
     * @param arg key for comparing
     * @param root object contained collection values
     */
    public String execute(String arg, Root root) {
        Long key;
        key = consoleParsing.createKey(arg);
        root.removeIfKeyLess(key);
        return "the remove lower key was executed";
    }
}
