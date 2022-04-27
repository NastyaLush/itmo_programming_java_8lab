package test.laba.server.mycommands;

import test.laba.common.commands.AbstractCommand;
import test.laba.common.IO.Colors;
import test.laba.common.commands.Root;
import test.laba.common.util.Response;

import java.io.Serializable;
import java.util.Collection;

/**
 * help command
 */
public class Help extends AbstractCommand implements Serializable {
    private final Collection<AbstractCommand> commands;
    public Help(Collection<AbstractCommand> commands) {
        super("Help", "вывести справку по доступным командам");
        this.commands = commands;
    }

    /**
     * output help for available commands
     * @return string with output help for available commands
     */
    public Response execute(String arg, Root root) {
        StringBuilder s = new StringBuilder();
        commands.forEach(command -> s.append(Colors.BlUE + command.getName() + Colors.END + ": " + command.getDescription() + '\n'));
        return new Response(s.toString());
    }
}
