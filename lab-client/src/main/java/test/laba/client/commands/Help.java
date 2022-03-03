package test.laba.client.commands;

import java.util.List;


public class Help extends AbstractCommand {
    private final List<AbstractCommand> commands;
    public Help(List<AbstractCommand> commands) {
        super("Help", "вывести справку по доступным командам");
        this.commands = commands;
    }
    public String execute() {
        String s = "";
        for (AbstractCommand command:commands) {
            s += command.getName() + ": " + command.getDescription() + '\n';
        }
        return s;
    }
}
