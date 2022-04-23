package test.laba.common.IO;

import test.laba.common.commands.Root;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
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

    public String read(){
          return userScanner.nextLine().trim().toLowerCase();
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
