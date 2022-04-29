package test.laba.server.mycommands;

import test.laba.server.mycommands.commands.AbstractCommand;

import test.laba.common.responses.Response;
import test.laba.common.responses.ResponseWithError;
import test.laba.common.util.Values;
import test.laba.server.workwithfile.Save;

import java.util.HashMap;

/**
 * class is responsible for implementation commands
 */
public class CommandsManager {
    private final Root root;
    private final HashMap<String, AbstractCommand> commands = new HashMap<>();
    private final HashMap<String, Values> commandValues = new HashMap<>();
    private final Save save;
    private final History history;
    private final InsertNull insertNull;
    private final RemoveAnyByUnitOfMeasure removeAnyByUnitOfMeasure;
    private final RemoveKey removeKey;
    private final RemoveLower removeLower;
    private final RemoveLowerKey removeLowerKey;
    private final UpdateID updateID;

    /**
     * create command classes
     */
    public CommandsManager(Root root, Save save) {
        this.save = save;
        ExecuteScript executeScript = new ExecuteScript();
        commands.put(executeScript.getName().toLowerCase(), executeScript);
        Info info = new Info();
        commands.put(info.getName().toLowerCase(), info);
        AverageOfManufactureCost averageOfManufactureCost = new AverageOfManufactureCost();
        commands.put(averageOfManufactureCost.getName().toLowerCase(), averageOfManufactureCost);
        Clear clear = new Clear();
        commands.put(clear.getName().toLowerCase(), clear);
        Exit exit = new Exit();
        commands.put(exit.getName().toLowerCase(), exit);
        GroupCountingByPrice groupCountingByPrice = new GroupCountingByPrice();
        commands.put(groupCountingByPrice.getName().toLowerCase(), groupCountingByPrice);
        Help help = new Help(commands.values());
        commands.put(help.getName().toLowerCase(), help);
        this.history = new History();
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
        this.root = root;
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
                        response1 = removeLowerKey.execute(response.getKey(), root);
                        break;
                    case "Remove_Lower":
                        response1 = removeLower.execute(response.getProduct(), root);
                        break;
                    case "Remove_Key":
                        response1 = removeKey.execute(response.getKey(), root);
                        break;
                    case "Remove_any_by_unit_of_measure":
                        response1 = removeAnyByUnitOfMeasure.execute(response.getUnitOfMeasure(), root);
                        break;
                    case "Insert_Null":
                        response1 = insertNull.execute(response.getKey(), response.getProduct(), root);
                        break;
                    case "Update_ID":
                        response1 = updateID.execute(response, root);
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

    public void save() {
        save.execute("", root);
    }

    public HashMap<String, Values> getCommandValues() {
        return commandValues;
    }


}
