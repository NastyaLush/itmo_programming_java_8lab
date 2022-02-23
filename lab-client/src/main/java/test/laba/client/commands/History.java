package test.laba.client.commands;



import test.laba.client.console.Console;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

public class History extends AbstractCommand {
    private Deque<String> history = new ArrayDeque<>();
    private Integer i=0;
    public History(){
        super("History","вывести последние 10 команд (без их аргументов)");
    }

    public  void addToHistory(String s){
        history.add(s);
        i++;
        if(i>10){
            history.pollFirst();
        }
    }

    public Queue<String> getHistory() {
        return history;
    }
    public  void  history(Console console){
        console.print("History: "+history);
    }
}
