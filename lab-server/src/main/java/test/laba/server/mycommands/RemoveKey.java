package test.laba.server.mycommands;

import test.laba.common.commands.AbstractCommand;
import test.laba.common.commands.ConsoleParsing;
import test.laba.common.commands.Root;

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
