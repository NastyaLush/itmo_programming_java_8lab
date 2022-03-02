package test.laba.client.commands;

import test.laba.client.console.Console;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Help extends AbstractCommand {
    private final List<AbstractCommand> commands;
    public Help( List<AbstractCommand> commands) {
        super("Help", "вывести справку по доступным командам");
        this.commands=commands;
    }
    public String execute() {
        String s = null;
        for (AbstractCommand command:commands) {
            s+= command.getName() + ": " + command.getDescription() + '\n';
        }
        return s;
    }
}
