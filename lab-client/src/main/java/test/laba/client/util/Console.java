package test.laba.client.util;

import test.laba.common.IO.Colors;
import test.laba.common.exception.ScriptError;
import test.laba.common.util.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * the class is responsible for work with console and choose command for run
 */
public class Console {
    private static final BufferedReader USER_SCANNER = new BufferedReader(new InputStreamReader(System.in));


    /**
     * scan line from console
     *
     * @return scanning string
     */
    public String scanner() {
        try {
            return USER_SCANNER.readLine();
        } catch (IOException e) {
            return null;
        }

    }

    /**
     * print the argument
     *
     * @param object object for printing
     */
    public void print(Object object) {
        System.out.println(object);
    }

    /**
     * print the argument with red text
     *
     * @param object object for printing with red text
     */
    public void printError(Object object) throws ScriptError {
        Util.toColor(object.toString(), Colors.RED);
    }

    /**
     * print the argument
     *
     * @param object object for printing
     */
    public void ask(Object object) {
        System.out.println(object);
    }

    public String read() {
        try {
            return USER_SCANNER.readLine().trim().toLowerCase();
        } catch (IOException | NullPointerException e) {
            return null;
        }
    }


    /**
     * print question and return true if answer yes, another case false
     *
     * @return true if answer yes, false if answer no
     */
    public boolean askQuestion(/*String question*/) {
        String answer = null;
        try {
            answer = scanner().toLowerCase().trim();
        } catch (NullPointerException e) {
            printError("Вы не можете ввести null!!!!!!!!");
        }
        if ("да".equals(answer) || "yes".equals(answer)) {
            return true;
        }
        if ("нет".equals(answer) || "no".equals(answer)) {
            return false;
        }
        printError("Ответ не распознан, пожалуйста введите да или нет");
        return askQuestion(/*question*/);
    }

    /**
     * print question and return answer
     *
     * @return answer
     */
    public String askFullQuestion(/*String question*/) {
       // ask(question);
        try {
            return scanner().toLowerCase().trim();
        } catch (NullPointerException e) {
            printError("Вы не можете ввести null!");
        }
        return askFullQuestion(/*question*/);
    }

    public int isAvailable() throws IOException {
        return System.in.available();
    }

}
