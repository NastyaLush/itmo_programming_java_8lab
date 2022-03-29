package test.laba.client.console;

import test.laba.client.commands.AbstractCommand;
import test.laba.client.commands.AverageOfManufactureCost;
import test.laba.client.commands.Clear;
import test.laba.client.commands.ExecuteScript;
import test.laba.client.commands.Exit;
import test.laba.client.commands.GroupCountingByPrice;
import test.laba.client.commands.Help;
import test.laba.client.commands.History;
import test.laba.client.commands.Info;
import test.laba.client.commands.InsertNull;
import test.laba.client.commands.RemoveAnyByUnitOfMeasure;
import test.laba.client.commands.RemoveKey;
import test.laba.client.commands.RemoveLower;
import test.laba.client.commands.RemoveLowerKey;
import test.laba.client.commands.Save;
import test.laba.client.commands.Show;
import test.laba.client.commands.UpdateID;
import test.laba.client.dataClasses.Root;

import java.util.HashMap;

/**
 * class is responsible for implementation commands
 */
public class CommandsManager {
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
    private final Save save;
    private final Show show;
    private final UpdateID updateID;

    /**
     * create command classes
     * @param console object is responsible for work with console
     */
    public CommandsManager(Console console, ConsoleParsing consoleParsing, FileManager fileManager) {
        this.executeScript = new ExecuteScript(console, fileManager);
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
        this.save = new Save(fileManager);
        commands.put(save.getName().toLowerCase(), save);
        this.show = new Show();
        commands.put(show.getName().toLowerCase(), show);
        this.updateID = new UpdateID(console, consoleParsing);
        commands.put(updateID.getName().toLowerCase(), updateID);
    }

    public String chooseCommand(String[] arguments, Root root) {
        AbstractCommand command = this.commands.get(arguments[0]);
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

    private  boolean isArguments(String arg) {
        return !"".equals(arg);
    }
    public History getHistory() {
        return history;
    }
}
