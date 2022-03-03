package test.laba.client.console;

import java.io.BufferedReader;
import java.io.IOException;

public class ScriptConsole extends Console {
    private final BufferedReader reader;

    public ScriptConsole(BufferedReader reader) {
        this.reader = reader;
    }
    public String scanner()  {
        String command;
        try {
            command = (reader.readLine().trim());
            return command;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "exit";
    }
    public void print(Object object) {
        System.out.println(object);
    }
    public void  printError(Object object)  {
        System.out.println(ansiRed + object + ansiReset);
        try {
            reader.close();
        } catch (IOException e) {
            super.printError("Ошибка при выполнении скрипта");
        }

    }
    public void ask(Object object) {
        System.out.println(object);
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
