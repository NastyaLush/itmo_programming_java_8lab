package test.laba.server.mycommands;


import test.laba.server.Root;
import test.laba.server.mycommands.commands.AbstractCommand;

import test.laba.common.dataClasses.UnitOfMeasure;
import test.laba.common.responses.Response;
import test.laba.common.responses.ResponseWithError;

/**
 * remove any by unit of measure command
 */
public class RemoveAnyByUnitOfMeasure extends AbstractCommand {
    public RemoveAnyByUnitOfMeasure() {
        super("Remove_any_by_unit_of_measure", "удалить из коллекции один элемент, значение поля unitOfMeasure которого эквивалентно заданному");
    }

    /**
     * remove one element from the collection, the value of the unit Of Measure field of which is equivalent to the specified
     *
     * @param arg  argument type of unit of measure to comparing
     * @param root object contained collection values
     */
    public Response execute(String arg, Root root) {
        return new ResponseWithError("the Remove_any_by_unit_of_measure can not be executed");
    }

    public Response execute(UnitOfMeasure unitOfMeasure, Root root) {
        root.removeAnyByUnitOfMeasure(unitOfMeasure);
        return new Response("the product was deleted");
    }
}
