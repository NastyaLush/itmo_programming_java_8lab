package test.laba.client.util;

import test.laba.common.IO.Colors;
import test.laba.common.util.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * the class is responsible for work with console and choose command for run
 */
public final class Console {
    private static final BufferedReader USER_SCANNER = new BufferedReader(new InputStreamReader(System.in));

    private Console() {
    }

    /**
     * scan line from console
     *
     * @return scanning string
     */
    public static String scanner() {
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
    public static void print(Object object) {
        System.out.println(object);
    }

    /**
     * print the argument with red text
     *
     * @param object object for printing with red text
     */
    public static void printError(Object object) {
      Util.toColor(object.toString(), Colors.RED);
    }

    /**
     * print the argument
     *
     * @param object object for printing
     */
    public static void ask(Object object) {
        System.out.println(object);
    }

    public static String read() {
        try {
            return USER_SCANNER.readLine().trim().toLowerCase();
        } catch (IOException | NullPointerException e) {
            return null;
        }
    }


    /**
     * print question and return true if answer yes, another case false
     *
     * @param question question for answering
     * @return true if answer yes, false if answer no
     */
    public static boolean askQuestion(String question) {
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
     *
     * @param question question for answering
     * @return answer
     */
    public static String askFullQuestion(String question) {
        ask(question);
        return scanner();
    }

}
