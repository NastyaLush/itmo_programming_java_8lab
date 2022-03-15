package test.laba.client.commands;

import test.laba.client.console.ConsoleParsing;
import test.laba.client.dataClasses.Root;

/**
 * remove key object
 */
public class RemoveKey extends AbstractCommand {
    public RemoveKey() {
        super("Remove_Key", "удалить элемент из коллекции по его ключу");
    }

    /**
     *
     * @param arg key for searching
     * @param root object contained collection values
     * @param consoleParsing  object is responsible for parsing from console
     */
    public void execute(String arg, Root root, ConsoleParsing consoleParsing)  {
        Long key = consoleParsing.createKey(arg);
        root.remove(key);
    }
}
