package test.laba.client.commands;

import test.laba.client.console.Colors;
import test.laba.client.dataClasses.Root;
import java.util.Collection;

/**
 * help command
 */
public class Help extends AbstractCommand {
    private final Collection<AbstractCommand> commands;
    public Help(Collection<AbstractCommand> commands) {
        super("Help", "вывести справку по доступным командам");
        this.commands = commands;
    }

    /**
     * output help for available commands
     * @return string with output help for available commands
     */
    public String execute(String arg, Root root) {
        String s = "";
        for (AbstractCommand command:commands) {
            s +=  Colors.BlUE + command.getName() + Colors.END + ": " + command.getDescription() + '\n';
        }
        return s;
    }
}
