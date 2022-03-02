package test.laba.client.commands;

import test.laba.client.console.*;
import test.laba.client.console.Console;
import test.laba.client.exception.CommandWithoutArguments;
import test.laba.client.mainClasses.Root;

import java.io.*;


public class ExecuteScript extends AbstractCommand {
    private boolean flag = true;
    public ExecuteScript() {
        super("ExecuteScript", "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
    }
    public void execute(String fileName, Root root, FileManager fileManager, Console console, ConsoleParsing parsingInterface) throws CommandWithoutArguments {
        BufferedReader reader = null;
        try {
            File file = new File(fileName);
            FileReader fr = new FileReader(file);
            reader = new BufferedReader(fr);
            ScriptConsole scriptConsole = new ScriptConsole(reader, console);
            SaveCollection saveCollection = new SaveCollection();
            CommandsManager commandsManager = new CommandsManager(saveCollection,scriptConsole);
            fileManager.readScript(reader, root, commandsManager, fileManager, console, parsingInterface, scriptConsole);
        } catch (FileNotFoundException e) {
            console.printError("Файл не найден, проверьте путь к нему");
        } catch ( NullPointerException e) {
            console.printError("Файл не найден, проверьте путь к нему");
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                console.printError("Невозможно закрыть файл!");
            }
        }

    }
}
