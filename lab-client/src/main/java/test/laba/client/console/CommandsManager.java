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
import test.laba.client.mainClasses.Product;
import test.laba.client.mainClasses.Root;

import java.util.ArrayList;
import java.util.List;


public class CommandsManager {
    private final List<AbstractCommand> commands = new ArrayList<>();
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

    public CommandsManager(SaveCollection saveCollection, Console console) {
        this.executeScript = new ExecuteScript();
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
        this.updateID = new UpdateID();
        commands.add(updateID);
    }


    public void help(String arg, Console console) throws CommandWithoutArguments {
        if (isArguments(arg)) {
            throw new CommandWithoutArguments("Данная команда не принимает аргументы", console);
        }
        console.print(help.execute());
    }
    public void info(String arg, Root root, Console console) throws CommandWithoutArguments {
        if (isArguments(arg)) {
            throw new CommandWithoutArguments("Данная команда не принимает аргументы", console);
        }
        console.print(info.execute(root));
    }
    public void show(String arg, Root root, Console console) throws CommandWithoutArguments {
        if (isArguments(arg)) {
            throw new CommandWithoutArguments("Данная команда не принимает аргументы", console);
        }
        console.print(show.execute(root));
    }
    public void insertNull(String arg, Root root, Console console, ConsoleParsing parsingInterface) throws CommandWithArguments {
        if (!isArguments(arg)) {
            throw new CommandWithArguments("Данная команда должна принимать аргументы, а именно ключ нового элемента. Обратите внимание сначала идет команда, затем через пробел аргументы", console);
        }
        insertNull.execute(root, arg, console, parsingInterface);
    }
    public  void updateID(String arg, Root root, Console console, ConsoleParsing consoleParsing) throws CommandWithArguments {
        if (!isArguments(arg)) {
            throw new CommandWithArguments("Данная команда должна принимать аргументы, а именно ключ нового элемента. Обратите внимание сначала идет команда, затем через пробел аргументы", console);
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
               product = consoleParsing.parsProductFromConsole(root, arg, console);
               product.setId(id);
            } else {
               updateID.execute(root, id, console, consoleParsing);
            }
        }
    public  void removeKey(String arg, Root root, Console console, ConsoleParsing parsingInterface) throws CommandWithArguments {
        if (!isArguments(arg)) {
            throw new CommandWithArguments("Данная команда должна принимать аргументы, а именно ключ элемента, который надо удалить. Обратите внимание сначала идет команда, затем через пробел аргументы", console);
        }
        removeKey.execute(arg, root, console, parsingInterface);
    }
    public  void clear(String arg, Root root, Console console) throws CommandWithoutArguments {
        if (isArguments(arg)) {
            throw new CommandWithoutArguments("Данная команда не принимает аргументы", console);
        }
        clear.execute(root);
    }
    public  void save(String arg, Root root, FileManager fileManager, Console console, ConsoleParsing consoleParsing) throws CommandWithoutArguments {
        if (isArguments(arg)) {
            throw new CommandWithoutArguments("Данная команда не принимает аргументы", console);
        }
        save.execute(root, fileManager, console);
    }
    public  void executeScript(String arg, Root root, FileManager fileManager, Console console, ConsoleParsing parsingInterface) throws CommandWithArguments, CommandWithoutArguments {
        if (!isArguments(arg)) {
            throw new CommandWithArguments("Данная команда должна принимать аргументы", console);
        }
         executeScript.execute(arg, root, fileManager, console, parsingInterface);
    }
    public  void exit(String arg, Console console) throws CommandWithoutArguments {
        if (isArguments(arg)) {
            throw new CommandWithoutArguments("Данная команда не принимает аргументы", console);
        }
        exit.execute(console);
    }
    public  void removeLower(String arg, Root root, Console console, ConsoleParsing consoleParsing)throws CommandWithoutArguments {
        if (isArguments(arg)) {
            throw new CommandWithoutArguments("Данная команда ну принимает аргументы", console);
        }
        console.print("Введите ключ: ");
        String key = console.scanner();
        Product product = consoleParsing.parsProductFromConsole(root, key, console);
        removeLower.execute(product, root);
    }
    public  void history(String arg, Console console) throws CommandWithoutArguments {
        if (isArguments(arg)) {
            throw new CommandWithoutArguments("Данная команда не принимает аргументы", console);
        }
        history.execute(console);
    }
    public  void removeLowerKey(String arg, Root root, Console console, ConsoleParsing parsingInterface)throws CommandWithArguments {
        if (!isArguments(arg)) {
            throw new CommandWithArguments("Данная команда должна принимать аргументы", console);
        }
        removeLowerKey.execute(arg, root, console, parsingInterface);
    }
    public  void removeAnyByUnitOfMeasure(String arg, Root root, Console console, ConsoleParsing parsingInterface) throws CommandWithArguments {
        if (!isArguments(arg)) {
            throw new CommandWithArguments("Данная команда должна принимать аргументы", console);
        }
        removeAnyByUnitOfMeasure.execute(arg, root, console, parsingInterface);
    }
    public  void averageOfManufactureCost(String arg, Root root, Console console) throws CommandWithoutArguments {
        if (isArguments(arg)) {
            throw new CommandWithoutArguments("Данная команда не принимает аргументы", console);
        }
        averageOfManufactureCost.execute(root, console);
    }
    public  void groupCountingByPrice(String arg, Root root, Console console) throws CommandWithoutArguments {
        if (isArguments(arg)) {
            throw new CommandWithoutArguments("Данная команда не принимает аргументы", console);
        }
        groupCountingByPrice.execute(root, console);
    }


    private  boolean isArguments(String arg) {
        return !"".equals(arg);
    }

    public History getHistory() {
        return history;
    }
}
