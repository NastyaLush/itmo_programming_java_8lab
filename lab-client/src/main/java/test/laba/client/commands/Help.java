package test.laba.client.commands;

import java.util.List;


public class Help extends AbstractCommand {
    private final List<AbstractCommand> commands;
    public Help(List<AbstractCommand> commands) {
        super("Help", "вывести справку по доступным командам");
        this.commands = commands;
    }
    public String execute() {
        StringBuilder s = null;
        for (AbstractCommand command:commands) {
            s = (s == null ? new StringBuilder("null") : s).append(command.getName()).append(": ").append(command.getDescription()).append('\n');
        }
        return s == null ? null : s.toString();
    }
}
