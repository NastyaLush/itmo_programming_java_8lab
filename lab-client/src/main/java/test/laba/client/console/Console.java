package test.laba.client.console;

import test.laba.client.dataClasses.Root;
import java.util.Scanner;

/**
 * the class is responsible for work with console and choose command for run
 */
public class Console {
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

    public String toColor(String s, Colors colors) {
        return colors + s + Colors.END;
    }
    /**
     * print the argument with red text
     * @param object object for printing with red text
     */
    public void  printError(Object object) {
        System.out.println(toColor(object.toString(), Colors.RED));
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
     * @param console object worked with console
     */
    public void interactivelyMode(Root root, CommandsManager commandsManager, Console console) {
        print("Программа в интерактивном режиме, для получения информации о возможностях, введите help ");
        String[] command;
        int numberOfCommand = 0;
        int numberOfArguments = 1;
        int numberOfBeginTrim = 2;
        final int limit = 3;
        String answer;
        while (userScanner.hasNext()) {
                command = (userScanner.nextLine().trim() + " ").split(" ", limit);
                for (int i = numberOfBeginTrim; i < command.length; i++) {
                    command[numberOfArguments] += " " + command[i];
                    command[i] = command[i].trim();
                }
                command[numberOfArguments] = command[numberOfArguments].trim();
                command[numberOfCommand] = command[numberOfCommand].toLowerCase();
                commandsManager.getHistory().addToHistory(command[numberOfCommand]);
                answer = commandsManager.chooseCommand(command, root);
                console.print(answer);
                if ("Thank you for using".equals(answer)) {
                    break;
                }
        }

    }

    /**
     * print question and return true if answer yes, another case false
     * @param question question for answering
     * @return true if answer yes, false if answer no
     */
    public boolean askQuestion(String question) {
        String answer;
        ask(question);
        answer = scanner().toLowerCase().trim();
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
