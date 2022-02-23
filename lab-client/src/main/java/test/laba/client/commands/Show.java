package test.laba.client.commands;


import test.laba.client.console.Console;
import test.laba.client.mainClasses.Root;

public class Show extends AbstractCommand {
    public Show(){
        super("show","вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
    }
    public void show(Root root, Console console){
        console.print(root.getProducts());
    }
}
