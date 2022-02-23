package test.laba.client.console;


import test.laba.client.exception.CommandWithArguments;
import test.laba.client.exception.CommandWithoutArguments;
import test.laba.client.exception.exucuteError;
import test.laba.client.mainClasses.Root;

import java.util.Scanner;


public class Console {
    String ANSI_RESET = "\u001B[0m";
    String ANSI_RED = "\u001B[31m";
    Scanner userScanner= new Scanner(System.in);
    private boolean flag=true;
    public String scanner()  {
        return userScanner.nextLine();
    }
    public void print(Object object){
      System.out.println(object);
    }
    public void  printError(Object object) throws exucuteError {
        System.out.println(ANSI_RED+object+ANSI_RESET);
    }
    public void  ask(Object object){
        System.out.println(object);
    }

    public void interactivelyMode(Root root, CommandsManager commandsManager, FileManager fileManager, Console console, ConsoleParsing parsingInterface) throws CommandWithoutArguments, exucuteError {
        print("Программа в интерактивном режиме, для получения информации о возможностях, введите help ");
        String[] command;
        boolean flag=true;
        while (flag) {
            command = (userScanner.nextLine().trim()+ " ").split(" ", 3);
            for(int i=2;i<command.length;i++){
                command[1]+=" "+command[i];
                command[i]=command[i].trim();
            }
            command[1] = command[1].trim();
            command[0]= command[0].toLowerCase();
            commandsManager.getHistory().addToHistory(command[0]);
            flag= command(command, root,commandsManager,fileManager,console,parsingInterface);
            System.out.println("команда обработана");
        }

    }
    public boolean command(String[] command, Root root, CommandsManager commandsManager, FileManager fileManager, Console console, ConsoleParsing consoleParsing) throws CommandWithoutArguments, exucuteError {
       try {
            switch (command[0].trim()) {
                case "help" -> {
                    commandsManager.help(command[1],console);
                    return true;
                }
                case "info" -> {
                    commandsManager.info(command[1], root,console);
                    return true;
                }
                case "show" -> {
                    commandsManager.show(command[1], root,console);
                    return true;
                }
                case "insert" -> {
                    commandsManager.insertNull(command[2].trim(), root,console,consoleParsing);
                    return true;
                }
               /* case "update" -> {
                    commandsManager.updateID(command[1], root,console,consoleParsing);
                    return true;
                }
                case "remove_key" -> {
                    commandsManager.removeKey(command[1], root,console,consoleParsing);
                    return true;
                }
                case "clear" -> {
                    commandsManager.clear(command[1], root,console);
                    return true;
                }
                case "save" -> {
                    commandsManager.save(command[1], root, fileManager,console,consoleParsing);
                    return true;
                }*/
                case "execute_script" -> {
                    commandsManager.executeScript(command[1], root, fileManager,console,consoleParsing);
                    return true;
                }/*
                case "remove_lower" -> {
                    commandsManager.removeLower(command[1], root,console,consoleParsing);
                    return true;
                }
                case "history" -> {
                    commandsManager.history(command[1], console);
                    return true;
                }
                case "remove_lower_key" -> {
                    commandsManager.removeLowerKey(command[1], root,console,consoleParsing);
                    return true;
                }
                case "remove_any_by_unit_of_measure" -> {
                    commandsManager.removeAnyByUnitOfMeasure(command[1], root,console,consoleParsing);
                    return true;
                }
                case "average_of_manufacture_cost" -> {
                    commandsManager.averageOfManufactureCost(command[1], root,console);
                    return true;
                }
                case "group_counting_by_price" -> {
                    commandsManager.groupCountingByPrice(command[1], root,console);
                    return true;
                }
                case "exit" -> {
                    commandsManager.exit(command[1], console);
                    return false;
                }*/
                default -> {
                    console.printError("Данной команды не существует, проверьте корректность данных или введите help для получения списка команд,вы ввели " + command[0].trim());
                    return true;
                }
            }
        }
       catch (CommandWithoutArguments | CommandWithArguments e){
           return true;
       }
        }
    public boolean askQuestion(String s) throws exucuteError {
        String answer;
        print(s);
        answer= scanner().toLowerCase();
        if(answer.equals("да")||answer.equals("yes")){
            return true;
        }
        if(answer.equals("нет")||answer.equals("no")){
            return false;
        }
        printError("Ответ не распознан, пожалуйста введите да или нет");
        return askQuestion(s);
    }
    public String askFullQuestion(String s){
        print(s);
        return scanner();
    }

    public boolean isFlag() {
        return flag;
    }
}
