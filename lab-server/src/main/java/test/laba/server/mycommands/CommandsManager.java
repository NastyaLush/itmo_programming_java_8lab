package test.laba.server.mycommands;

import test.laba.common.commands.AbstractCommand;
import test.laba.common.commands.ConsoleParsing;
import test.laba.common.exception.VariableException;
import test.laba.server.mycommands.AverageOfManufactureCost;
import test.laba.server.mycommands.Clear;
import test.laba.server.mycommands.ExecuteScript;
import test.laba.server.mycommands.Exit;
import test.laba.server.mycommands.GroupCountingByPrice;
import test.laba.server.mycommands.Help;
import test.laba.server.mycommands.History;
import test.laba.server.mycommands.Info;
import test.laba.server.mycommands.InsertNull;
import test.laba.server.mycommands.RemoveAnyByUnitOfMeasure;
import test.laba.server.mycommands.RemoveKey;
import test.laba.server.mycommands.RemoveLower;
import test.laba.server.mycommands.RemoveLowerKey;
import test.laba.server.mycommands.Show;
import test.laba.server.mycommands.UpdateID;
import test.laba.common.commands.Root;

import java.util.HashMap;

/**
 * class is responsible for implementation commands
 */
public class CommandsManager {
    private Root root;
    private final HashMap<String, AbstractCommand> commands = new HashMap<>();
    private final AverageOfManufactureCost averageOfManufactureCost;
    private final Clear clear;
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
    public CommandsManager(ConsoleParsing consoleParsing, Root root) {
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
        this.insertNull = new InsertNull(consoleParsing);
        commands.put(insertNull.getName().toLowerCase(), insertNull);
        this.removeAnyByUnitOfMeasure = new RemoveAnyByUnitOfMeasure(consoleParsing);
        commands.put(removeAnyByUnitOfMeasure.getName().toLowerCase(), removeAnyByUnitOfMeasure);
        this.removeKey = new  RemoveKey(consoleParsing);
        commands.put(removeKey.getName().toLowerCase(), removeKey);
        this.removeLower = new  RemoveLower(consoleParsing);
        commands.put(removeLower.getName().toLowerCase(), removeLower);
        this.removeLowerKey = new RemoveLowerKey(consoleParsing);
        commands.put(removeLowerKey.getName().toLowerCase(), removeLowerKey);
        this.show = new Show();
        commands.put(show.getName().toLowerCase(), show);
        this.updateID = new UpdateID(consoleParsing);
        commands.put(updateID.getName().toLowerCase(), updateID);
        this.root = root;
    }

    public HashMap<String, AbstractCommand> getCommands() {
        return commands;
    }
    public String chooseCommand(String argument){
        String [] arguments = argument.split(" ");
        history.addToHistory(arguments[0]);
        AbstractCommand command = this.commands.get(arguments[0].toLowerCase());
        if (command != null) {
            if (arguments.length > 1) {
                return command.execute(arguments[1], root);
            } else {
                return command.execute("null", root);
            }
        } else {
            return "Данной команды не существует, проверьте корректность данных или введите help для получения списка команд,вы ввели " + arguments[0].trim();
        }
    }

    public History getHistory() {
        return history;
    }
}
