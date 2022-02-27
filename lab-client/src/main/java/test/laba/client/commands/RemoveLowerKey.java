package test.laba.client.commands;


import test.laba.client.console.Console;
import test.laba.client.console.ConsoleParsing;
import test.laba.client.mainClasses.Product;
import test.laba.client.mainClasses.Root;

import java.util.Iterator;
import java.util.Map;

public class RemoveLowerKey extends AbstractCommand {
    public RemoveLowerKey() {
        super("Remove_Lower_Key", "удалить из коллекции все элементы, ключ которых меньше, чем заданный");
    }
    public void execute(String arg, Root root, Console console, ConsoleParsing consoleParsing) {
        Long key;
        Iterator<Map.Entry<Long, Product>> itr = root.getProducts().entrySet().iterator();
        key = consoleParsing.toLongNumber(arg, console);

        while (itr.hasNext()) {
            Map.Entry<Long, Product> entry = itr.next();
            if (key > entry.getKey()) {
                itr.remove();
            }
        }
    }
}
