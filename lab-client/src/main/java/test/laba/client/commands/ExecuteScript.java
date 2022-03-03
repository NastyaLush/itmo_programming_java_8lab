package test.laba.client.commands;

import test.laba.client.console.CommandsManager;
import test.laba.client.console.ConsoleParsing;
import test.laba.client.console.FileManager;
import test.laba.client.console.SaveCollection;
import test.laba.client.console.ScriptConsole;
import test.laba.client.exception.CommandWithoutArguments;
import test.laba.client.mainClasses.Root;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class ExecuteScript extends AbstractCommand {
    public ExecuteScript() {
        super("ExecuteScript", "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
    }
    public void execute(String fileName, Root root, FileManager fileManager, ConsoleParsing parsingInterface) throws CommandWithoutArguments, IOException {
        BufferedReader reader = null;
        try {
            File file = new File(fileName);
            FileReader fr = new FileReader(file);
            reader = new BufferedReader(fr);
            ScriptConsole scriptConsole = new ScriptConsole(reader);
            SaveCollection saveCollection = new SaveCollection();
            CommandsManager commandsManager = new CommandsManager(saveCollection, scriptConsole);
            fileManager.readScript(reader, root, commandsManager, fileManager, parsingInterface, scriptConsole);
        }  finally {
            reader.close();
        }

    }
}
