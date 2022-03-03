package test.laba.client.commands;

import test.laba.client.console.ConsoleParsing;
import test.laba.client.exception.VariableException;
import test.laba.client.mainClasses.Root;
import test.laba.client.mainClasses.UnitOfMeasure;

public class RemoveAnyByUnitOfMeasure extends AbstractCommand {
    public RemoveAnyByUnitOfMeasure() {
        super("Remove_any_by_unit_of_measure", "удалить из коллекции один элемент, значение поля unitOfMeasure которого эквивалентно заданному");
    }
    public void execute(String arg, Root root, ConsoleParsing parsingInterface) throws VariableException {
        String argument = arg.trim().toUpperCase();
            UnitOfMeasure unitOfMeasure;
            unitOfMeasure = parsingInterface.toRightUnitOfMeasure(argument);
            root.removeAnyByUnitOfMeasure(unitOfMeasure);

    }
}
