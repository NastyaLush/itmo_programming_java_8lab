package test.laba.client.commands;

import test.laba.client.console.CommandsManager;
import test.laba.client.console.Console;
import test.laba.client.console.ConsoleParsing;
import test.laba.client.console.FileManager;
import test.laba.client.console.SaveCollection;
import test.laba.client.console.ScriptConsole;
import test.laba.client.exception.CommandWithoutArguments;
import test.laba.client.exception.ScriptError;
import test.laba.client.mainClasses.Root;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class ExecuteScript extends AbstractCommand {
    private Console console;
    public ExecuteScript(Console console) {
        super("Execute_script", "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
        this.console = console;
    }
    public void execute(String fileName, Root root, FileManager fileManager) throws CommandWithoutArguments, IOException {
        try (FileReader fr = new FileReader(fileName)) {
            BufferedReader reader = new BufferedReader(fr);
            ScriptConsole scriptConsole = new ScriptConsole(reader, fr);
            SaveCollection saveCollection = new SaveCollection();
            ConsoleParsing consoleParsing = new ConsoleParsing(scriptConsole);
            CommandsManager commandsManager = new CommandsManager(saveCollection, scriptConsole);
            fileManager.readScript(reader, root, commandsManager, fileManager, consoleParsing, scriptConsole);
        } catch (FileNotFoundException e) {
            console.printError("Файл не найден, проверьте путь: " + fileName);
        } catch (IOException e) {
            console.printError("не удалось выпллнить скрипт");
        } catch (ScriptError e) {
            console.printError("ошибка при выполнении скрипта");
        }

    }
}
