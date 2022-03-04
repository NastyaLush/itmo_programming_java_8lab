package test.laba.client.console;

import test.laba.client.exception.ScriptError;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ScriptConsole extends Console {
    private final BufferedReader reader;
    private FileReader fileReader;

    public ScriptConsole(BufferedReader reader, FileReader fileReader) {
        this.reader = reader;
        this.fileReader = fileReader;
    }
    public String scanner()  {
        String command;
        try {
            command = (reader.readLine().trim());
            return command;
        } catch (IOException e) {
            return null;
        }
    }
    public void print(Object object) {
        System.out.println(object);
    }
    public void  printError(Object object)  {
        System.out.println(ansiRed + object + ansiReset);
        try {
            fileReader.close();
            reader.close();
        } catch (IOException e) {
            super.printError("Ошибка при выполнении скрипта");
        } finally {
            throw new ScriptError();
        }
    }
    public void ask(Object object) {
    }
    public String askFullQuestion(String s) {
        ask(s);
        return scanner();
    }
    public boolean askQuestion(String s) {
        String answer;
        ask(s);
        answer = scanner().toLowerCase();
        if ("да".equals(answer) || "yes".equals(answer)) {
            return true;
        }
        if ("нет".equals(answer) || "no".equals(answer)) {
            return false;
        }
        printError("Ответ не распознан, пожалуйста введите да или нет");
        return askQuestion(s);
    }
}
