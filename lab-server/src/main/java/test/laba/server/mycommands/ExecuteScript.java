package test.laba.server.mycommands;


import test.laba.common.commands.AbstractCommand;
import test.laba.common.commands.Root;

import java.io.IOException;


/**
 * execute script command
 */
public class ExecuteScript extends AbstractCommand {
   // private final FileManager fileManager;
    public ExecuteScript() {
        super("Execute_script", "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
       // this.fileManager = fileManager;
    }

    @Override
    public String execute(String arguments, Root root) {
        return null;
    }

    /**
     * execute script
     * @param fileName name file for work
     * @param root object contained collection values
     * @throws IOException throws if work with file is impossible
     */
    /*public String execute(String fileName, Root root) {
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
    }*/
}
