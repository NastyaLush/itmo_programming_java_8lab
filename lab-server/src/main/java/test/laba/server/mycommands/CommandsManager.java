package test.laba.server.mycommands;

import test.laba.common.exception.CreateError;
import test.laba.common.exception.VariableException;
import test.laba.server.BD.BDManager;
import test.laba.server.BD.BDUsersManager;
import test.laba.server.mycommands.commands.AbstractCommand;

import test.laba.common.responses.Response;
import test.laba.common.responses.ResponseWithError;
import test.laba.common.util.Values;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * class is responsible for implementation commands
 */
public class CommandsManager {
    private Root root;
    private HashMap<String, AbstractCommand> commands = new HashMap<>();
    private final HashMap<String, Values> commandValues = new HashMap<>();
    private History history;
    private InsertNull insertNull;
    private RemoveAnyByUnitOfMeasure removeAnyByUnitOfMeasure;
    private RemoveKey removeKey;
    private RemoveLower removeLower;
    private RemoveLowerKey removeLowerKey;
    private UpdateID updateID;
    private BDManager bdManager;
    private BDUsersManager bdUsersManager;

    /**
     * create command classes
     */


    public CommandsManager(BDManager bdManager, BDUsersManager bdUsersManager) throws VariableException, CreateError, SQLException {
        this.bdUsersManager = bdUsersManager;
        this.bdManager = bdManager;
        this.history = new History();
        this.root = bdManager.getProducts();
        initialization();
    }

    private void initialization() {
        commands.put(history.getName().toLowerCase(), history);
        this.insertNull = new InsertNull();
        commands.put(insertNull.getName().toLowerCase(), insertNull);
        commandValues.put(insertNull.getName().toLowerCase(), Values.PRODUCT);
        this.removeAnyByUnitOfMeasure = new RemoveAnyByUnitOfMeasure();
        commands.put(removeAnyByUnitOfMeasure.getName().toLowerCase(), removeAnyByUnitOfMeasure);
        commandValues.put(removeAnyByUnitOfMeasure.getName().toLowerCase(), Values.UNIT_OF_MEASURE);
        this.removeKey = new RemoveKey();
        commands.put(removeKey.getName().toLowerCase(), removeKey);
        commandValues.put(removeKey.getName().toLowerCase(), Values.KEY);
        this.removeLower = new RemoveLower();
        commands.put(removeLower.getName().toLowerCase(), removeLower);
        commandValues.put(removeLower.getName().toLowerCase(), Values.PRODUCT_WITHOUT_KEY);
        this.removeLowerKey = new RemoveLowerKey();
        commands.put(removeLowerKey.getName().toLowerCase(), removeLowerKey);
        commandValues.put(removeLowerKey.getName().toLowerCase(), Values.KEY);
        Show show = new Show();
        commands.put(show.getName().toLowerCase(), show);
        this.updateID = new UpdateID();
        commandValues.put(updateID.getName().toLowerCase(), Values.PRODUCT_WITH_QUESTIONS);
        commands.put(updateID.getName().toLowerCase(), updateID);
        Help help = new Help();
        commands.put(help.getName().toLowerCase(), help);
        help.setCommands(commands.values());
        Clear clear = new Clear(bdManager);
        commands.put(clear.getName().toLowerCase(), clear);
        ExecuteScript executeScript = new ExecuteScript();
        commands.put(executeScript.getName().toLowerCase(), executeScript);
        Info info = new Info();
        commands.put(info.getName().toLowerCase(), info);
        AverageOfManufactureCost averageOfManufactureCost = new AverageOfManufactureCost();
        commands.put(averageOfManufactureCost.getName().toLowerCase(), averageOfManufactureCost);
        Exit exit = new Exit();
        commands.put(exit.getName().toLowerCase(), exit);
        GroupCountingByPrice groupCountingByPrice = new GroupCountingByPrice();
        commands.put(groupCountingByPrice.getName().toLowerCase(), groupCountingByPrice);
    }

    public HashMap<String, AbstractCommand> getCommands() {
        return commands;
    }

    public Response chooseCommand(Response response) {
        history.addToHistory(response.getCommand());
        Response response1 = null;
        AbstractCommand command = this.commands.get(response.getCommand().toLowerCase());
        if (command != null) {
            if (commandValues.containsKey(command.getName().toLowerCase())) {
                switch (command.getName()) {
                    case "Remove_Lower_Key":
                        response1 = removeLowerKey.execute(response, bdUsersManager, bdManager, root);
                        break;
                    case "Remove_Lower":
                        response1 = removeLower.execute(response, bdUsersManager, bdManager, root);
                        break;
                    case "Remove_Key":
                        response1 = removeKey.execute(response, root, bdManager, bdUsersManager);
                        break;
                    case "Remove_any_by_unit_of_measure":
                        response1 = removeAnyByUnitOfMeasure.execute(response.getUnitOfMeasure(), root, bdManager, bdUsersManager, response.getLogin());
                        break;
                    case "Insert_Null":
                        response1 = insertNull.execute(response.getKey(), response, root, bdManager, bdUsersManager);
                        break;
                    case "Update_ID":
                        response1 = updateID.execute(response, root, bdUsersManager, response.getLogin(), bdManager);
                        break;
                    default:
                        break;
                }
                return response1;
            }
            return command.execute(response.getMessage(), root);
        } else {
            return new ResponseWithError("Данной команды не существует, проверьте корректность "
                    + "данных или введите help для получения списка команд,вы ввели " + response.getCommand().trim());
        }
    }
/*
    public void save() {
        save.execute("", root);
    }
    public void saveUsers(UsersHandler usersHandler) throws ParsException, IOException {
        save.save(usersHandler);
    }*/


    public HashMap<String, Values> getCommandValues() {
        return commandValues;
    }


}
