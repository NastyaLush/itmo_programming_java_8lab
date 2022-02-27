package test.laba.client.commands;

import test.laba.client.console.ConsoleParsing;
import test.laba.client.console.CommandsManager;
import test.laba.client.console.Console;
import test.laba.client.console.FileManager;
import test.laba.client.console.ScriptConsole;
import test.laba.client.exception.CommandWithoutArguments;
import test.laba.client.mainClasses.Root;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;


public class ExecuteScript extends AbstractCommand {
    private boolean flag = true;
    public ExecuteScript() {
        super("ExecuteScript", ": считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
    }
    public void execute(String fileName, Root root, CommandsManager commandsManager, FileManager fileManager, Console console, ConsoleParsing parsingInterface) throws CommandWithoutArguments {

        try {
            File file = new File(fileName);
            FileReader fr;
            fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            ScriptConsole scriptConsole = new ScriptConsole(reader, console);
            fileManager.readScript(reader, root, commandsManager, fileManager, console, parsingInterface, scriptConsole);
        } catch (FileNotFoundException | NullPointerException e) {
            console.printError("Файл не найден, проверьте путь к нему");
        }
    }
}
