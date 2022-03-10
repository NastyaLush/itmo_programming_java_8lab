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
import test.laba.client.exception.CommandWithArguments;
import test.laba.client.exception.CommandWithoutArguments;
import test.laba.client.exception.VariableException;
import test.laba.client.dataClasses.Product;
import test.laba.client.dataClasses.Root;
import test.laba.client.dataClasses.UnitOfMeasure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * class is responsible for implementation commands
 */
public class CommandsManager {
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
     * @param saveCollection object is responsible for saving collection
     * @param console object is responsible for work with console
     */
    public CommandsManager(SaveCollection saveCollection, Console console) {
        this.executeScript = new ExecuteScript(console);
        List<AbstractCommand> commands = new ArrayList<>();
        commands.add(executeScript);
        this.info = new Info();
        commands.add(info);
        this.averageOfManufactureCost = new AverageOfManufactureCost();
        commands.add(averageOfManufactureCost);
        this.clear = new Clear();
        commands.add(clear);
        this.exit = new Exit();
        commands.add(exit);
        this.groupCountingByPrice = new GroupCountingByPrice();
        commands.add(groupCountingByPrice);
        this.help = new Help(commands);
        commands.add(help);
        this.history = new History();
        commands.add(history);
        this.insertNull = new InsertNull();
        commands.add(insertNull);
        this.removeAnyByUnitOfMeasure = new RemoveAnyByUnitOfMeasure();
        commands.add(removeAnyByUnitOfMeasure);
        this.removeKey = new  RemoveKey();
        commands.add(removeKey);
        this.removeLower = new  RemoveLower();
        commands.add(removeLower);
        this.removeLowerKey = new RemoveLowerKey();
        commands.add(removeLowerKey);
        this.save = new Save(saveCollection);
        commands.add(save);
        this.show = new Show();
        commands.add(show);
        this.updateID = new UpdateID(console);
        commands.add(updateID);
    }
    /**
     * check accept command arguments or not and execute command
     * @param arg argument for processing
     * @param console object is responsible for work with console
     * @throws CommandWithoutArguments throws if argument not right
     */
    public void help(String arg, Console console) throws CommandWithoutArguments {
        if (isArguments(arg)) {
            throw new CommandWithoutArguments("Команда " + help.getName() + " не принимает аргументы", console);
        }
        console.print(help.execute());
    }
    /**
     * check accept command arguments or not and execute command
     * @param arg argument for processing
     * @param root object contained collection
     * @param console object is responsible for work with console
     * @throws CommandWithoutArguments throws if argument not right
     */
    public void info(String arg, Root root, Console console) throws CommandWithoutArguments {
        if (isArguments(arg)) {
            throw new CommandWithoutArguments("Команда " + info.getName() + " не принимает аргументы", console);
        }
        console.print(info.execute(root));
    }
    /**
     * check accept command arguments or not and execute command
     * @param arg argument for processing
     * @param root object contained collection
     * @param console object is responsible for work with console
     * @throws CommandWithoutArguments throws if argument not right
     */
    public void show(String arg, Root root, Console console) throws CommandWithoutArguments {
        if (isArguments(arg)) {
            throw new CommandWithoutArguments("Команда " + show.getName() + " не принимает аргументы", console);
        }
        console.print(show.execute(root));
    }
    /**
     * check accept command arguments or not and execute command
     * @param arg argument for processing
     * @param root object contained collection
     * @param console object is responsible for work with console
     * @param consoleParsing object is responsible for parsing from console
     * @throws CommandWithArguments throws if argument not right
     */
    public void insertNull(String arg, Root root, Console console, ConsoleParsing consoleParsing) throws CommandWithArguments {
        if (!isArguments(arg)) {
            throw new CommandWithArguments("Команда " + insertNull.getName() + " должна принимать аргументы, а именно ключ нового элемента. Обратите внимание сначала идет команда, затем через пробел аргументы", console);
        }
        insertNull.execute(root, arg, consoleParsing);
    }
    /**
     * check accept command arguments or not and execute command, check argument for compliance with requirements
     * @param arg argument for processing
     * @param root object contained collection
     * @param console object is responsible for work with console
     * @param consoleParsing object is responsible for parsing from console
     * @throws CommandWithArguments throws if argument not right
     */
    public  void updateID(String arg, Root root, Console console, ConsoleParsing consoleParsing) throws CommandWithArguments {
        if (!isArguments(arg)) {
            throw new CommandWithArguments("Команда " + updateID.getName() + " должна принимать аргументы, а именно ключ нового элемента. Обратите внимание сначала идет команда, затем через пробел аргументы", console);
        }
        String argument = arg;
        long id = 0;
        boolean flag = true;
        Product product;
            //проверка соответствия формата ID
            while (flag) {
                try {
                    id = Long.parseLong(argument);
                    flag = false;
                } catch (NumberFormatException e) {
                    console.printError("Неправильный формат ввода, ожидалось число, повторите попытку");
                    console.print("Введите ID");
                    argument = console.scanner();
                }
            }

            //проверка существования ID
            if (!root.containsID(id)) {
                console.print("Данного ID не существует, идет создание нового объекта");
               product = consoleParsing.parsProductFromConsole(root);
               product.setId(id);
               root.setProduct(product);
            } else {
               updateID.execute(root, id, consoleParsing);
            }
        }
    /**
     * check accept command arguments or not and execute command
     * @param arg argument for processing
     * @param root object contained collection
     * @param console object is responsible for work with console
     * @param consoleParsing object is responsible for parsing from console
     * @throws CommandWithArguments throws if argument not right
     */
    public  void removeKey(String arg, Root root, Console console, ConsoleParsing consoleParsing) throws CommandWithArguments {
        if (!isArguments(arg)) {
            throw new CommandWithArguments("Команда " + removeKey.getName() + " должна принимать аргументы, а именно ключ элемента, который надо удалить. Обратите внимание сначала идет команда, затем через пробел аргументы", console);
        }
        removeKey.execute(arg, root, consoleParsing);
    }
    /**
     * check accept command arguments or not and execute command
     * @param arg argument for processing
     * @param root object contained collection
     * @param console object is responsible for work with console
     * @throws CommandWithoutArguments throws if argument not right
     */
    public  void clear(String arg, Root root, Console console) throws CommandWithoutArguments {
        if (isArguments(arg)) {
            throw new CommandWithoutArguments("Команда " + clear.getName() + " не принимает аргументы", console);
        }
        clear.execute(root);
    }
    /**
     * check accept command arguments or not and execute command
     * @param arg argument for processing
     * @param root object contained collection
     * @param fileManager object is responsible for work with file
     * @param console object is responsible for work with console
     * @throws CommandWithoutArguments throws if argument not right
     */
    public  void save(String arg, Root root, FileManager fileManager, Console console) throws CommandWithoutArguments {
        if (isArguments(arg)) {
            throw new CommandWithoutArguments("Команда " + save.getName() + " не принимает аргументы", console);
        }
        try {
            save.execute(root, fileManager);
        } catch (IOException e) {
            console.printError("Не удалось сохранить коллекцию!");
        }
    }
    /**
     * check accept command arguments or not and execute command
     * @param arg argument for processing
     * @param root object contained collection
     * @param fileManager object is responsible for work with file
     * @param console object is responsible for work with console
     * @throws CommandWithoutArguments throws if argument not right
     * @throws CommandWithArguments throws if argument not right
     */
    public  void executeScript(String arg, Root root, FileManager fileManager, Console console) throws CommandWithArguments {
        if (!isArguments(arg)) {
            throw new CommandWithArguments("Команда " + executeScript.getName() + "  должна принимать аргументы", console);
        }
        try {
            executeScript.execute(arg, root, fileManager);
        } catch (java.io.IOException | CommandWithoutArguments e) {
            console.printError("Ошибка при выполнении скрипта");
        }
    }

    /**
     * check accept command arguments or not and execute command
     * @param arg argument for processing
     * @throws CommandWithoutArguments throws if argument not right
     */
    public  void exit(String arg, Console console) throws CommandWithoutArguments {
        if (isArguments(arg)) {
            throw new CommandWithoutArguments("Команда " + exit.getName() + " не принимает аргументы", console);
        }
        exit.execute();
    }

    /**
     * check accept command arguments or not and execute command
     * check accept command arguments or not and execute command
     * @param arg argument for processing
     * @param root object contained collection
     * @param console object is responsible for work with console
     * @param consoleParsing object is responsible for console parsing
     * @throws CommandWithoutArguments throws if argument not right
     */
    public  void removeLower(String arg, Root root, Console console, ConsoleParsing consoleParsing)throws CommandWithoutArguments {
        if (isArguments(arg)) {
            throw new CommandWithoutArguments("Команда " + removeLower.getName() + " ну принимает аргументы", console);
        }
        Product product = consoleParsing.parsProductFromConsole(root);
        removeLower.execute(product, root);
    }

    /**
     * check accept command arguments or not and execute command
     * @param arg argument for processing
     * @param console object is responsible for work with console
     * @throws CommandWithoutArguments throws if argument not right
     */
    public  void history(String arg, Console console) throws CommandWithoutArguments {
        if (isArguments(arg)) {
            throw new CommandWithoutArguments("Команда " + history.getName() + " не принимает аргументы", console);
        }
        console.print(history.execute());
    }

    /**
     * check accept command arguments or not and execute command
     * @param arg argument for processing
     * @param root object contained collection
     * @param console object is responsible for work with console
     * @param consoleParsing object is responsible for console parsing
     * @throws CommandWithArguments throws if argument not right
     */
    public  void removeLowerKey(String arg, Root root, Console console, ConsoleParsing consoleParsing)throws CommandWithArguments {
        if (!isArguments(arg)) {
            throw new CommandWithArguments("Команда " + removeLowerKey.getName() + " должна принимать аргументы", console);
        }
        removeLowerKey.execute(arg, root, consoleParsing);
    }

    /**
     * check accept command arguments or not and execute command
     * @param arg argument for processing
     * @param root object contained collection
     * @param console object is responsible for work with console
     * @param consoleParsing object is responsible for console parsing
     * @throws CommandWithArguments throws if argument not right
     */
    public  void removeAnyByUnitOfMeasure(String arg, Root root, Console console, ConsoleParsing consoleParsing) throws CommandWithArguments {
        if (!isArguments(arg)) {
            throw new CommandWithArguments("Команда " + removeAnyByUnitOfMeasure.getName() + " должна принимать аргументы", console);
        }
        try {
            removeAnyByUnitOfMeasure.execute(arg, root, consoleParsing);
        } catch (VariableException | IllegalArgumentException e) {
            console.printError("Проверьте вводимый аргумент, поле может принимать только: " + Arrays.toString(UnitOfMeasure.values()));
            console.ask("Повторите ввод:");
            removeAnyByUnitOfMeasure(console.scanner(), root, console, consoleParsing);
        }
    }

    /**
     * check accept command arguments or not and execute command
     * @param arg argument for processing
     * @param root object contained collection
     * @param console object is responsible for work with console
     * @throws CommandWithoutArguments throws if argument not right
     */
    public  void averageOfManufactureCost(String arg, Root root, Console console) throws CommandWithoutArguments {
        if (isArguments(arg)) {
            throw new CommandWithoutArguments("Команда " + averageOfManufactureCost.getName() + " не принимает аргументы", console);
        }
        console.print("Average Of Manufacture Cost: " + averageOfManufactureCost.execute(root));
    }

    /**
     * check accept command arguments or not and execute command
     * @param arg argument for processing
     * @param root object contained collection
     * @param console object is responsible for work with console
     * @throws CommandWithoutArguments throws if argument not right
     */
    public  void groupCountingByPrice(String arg, Root root, Console console) throws CommandWithoutArguments {
        if (isArguments(arg)) {
            throw new CommandWithoutArguments("Команда " + groupCountingByPrice.getName() + " не принимает аргументы", console);
        }

        HashMap<Long, Long> countingByPrice = groupCountingByPrice.execute(root);
        console.print("price = count\n");
        for (Map.Entry<Long, Long> entry: countingByPrice.entrySet()) {
            console.print(entry.getKey() + " =  " + entry.getValue());
        }
    }


    private  boolean isArguments(String arg) {
        return !"".equals(arg);
    }
    public History getHistory() {
        return history;
    }
}
