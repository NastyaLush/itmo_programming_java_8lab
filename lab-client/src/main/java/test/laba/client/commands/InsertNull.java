package test.laba.client.commands;

import test.laba.client.console.ConsoleParsing;
import test.laba.client.dataClasses.Root;

/**
 * insert null command
 */
public class InsertNull extends AbstractCommand {
    public InsertNull() {
        super("Insert_Null", "добавить новый элемент с заданным ключом");
    }

    /**
     * add new product with key to collection
     * @param root object contained collection values
     * @param arg key for adding
     * @param consoleParsing object is responsible for parsing from console
     */
    public void execute(Root root, String arg, ConsoleParsing consoleParsing) {
         root.setProductWithKey(consoleParsing.parsProductFromConsole(root), consoleParsing.createKey(arg));
    }
}
