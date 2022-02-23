package test.laba.client.commands;


import test.laba.client.console.Console;
import test.laba.client.exception.CommandWithoutArguments;
import test.laba.client.mainClasses.Root;

public class Info extends AbstractCommand {
    public Info(){
        super("info","вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
    }

    public void info(Root root, Console console) throws CommandWithoutArguments {
        console.print("Class of collection: "+root.getProducts().getClass()+
                "\nDate of initialization: "+root.getDateOfLastModification()+
                "\nNumber of elements: "+root.getProducts().size());
    }
}
