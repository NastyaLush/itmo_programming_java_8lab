package test.laba.client.commands;


import test.laba.client.console.Console;
import test.laba.client.console.ConsoleParsing;
import test.laba.client.mainClasses.Root;

public class RemoveKey extends AbstractCommand {
    public RemoveKey() {
        super("RemoveKey", "удалить элемент из коллекции по его ключу");
    }
    public void execute(String arg, Root root, Console console, ConsoleParsing parsingInterface)  {
        Long key = parsingInterface.toLongNumber(arg, console);
        root.remove(key);
        console.print("Объект удален");


    }
}
