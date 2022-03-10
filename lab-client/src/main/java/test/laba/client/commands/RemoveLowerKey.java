package test.laba.client.commands;

import test.laba.client.console.ConsoleParsing;
import test.laba.client.dataClasses.Root;

/**
 * remove lower key command
 */
public class RemoveLowerKey extends AbstractCommand {
    public RemoveLowerKey() {
        super("Remove_Lower_Key", "удалить из коллекции все элементы, ключ которых меньше, чем заданный");
    }

    /**
     * remove from the collection all items whose key is less than the specified one
     * @param arg key for comparing
     * @param root object contained collection values
     * @param consoleParsing object is responsible for parsing from console
     */
    public void execute(String arg, Root root, ConsoleParsing consoleParsing) {
        Long key;
        key = consoleParsing.toLongNumber(arg);
        root.removeIfKeyLess(key);
    }
}
