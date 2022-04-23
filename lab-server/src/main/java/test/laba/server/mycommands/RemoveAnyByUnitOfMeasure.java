package test.laba.server.mycommands;


import test.laba.common.commands.ConsoleParsing;
import test.laba.common.commands.Root;
import test.laba.common.exception.VariableException;
import test.laba.common.commands.AbstractCommand;

import test.laba.common.dataClasses.UnitOfMeasure;

/**
 * remove any by unit of measure command
 */
public class RemoveAnyByUnitOfMeasure extends AbstractCommand {
    private ConsoleParsing consoleParsing;
    public RemoveAnyByUnitOfMeasure(ConsoleParsing consoleParsing) {
        super("Remove_any_by_unit_of_measure", "удалить из коллекции один элемент, значение поля unitOfMeasure которого эквивалентно заданному");
        this.consoleParsing = consoleParsing;
    }

    /**
     * remove one element from the collection, the value of the unit Of Measure field of which is equivalent to the specified
     * @param arg argument type of unit of measure to comparing
     * @param root  object contained collection values
     * @throws VariableException throws if parsing variable to right field is impossible
     */
    public String execute(String arg, Root root)  {
        String argument = arg.trim().toUpperCase();
            UnitOfMeasure unitOfMeasure;
        try {
            unitOfMeasure = consoleParsing.toRightUnitOfMeasure(argument);
            root.removeAnyByUnitOfMeasure(unitOfMeasure);
        } catch (VariableException e) {
            // TODO: 16.04.2022 error handling
        }
            return "the product was deleted";
    }
}
