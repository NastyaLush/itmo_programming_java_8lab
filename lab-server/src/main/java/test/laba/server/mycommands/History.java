package test.laba.server.mycommands;

import test.laba.common.IO.Colors;
import test.laba.server.mycommands.commands.AbstractCommand;
import test.laba.common.responses.Response;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * history command
 */
public class History extends AbstractCommand {
    private final Deque<String> history = new ArrayDeque<>();
    private Integer i = 0;
    private  final  int numberOfProducts = 10;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    public History() {
        super("History", "вывести последние 10 команд (без их аргументов)");
    }

    public  void addToHistory(String s) {
        lock.writeLock().lock();
        try {
            history.add(s);
            i++;
            if (i > numberOfProducts) {
                history.pollFirst();
                --i;
            }
        } finally {
            lock.writeLock().unlock();
        }

    }

    /**
     * output the last 10 commands (without their arguments)
     * @return string with the last 10 commands (without their arguments)
     */
    @Override
    public Response execute(String arg, Root root) {
        lock.readLock().lock();
        try {
            return new Response("History: " + history);
        } finally {
            lock.readLock().unlock();
        }
    }
}
