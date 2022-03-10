package test.laba.client.commands;

import test.laba.client.dataClasses.Product;
import test.laba.client.dataClasses.Root;

/**
 * remove lower command
 */
public class RemoveLower extends AbstractCommand {
    public RemoveLower() {
        super("Remove_Lower", "удалить из коллекции все элементы, меньшие, чем заданный");
    }

    /**
     *
     * @param product product for comparing
     * @param root object contained collection values
     */
    public void execute(Product product, Root root) {
        root.removeIfLess(product);
    }
}
