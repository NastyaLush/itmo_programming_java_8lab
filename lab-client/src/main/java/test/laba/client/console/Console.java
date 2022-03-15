package test.laba.client.console;


import test.laba.client.exception.CommandWithArguments;
import test.laba.client.exception.CommandWithoutArguments;
import test.laba.client.dataClasses.Root;
import java.util.Scanner;

/**
 * the class is responsible for work with console and choose command for run
 */
public class Console {
    protected final String ansiReset = "\u001B[0m";
    protected final String ansiRed = "\u001B[31m";
    private final Scanner userScanner = new Scanner(System.in);


    /**
     * scan line from console
     * @return scanning string
     */
    public String scanner()  {
        return userScanner.nextLine();
    }

    /**
     * print the argument
     * @param object object for printing
     */
    public void print(Object object) {
      System.out.println(object);
    }

    /**
     * print the argument with red text
     * @param object object for printing with red text
     */
    public void  printError(Object object) {
        System.out.println(ansiRed + object + ansiReset);
    }

    /**
     * print the argument
     * @param object object for printing
     */
    public void  ask(Object object) {
        System.out.println(object);
    }

    /**
     * separates command and arguments. transmits for implementations
     * @param root object contained collection
     * @param commandsManager object is responsible for implements command
     * @param fileManager object worked with file
     * @param console object worked with console
     * @param consoleParsing object is responsible for parsing from console
     * @throws CommandWithoutArguments throws if command accept wrong argument
     */
    public void interactivelyMode(Root root, CommandsManager commandsManager, FileManager fileManager, Console console, ConsoleParsing consoleParsing) throws CommandWithoutArguments {
        print("Программа в интерактивном режиме, для получения информации о возможностях, введите help ");
        String[] command;
        boolean flag = true;
        int numberOfCommand = 0;
        int numberOfArguments = 1;
        int numberOfBeginTrim = 2;
        final int limit = 3;
        while (flag && userScanner.hasNext()) {
                command = (userScanner.nextLine().trim() + " ").split(" ", limit);
                for (int i = numberOfBeginTrim; i < command.length; i++) {
                    command[numberOfArguments] += " " + command[i];
                    command[i] = command[i].trim();
                }
                command[numberOfArguments] = command[numberOfArguments].trim();
                command[numberOfCommand] = command[numberOfCommand].toLowerCase();
                commandsManager.getHistory().addToHistory(command[numberOfCommand]);
                flag = command(command, root, commandsManager, fileManager, console, consoleParsing);
                System.out.println("command finished");
        }

    }

    /**
     * chose command for implementation
     * @param command command name
     * @param root object contained collection
     * @param commandsManager object is responsible for implements command
     * @param fileManager object worked with file
     * @param console object worked with console
     * @param consoleParsing object is responsible for parsing from console
     * @throws CommandWithoutArguments throws if command accept wrong argument
     */
    public boolean command(String[] command, Root root, CommandsManager commandsManager, FileManager fileManager, Console console, ConsoleParsing consoleParsing) throws CommandWithoutArguments  {
       boolean flag;
        try {
            switch (command[0].trim()) {
                case "help":
                    commandsManager.help(command[1], console);
                    flag = true;
                    break;

                case "info" :
                    commandsManager.info(command[1], root, console);
                    flag = true;
                    break;

                case "show" :
                    commandsManager.show(command[1], root, console);
                    flag = true;
                    break;

                case "insert_null" :
                    commandsManager.insertNull(command[1].trim(), root, console, consoleParsing);
                    return true;

                case "update_id" :
                    commandsManager.updateID(command[1], root, console, consoleParsing);
                    flag = true;
                    break;


                default:
                    flag =  commandHelper(command, root, commandsManager, fileManager, console, consoleParsing);
                    break;

            }
        } catch (CommandWithoutArguments | CommandWithArguments e) {
           flag = true;
       }
        return flag;
    }
    private boolean commandHelper(String[] command, Root root, CommandsManager commandsManager, FileManager fileManager, Console console, ConsoleParsing consoleParsing) throws CommandWithoutArguments, CommandWithArguments {
        boolean flag = true;
        switch (command[0].trim()) {
            case "remove_key" :
                commandsManager.removeKey(command[1], root, console, consoleParsing);
                break;

            case "clear" :
                commandsManager.clear(command[1], root, console);
                break;

            case "save" :
                commandsManager.save(command[1], root, fileManager, console);
                break;
            case "remove_lower_key" :
                commandsManager.removeLowerKey(command[1], root, console, consoleParsing);
                break;

            case "remove_any_by_unit_of_measure" :
                commandsManager.removeAnyByUnitOfMeasure(command[1], root, console, consoleParsing);
                break;

            case "exit" :
                commandsManager.exit(command[1], console);
                flag = false;
                break;

            default:
                flag =  commandHelper2(command, root, commandsManager, fileManager, console, consoleParsing);
                break;
        }
        return flag;
    }
    public boolean commandHelper2(String[] command, Root root, CommandsManager commandsManager, FileManager fileManager, Console console, ConsoleParsing consoleParsing) throws CommandWithoutArguments, CommandWithArguments {
        switch (command[0].trim()) {
            case "execute_script":
                commandsManager.executeScript(command[1], root, fileManager, console);
                break;

            case "average_of_manufacture_cost" :
                commandsManager.averageOfManufactureCost(command[1], root, console);
                break;

            case "group_counting_by_price" :
                commandsManager.groupCountingByPrice(command[1], root, console);
                break;

            case "remove_lower":
                commandsManager.removeLower(command[1], root, console, consoleParsing);
                break;

            case "history":
                commandsManager.history(command[1], console);
                break;

            default:
                console.printError("Данной команды не существует, проверьте корректность данных или введите help для получения списка команд,вы ввели " + command[0].trim());
                break;
        }
        return true;
    }

    /**
     * print question and return true if answer yes, another case false
     * @param question question for answering
     * @return true if answer yes, false if answer no
     */
    public boolean askQuestion(String question) {
        String answer;
        ask(question);
        answer = scanner().toLowerCase();
        if ("да".equals(answer) || "yes".equals(answer)) {
            return true;
        }
        if ("нет".equals(answer) || "no".equals(answer)) {
            return false;
        }
        printError("Ответ не распознан, пожалуйста введите да или нет");
        return askQuestion(question);
    }

    /**
     * print question and return answer
     * @param question question for answering
     * @return answer
     */
    public String askFullQuestion(String question) {
        ask(question);
        return scanner();
    }

}
