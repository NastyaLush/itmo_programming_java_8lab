package test.laba.client.commands;

import test.laba.client.console.ConsoleParsing;
import test.laba.client.exception.VariableException;
import test.laba.client.dataClasses.Root;
import test.laba.client.dataClasses.UnitOfMeasure;

/**
 * remove any by unit of measure command
 */
public class RemoveAnyByUnitOfMeasure extends AbstractCommand {
    public RemoveAnyByUnitOfMeasure() {
        super("Remove_any_by_unit_of_measure", "удалить из коллекции один элемент, значение поля unitOfMeasure которого эквивалентно заданному");
    }

    /**
     * remove one element from the collection, the value of the unit Of Measure field of which is equivalent to the specified
     * @param arg argument type of unit of measure to comparing
     * @param root  object contained collection values
     * @param consoleParsing object is responsible for parsing from console
     * @throws VariableException throws if parsing variable to right field is impossible
     */
    public void execute(String arg, Root root, ConsoleParsing consoleParsing) throws VariableException {
        String argument = arg.trim().toUpperCase();
            UnitOfMeasure unitOfMeasure;
            unitOfMeasure = consoleParsing.toRightUnitOfMeasure(argument);
            root.removeAnyByUnitOfMeasure(unitOfMeasure);

    }
}
