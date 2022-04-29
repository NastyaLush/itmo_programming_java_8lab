package test.laba.server.mycommands;

import test.laba.server.mycommands.commands.AbstractCommand;
import test.laba.common.responses.Response;


/**
 * exit command
 */
public class Exit extends AbstractCommand {
    public Exit() {
        super("Exit", "завершить программу (без сохранения в файл)");
    }

    /**
     * returns exit
     */
    public Response execute(String arg, Root root) {
        return new Response("exit");
    }
}
