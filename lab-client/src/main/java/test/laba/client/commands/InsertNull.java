package test.laba.client.commands;


import test.laba.client.console.Console;
import test.laba.client.console.ConsoleParsing;
import test.laba.client.exception.exucuteError;
import test.laba.client.mainClasses.Root;

public class InsertNull extends AbstractCommand {
    public InsertNull(){
        super("InsertNull","добавить новый элемент с заданным ключом");
    }
    public void insertnull(Root root, String arg, Console console, ConsoleParsing consoleParsing) throws exucuteError {
         consoleParsing.parsProductFromConsole(root,arg,console);
    }

}
