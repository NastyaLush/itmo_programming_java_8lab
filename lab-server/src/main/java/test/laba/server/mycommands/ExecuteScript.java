package test.laba.server.mycommands;


import test.laba.server.mycommands.commands.AbstractCommand;
import test.laba.server.Root;
import test.laba.common.responses.Response;
import test.laba.common.util.Values;



/**
 * execute script command
 */
public class ExecuteScript extends AbstractCommand {

    public ExecuteScript() {
        super("Execute_script", "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
    }

    @Override
    public Response execute(String arguments, Root root) {
        return new Response(Values.SCRIPT.toString(), arguments);
    }


}
