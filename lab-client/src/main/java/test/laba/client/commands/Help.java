package test.laba.client.commands;

import test.laba.client.console.Console;
import java.util.List;

public class Help extends AbstractCommand {
    public Help() {
        super("Help", "вывести справку по доступным командам");
    }
    public void execute(List<AbstractCommand> commands, Console console) {
        for (AbstractCommand command:commands) {
            console.print(command.getName() + ": " + command.getDescription());
        }
    }
}
