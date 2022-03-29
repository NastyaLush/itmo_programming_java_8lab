package test.laba.client.commands;

import test.laba.client.console.ConsoleParsing;
import test.laba.client.dataClasses.Root;

/**
 * remove key object
 */
public class RemoveKey extends AbstractCommand {
    private ConsoleParsing consoleParsing;
    public RemoveKey(ConsoleParsing consoleParsing) {
        super("Remove_Key", "удалить элемент из коллекции по его ключу");
        this.consoleParsing = consoleParsing;
    }

    /**
     *
     * @param arg key for searching
     * @param root object contained collection values
     */
    public String execute(String arg, Root root)  {
        Long key = consoleParsing.createKey(arg);
        root.remove(key);
        return "the element was deleted";
    }
}
