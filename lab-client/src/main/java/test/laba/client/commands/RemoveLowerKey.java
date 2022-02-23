package test.laba.client.commands;


import test.laba.client.console.Console;
import test.laba.client.console.ConsoleParsing;
import test.laba.client.exception.exucuteError;
import test.laba.client.mainClasses.Product;
import test.laba.client.mainClasses.Root;

import java.util.HashMap;

public class RemoveLowerKey extends AbstractCommand {
    public RemoveLowerKey(){
        super("RemoveLowerKey","удалить из коллекции все элементы, ключ которых меньше, чем заданный");
    }
    public void removeLowerKey(String arg, Root root, Console console, ConsoleParsing parsingInterface) throws exucuteError {
        Long key;
        key= parsingInterface.toLongNumber(arg,console);
        for(HashMap.Entry<Long, Product> prod: root.getProducts().entrySet()){
            if(key >prod.getKey()) root.getProducts().remove(prod.getKey());
        }
    }
}
