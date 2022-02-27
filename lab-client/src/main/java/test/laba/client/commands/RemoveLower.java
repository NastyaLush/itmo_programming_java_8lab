package test.laba.client.commands;



import test.laba.client.console.Console;
import test.laba.client.console.ConsoleParsing;
import test.laba.client.mainClasses.Product;
import test.laba.client.mainClasses.Root;

import java.util.Iterator;
import java.util.Map;

public class RemoveLower extends AbstractCommand {
    public RemoveLower() {
        super("Remove_Lower", "удалить из коллекции все элементы, меньшие, чем заданный");
    }
    public void execute(String arg, Root root, Console console, ConsoleParsing consoleParsing) {
        Long key = consoleParsing.toLongNumber(arg, console);
        Product product;
        Iterator<Map.Entry<Long, Product>> itr = root.getProducts().entrySet().iterator();

        if (root.getProducts().containsKey(key)) {
            product = root.getProducts().get(key);
            while (itr.hasNext()) {
                Map.Entry<Long, Product> entry = itr.next();
                if (product.compareTo(entry.getValue()) > 0) {
                    itr.remove();
                }
            }
        }
    }
}
