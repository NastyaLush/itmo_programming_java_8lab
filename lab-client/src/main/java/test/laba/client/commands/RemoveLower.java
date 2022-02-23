package test.laba.client.commands;



import test.laba.client.console.Console;
import test.laba.client.console.ConsoleParsing;
import test.laba.client.exception.exucuteError;
import test.laba.client.mainClasses.Product;
import test.laba.client.mainClasses.Root;

import java.util.HashMap;

public class RemoveLower extends AbstractCommand {
    public RemoveLower(){
        super("RemoveLower","удалить из коллекции все элементы, меньшие, чем заданный");
    }
    public void removeLower(String arg, Root root, Console console, ConsoleParsing parsingInterface) throws exucuteError {
        Long key;
        Product product;
        console.print("Введите ключ элемента, с которым будут сравниваться элементы ");
        arg= console.scanner();
        key= parsingInterface.toLongNumber(arg,console);
        product=root.getProducts().get(key);
        for(HashMap.Entry<Long,Product> prod: root.getProducts().entrySet()){
            if(product.hashCode() >prod.getValue().hashCode()) root.getProducts().remove(prod.getKey());
        }
    }
}
