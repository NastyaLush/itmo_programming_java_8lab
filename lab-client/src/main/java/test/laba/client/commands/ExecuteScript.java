package test.laba.client.commands;

import test.laba.client.console.CommandsManager;
import test.laba.client.console.Console;
import test.laba.client.console.ConsoleParsing;
import test.laba.client.console.FileManager;
import test.laba.client.console.ScriptConsole;
import test.laba.client.exception.ScriptError;
import test.laba.client.dataClasses.Root;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


/**
 * execute script command
 */
public class ExecuteScript extends AbstractCommand {
    private final Console console;
    private final FileManager fileManager;
    public ExecuteScript(Console console, FileManager fileManager) {
        super("Execute_script", "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
        this.console = console;
        this.fileManager = fileManager;
    }

    /**
     * execute script
     * @param fileName name file for work
     * @param root object contained collection values
     * @throws IOException throws if work with file is impossible
     */
    public String execute(String fileName, Root root) {
        try (FileReader fr = new FileReader(fileName.trim())) {
            BufferedReader reader = new BufferedReader(fr);
            ScriptConsole scriptConsole = new ScriptConsole(reader, fr);
            ConsoleParsing consoleParsing = new ConsoleParsing(scriptConsole);
            CommandsManager commandsManager = new CommandsManager(scriptConsole, consoleParsing, fileManager);
            if (!root.containsInStack(fileName)) {
                root.addToStack(fileName);
                console.print("Идет выполнение скрипта: " + fileName);
                fileManager.readScript(reader, root, commandsManager, scriptConsole);
            } else {
                root.cleanStack();
                console.printError("обнаружен цикл, невозможно выполнить скрипт");
            }
            root.deleteFromStack(fileName);
        } catch (FileNotFoundException e) {
            console.printError("Файл не найден, проверьте путь или права:" + fileName);
        } catch (IOException e) {
            console.printError("не удалось выполнить скрипт");
        } catch (ScriptError e) {
           // console.printError("");
        } catch (StackOverflowError e) {
            console.printError("в цикле появилась бесконечный цикл");
        }
        return "script is executed";
    }
}
