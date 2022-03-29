package test.laba.client.console;

import test.laba.client.exception.ScriptError;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * the class is responsible for work with script
 */
public class ScriptConsole extends Console {
    private final BufferedReader reader;
    private final FileReader fileReader;

    /**
     * the constructor accept file for reading and buffered reader
     * @param reader buffered reader
     * @param fileReader file reader
     */
    public ScriptConsole(BufferedReader reader, FileReader fileReader) {
        this.reader = reader;
        this.fileReader = fileReader;
    }

    /**
     * scan a line from script
     * @return line from script
     */
    public String scanner()  {
        String command;
        try {
            command = (reader.readLine().trim());
            return command;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * print argument to console
     * @param object object for print
     */
    public void print(Object object) {
        System.out.println(object);
    }

    /**
     * print error on console and close file and buffered reader
     * @param object object for print with red text
     */
    public void  printError(Object object)  {
        System.out.println(Colors.RED + object.toString() + Colors.END);
        try {
            fileReader.close();
            reader.close();
        } catch (IOException e) {
            super.printError("Ошибка при выполнении скрипта");
        } finally {
            throw new ScriptError();
        }
    }

    /**
     * to nothing override from parent class
     * @param object object for asking
     */
    public void ask(Object object) {
    }

    /**
     * ask question and return answer
     * @param question question for asking
     * @return answer
     */
    public String askFullQuestion(String question) {
        ask(question);
        return scanner();
    }

    /**
     * ask question and return true if answer was yes, another case false
     * @param question question for asking
     * @return answer
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
}
