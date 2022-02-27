package test.laba.client.commands;



import test.laba.client.console.Console;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

public class History extends AbstractCommand {
    private Deque<String> history = new ArrayDeque<>();
    private Integer i = 0;
    private final int numberOfProducts = 10;
    public History() {
        super("History", "вывести последние 10 команд (без их аргументов)");
    }

    public  void addToHistory(String s) {
        history.add(s);
        i++;
        if (i > numberOfProducts) {
            history.pollFirst();
        }
    }

    public Queue<String> getHistory() {
        return history;
    }
    public  void execute(Console console) {
        console.print("History: " + history);
    }
}
