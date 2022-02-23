package test.laba.client.commands;


import test.laba.client.console.CommandsManager;
import test.laba.client.console.Console;
import test.laba.client.console.ConsoleParsing;
import test.laba.client.console.FileManager;
import test.laba.client.exception.CommandWithoutArguments;
import test.laba.client.exception.CreateError;
import test.laba.client.exception.exucuteError;
import test.laba.client.mainClasses.Root;

public class ExecuteScript extends AbstractCommand {
    public ExecuteScript(){
        super("ExecuteScript",": считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
    }
    public void executeScript(String arg, Root root, CommandsManager commandsManager, FileManager fileManager, Console console, ConsoleParsing parsingInterface) throws CommandWithoutArguments {
        fileManager.readScript(arg, root,commandsManager,fileManager,console,parsingInterface,this);
    }
}
