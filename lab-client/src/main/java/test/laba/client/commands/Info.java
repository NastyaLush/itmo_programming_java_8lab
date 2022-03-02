package test.laba.client.commands;


import test.laba.client.console.Console;
import test.laba.client.exception.CommandWithoutArguments;
import test.laba.client.mainClasses.Root;

public class Info extends AbstractCommand {
    public Info() {
        super("Info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
    }

    public String execute(Root root) throws CommandWithoutArguments {
        return root.infoAboutCollection();
    }
}
