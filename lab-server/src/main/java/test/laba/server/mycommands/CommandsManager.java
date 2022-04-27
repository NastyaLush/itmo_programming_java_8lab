package test.laba.server.mycommands;

import test.laba.common.commands.AbstractCommand;
import test.laba.common.commands.Root;
import test.laba.common.util.Response;
import test.laba.common.util.ResponseWithError;
import test.laba.common.util.Values;
import test.laba.server.ServerApp;
import test.laba.server.workwithfile.Save;

import java.nio.channels.SocketChannel;
import java.util.HashMap;

/**
 * class is responsible for implementation commands
 */
public class CommandsManager {
    private Root root;
    private final HashMap<String, AbstractCommand> commands = new HashMap<>();
    private final HashMap<String, Values> commandvalues = new HashMap<>();
    private final AverageOfManufactureCost averageOfManufactureCost;
    private final Clear clear;
    private final Save save;
    private final ExecuteScript executeScript;
    private final Exit exit;
    private final GroupCountingByPrice groupCountingByPrice;
    private final Help help;
    private final History history;
    private final Info info;
    private final InsertNull insertNull;
    private final RemoveAnyByUnitOfMeasure removeAnyByUnitOfMeasure;
    private final RemoveKey removeKey;
    private final RemoveLower removeLower;
    private final RemoveLowerKey removeLowerKey;
    private final Show show;
    private final UpdateID updateID;

    /**
     * create command classes
     */
    public CommandsManager(Root root, Save save) {
        this.save = save;
        this.executeScript = new ExecuteScript();
        commands.put(executeScript.getName().toLowerCase(), executeScript);
        this.info = new Info();
        commands.put(info.getName().toLowerCase(), info);
        this.averageOfManufactureCost = new AverageOfManufactureCost();
        commands.put(averageOfManufactureCost.getName().toLowerCase(), averageOfManufactureCost);
        this.clear = new Clear();
        commands.put(clear.getName().toLowerCase(), clear);
        this.exit = new Exit();
        commands.put(exit.getName().toLowerCase(), exit);
        this.groupCountingByPrice = new GroupCountingByPrice();
        commands.put(groupCountingByPrice.getName().toLowerCase(), groupCountingByPrice);
        this.help = new Help(commands.values());
        commands.put(help.getName().toLowerCase(), help);
        this.history = new History();
        commands.put(history.getName().toLowerCase(), history);
        this.insertNull = new InsertNull();
        commands.put(insertNull.getName().toLowerCase(), insertNull);
        commandvalues.put(insertNull.getName().toLowerCase(), Values.PRODUCT);
        this.removeAnyByUnitOfMeasure = new RemoveAnyByUnitOfMeasure();
        commands.put(removeAnyByUnitOfMeasure.getName().toLowerCase(), removeAnyByUnitOfMeasure);
        commandvalues.put(removeAnyByUnitOfMeasure.getName().toLowerCase(), Values.UNIT_OF_MEASURE);
        this.removeKey = new  RemoveKey();
        commands.put(removeKey.getName().toLowerCase(), removeKey);
        commandvalues.put(removeKey.getName().toLowerCase(), Values.KEY);
        this.removeLower = new  RemoveLower();
        commands.put(removeLower.getName().toLowerCase(), removeLower);
        commandvalues.put(removeLower.getName().toLowerCase(), Values.PRODUCT_WITHOUT_KEY);
        this.removeLowerKey = new RemoveLowerKey();
        commands.put(removeLowerKey.getName().toLowerCase(), removeLowerKey);
        commandvalues.put(removeLowerKey.getName().toLowerCase(), Values.KEY);
        this.show = new Show();
        commands.put(show.getName().toLowerCase(), show);
        this.updateID = new UpdateID();
        commandvalues.put(updateID.getName().toLowerCase(), Values.PRODUCT_WITH_QUESTIONS);
        commands.put(updateID.getName().toLowerCase(), updateID);
        this.root = root;
        System.out.println(commandvalues);
    }

    public HashMap<String, AbstractCommand> getCommands() {
        return commands;
    }
    public Response chooseCommand(Response response, SocketChannel socketChannel){
        if(response.getCommand().equals(""))
        history.addToHistory(response.getCommand());
        AbstractCommand command = this.commands.get(response.getCommand().toLowerCase());
        if (command != null) {
            if(commandvalues.containsKey(command.getName().toLowerCase())){
                switch (command.getName()){
                    case "Remove_Lower_Key": {
                        return removeLowerKey.execute(response.getKey(), root);
                    }
                    case  "Remove_Lower": {
                        return removeLower.execute(response.getProduct(), root);
                    }
                    case "Remove_Key": {
                        return removeKey.execute(response.getKey(), root);
                    }
                    case "Remove_any_by_unit_of_measure": {
                        return removeAnyByUnitOfMeasure.execute(response.getUnitOfMeasure(), root);
                    }
                    case "Insert_Null": {
                        return insertNull.execute(response.getKey(), response.getProduct(), root);
                    }
                    case "Update_ID": {
                        return updateID.execute(response, root);
                    }

                }
            }
            return command.execute(response.getMessage(), root);
        } else {
            return new ResponseWithError("Данной команды не существует, проверьте корректность " +
                    "данных или введите help для получения списка команд,вы ввели " + response.getCommand().trim());
        }
    }

    public void save(){
        save.execute("", root);
    }

    public HashMap<String, Values> getCommandvalues() {
        return commandvalues;
    }

    public History getHistory() {
        return history;
    }

}
