package test.laba.client.commands;


import test.laba.client.console.Console;
import test.laba.client.console.ConsoleParsing;
import test.laba.client.exception.VariableException;
import test.laba.client.mainClasses.Product;
import test.laba.client.mainClasses.Root;
import test.laba.client.mainClasses.UnitOfMeasure;

import java.util.HashMap;

public class RemoveAnyByUnitOfMeasure extends AbstractCommand {
    public RemoveAnyByUnitOfMeasure(){
        super("Remove_any_by_unit_of_measure","удалить из коллекции один элемент, значение поля unitOfMeasure которого эквивалентно заданному");
    }
    public void removeAnyByUnitOfMeasure(String arg, Root root, Console console, ConsoleParsing parsingInterface){
        try {
            UnitOfMeasure unitOfMeasure;
            while (true) {
                try {
                    unitOfMeasure = parsingInterface.toRightUnitOfMeasure(arg,console);
                    break;
                }
                catch (VariableException e){
                    console.print("Введите unitOfMeasure, возможные варианты:PCS, MILLILITERS, GRAMS, UN_INIT");
                    arg= console.scanner();
                }
            }
            for(HashMap.Entry<Long, Product> prod: root.getProducts().entrySet()){
                if(unitOfMeasure==prod.getValue().getUnitOfMeasure()) {
                    root.getProducts().remove(prod.getKey());
                    break;
                }

            }
        }
        catch (VariableException ignored){}

    }
}
