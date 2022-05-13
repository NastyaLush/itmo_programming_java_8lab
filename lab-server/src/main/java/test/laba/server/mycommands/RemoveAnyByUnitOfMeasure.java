package test.laba.server.mycommands;


import test.laba.common.exception.WrongUsersData;
import test.laba.server.BD.BDManager;
import test.laba.server.BD.BDUsersManager;
import test.laba.server.mycommands.commands.AbstractCommand;

import test.laba.common.dataClasses.UnitOfMeasure;
import test.laba.common.responses.Response;
import test.laba.common.responses.ResponseWithError;

import java.sql.SQLException;

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

    public Response execute(UnitOfMeasure unitOfMeasure, Root root, BDManager bdManager, BDUsersManager bdUsersManager, String login) {

        try {
            Long key = root.removeAnyByUnitOfMeasure(unitOfMeasure, bdUsersManager.getId(login));
            if (key != null) {
                bdManager.removeKey(login, key, root, bdUsersManager);
                root.remove(key);
            } else {
                return new Response("so product not exist");
            }
        } catch (SQLException | WrongUsersData e) {
            e.printStackTrace();
            return new ResponseWithError("the object wasn't deleted because of " + e.getMessage());
        }
        return new Response("the product was deleted");
    }
}
