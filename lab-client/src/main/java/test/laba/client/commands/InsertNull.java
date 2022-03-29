package test.laba.client.commands;

import test.laba.client.console.ConsoleParsing;
import test.laba.client.dataClasses.Root;

/**
 * insert null command
 */
public class InsertNull extends AbstractCommand {
    private ConsoleParsing consoleParsing;
    public InsertNull(ConsoleParsing consoleParsing) {
        super("Insert_Null", "добавить новый элемент с заданным ключом");
        this.consoleParsing = consoleParsing;
    }

    /**
     * add new product with key to collection
     * @param root object contained collection values
     * @param arg key for adding
     */
    public String execute(String arg, Root root) {
         root.setProductWithKey(consoleParsing.createKey(arg), consoleParsing.parsProductFromConsole(root));
         return "insert null was executed";
    }
}
