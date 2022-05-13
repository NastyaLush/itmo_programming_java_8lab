package test.laba.server.mycommands;

import test.laba.server.mycommands.commands.AbstractCommand;
import test.laba.common.IO.Colors;
import test.laba.common.responses.Response;

import java.io.Serializable;
import java.util.Collection;

/**
 * help command
 */
public class Help extends AbstractCommand implements Serializable {
    private Collection<AbstractCommand> commands;
    public Help() {
        super("Help", "вывести справку по доступным командам");
    }

    /**
     * output help for available commands
     * @return string with output help for available commands
     */
    public Response execute(String arg, Root root) {
        StringBuilder s = new StringBuilder();
        commands.forEach(command -> s.append(Colors.BlUE).append(command.getName()).append(Colors.END)
                .append(": ").append(command.getDescription()).append('\n'));
        return new Response(s.toString());
    }

    public void setCommands(Collection<AbstractCommand> commands) {
        this.commands = commands;
    }
}
