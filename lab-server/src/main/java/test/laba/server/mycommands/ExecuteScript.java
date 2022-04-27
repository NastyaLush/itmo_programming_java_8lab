package test.laba.server.mycommands;


import test.laba.common.commands.AbstractCommand;
import test.laba.common.commands.Root;
import test.laba.common.util.Response;
import test.laba.common.util.ResponseWithError;
import test.laba.common.util.Values;
import test.laba.server.ServerApp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ResourceBundle;


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
    public Response execute(String arguments, Root root) {
        return new Response(Values.SCRIPT.toString(), arguments);
    }

    /**
     * execute script
     * @param root object contained collection values
     * @throws IOException throws if work with file is impossible
     */

}
