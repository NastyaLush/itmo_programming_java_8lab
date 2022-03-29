package test.laba.client.commands;

import test.laba.client.console.ConsoleParsing;
import test.laba.client.dataClasses.Product;
import test.laba.client.dataClasses.Root;

/**
 * remove lower command
 */
public class RemoveLower extends AbstractCommand {
    private ConsoleParsing consoleParsing;
    public RemoveLower(ConsoleParsing consoleParsing) {
        super("Remove_Lower", "удалить из коллекции все элементы, меньшие, чем заданный");
        this.consoleParsing = consoleParsing;
    }

    /**
     *
     * @param root object contained collection values
     */
    public String execute(String arg, Root root) {
        Product product = consoleParsing.parsProductFromConsole(root);
        root.removeIfLess(product);
        return "command was executed";
    }
}
