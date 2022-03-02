package test.laba.client.commands;

import test.laba.client.mainClasses.Product;
import test.laba.client.mainClasses.Root;


public class RemoveLower extends AbstractCommand {
    public RemoveLower() {
        super("Remove_Lower", "удалить из коллекции все элементы, меньшие, чем заданный");
    }
    public void execute(Product product, Root root) {
        root.removeIfLess(product);
    }
}
