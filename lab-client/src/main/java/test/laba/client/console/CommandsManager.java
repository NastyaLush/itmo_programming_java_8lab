package test.laba.client.console;



import test.laba.client.commands.*;
import test.laba.client.exception.CommandWithArguments;
import test.laba.client.exception.CommandWithoutArguments;
import test.laba.client.exception.exucuteError;
import test.laba.client.mainClasses.Root;

import java.util.*;

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

    public CommandsManager(){
        this.executeScript= new ExecuteScript();
        commands.add(executeScript);
        this.info= new Info();
        commands.add(info);
        this.averageOfManufactureCost= new AverageOfManufactureCost();
        commands.add(averageOfManufactureCost);
        this.clear= new Clear();
        commands.add(clear);
        this.exit= new Exit();
        commands.add(exit);
        this.groupCountingByPrice= new GroupCountingByPrice();
        commands.add(groupCountingByPrice);
        this.help= new Help();
        commands.add(help);
        this.history= new History();
        commands.add(history);
        this.insertNull= new InsertNull();
        commands.add(insertNull);
        this.removeAnyByUnitOfMeasure= new RemoveAnyByUnitOfMeasure();
        commands.add(removeAnyByUnitOfMeasure);
        this.removeKey= new  RemoveKey();
        commands.add(removeKey);
        this.removeLower= new  RemoveLower();
        commands.add(removeLower);
        this.removeLowerKey= new RemoveLowerKey();
        commands.add(removeLowerKey);
        this.save= new Save();
        commands.add(save);
        this.show= new Show();
        commands.add(show);
        this.updateID= new UpdateID();
        commands.add(updateID);
    }


    public void help(String arg,Console console) throws CommandWithoutArguments {
        if(isArguments(arg)) {
            throw new CommandWithoutArguments("Данная команда не принимает аргументы", console);
        }
        help.help(commands, console);
    }
    public void info(String arg, Root root, Console console) throws CommandWithoutArguments {
        if(isArguments(arg)) {
            throw new CommandWithoutArguments("Данная команда не принимает аргументы", console);
        }
        info.info(root, console);
    }
    public void show(String arg, Root root,Console console) throws CommandWithoutArguments {
        if(isArguments(arg)) {
            throw new CommandWithoutArguments("Данная команда не принимает аргументы", console);
        }
        show.show(root, console);
    }
    public void insertNull(String arg, Root root, Console console, ConsoleParsing parsingInterface) throws CommandWithArguments{
        if(!isArguments(arg)) {
            throw new CommandWithArguments("Данная команда должна принимать аргументы, а именно ключ нового элемента. Обратите внимание сначала идет команда, затем через пробел аргументы", console);
        }
        insertNull.insertnull(root, arg, console, parsingInterface);
    }
   /* public  boolean updateID(String arg, Root root, Console console, ConsoleParsing parsingInterface) throws CommandWithArguments{
        if(!isArguments(arg)) {
            throw new CommandWithArguments("Данная команда должна принимать аргументы, а именно ключ нового элемента. Обратите внимание сначала идет команда, затем через пробел аргументы", console);
        }
        if(console.isFlag())  {
            long id = 0;
            boolean flag = true;
            //проверка соответствия формата ID
            while (flag) {
                try {
                    id = Long.parseLong(arg);
                    flag = false;
                } catch (NumberFormatException e) {
                    console.printError("Неправильный формат ввода, ожидалось число, повторите попытку");
                    if(!console.isFlag()) return false;
                    console.print("Введите ID");
                }
            }

            //проверка существования ID
            if (!root.getProducts().containsKey(id)) {
                console.print("Данного ID не существует, идет создание нового объекта");
               return insertNull(arg, root, console, parsingInterface);
            }

            //работа программы, если id существует
            else {
                return updateID.updateID(root, id, console, parsingInterface);
            }
        }
    }
    public  boolean removeKey(String arg, Root root,Console console, ConsoleParsing parsingInterface) throws CommandWithArguments {
        if(!isArguments(arg)) {
            throw new CommandWithArguments("Данная команда должна принимать аргументы, а именно ключ элемента, который надо удалить. Обратите внимание сначала идет команда, затем через пробел аргументы", console);
            return
        }
        removeKey.removeKey(arg,root,console,parsingInterface);
    }
    public  void clear(String arg, Root root,Console console) throws CommandWithoutArguments {
        if (isArguments(arg)) {
            throw new CommandWithoutArguments("Данная команда не принимает аргументы", console);
        }
        clear.clear(root);
    }
    public  void save(String arg, Root root, FileManager fileManager,Console console,ConsoleParsing consoleParsing) throws CommandWithoutArguments {
        if (isArguments(arg)) {
            throw new CommandWithoutArguments("Данная команда не принимает аргументы", console);
        }
        save.save(root,fileManager,console,consoleParsing);
    }
    */public  void executeScript(String arg, Root root, FileManager fileManager, Console console, ConsoleParsing parsingInterface) throws CommandWithArguments, CommandWithoutArguments{
        if(!isArguments(arg)) {
            throw new CommandWithArguments("Данная команда должна принимать аргументы", console);
        }
         executeScript.executeScript(arg, root,this,fileManager,console,parsingInterface);
    }/*
    public  void exit(String arg, Console console) throws CommandWithoutArguments {
        if(isArguments(arg)) {
            throw new CommandWithoutArguments("Данная команда не принимает аргументы", console);
        }
        exit.exit(console);
    }
    public  boolean removeLower(String arg, Root root, Console console, ConsoleParsing parsingInterface)throws CommandWithoutArguments{
        if (isArguments(arg)) {
            throw new CommandWithoutArguments("Данная команда не принимает аргументы", console);
        }
        return removeLower.removeLower(arg,root,console,parsingInterface);
    }
    public  void history(String arg, Console console) throws CommandWithoutArguments{
        if(isArguments(arg)) {
            throw new CommandWithoutArguments("Данная команда не принимает аргументы", console);
        }
        history.history(console);
    }
    public  boolean removeLowerKey(String arg, Root root, Console console, ConsoleParsing parsingInterface)throws CommandWithArguments{
        if (!isArguments(arg)) {
            throw new CommandWithArguments("Данная команда должна принимать аргументы", console);
        }
        return removeLowerKey.removeLowerKey(arg,root,console,parsingInterface);
    }*/
    public  void removeAnyByUnitOfMeasure(String arg, Root root, Console console, ConsoleParsing parsingInterface) throws CommandWithArguments, exucuteError {
        if (!isArguments(arg)) {
            throw new CommandWithArguments("Данная команда должна принимать аргументы", console);
        }
        removeAnyByUnitOfMeasure.removeAnyByUnitOfMeasure(arg,root,console,parsingInterface);
    }
    public  void averageOfManufactureCost(String arg, Root root,Console console) throws CommandWithoutArguments {
        if (isArguments(arg)) {
            throw new CommandWithoutArguments("Данная команда не принимает аргументы", console);
        }
        averageOfManufactureCost.averageOfManufactureCost(root,console);
    }
    public  void groupCountingByPrice(String arg, Root root,Console console) throws CommandWithoutArguments {
        if (isArguments(arg)) {
            throw new CommandWithoutArguments("Данная команда не принимает аргументы", console);
        }
        groupCountingByPrice.groupCountingByPrice(root);
    }


    private  boolean isArguments(String arg){
        return !arg.equals("");
    }

    public History getHistory() {
        return history;
    }
}