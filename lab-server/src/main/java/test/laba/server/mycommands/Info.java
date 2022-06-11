package test.laba.server.mycommands;

import test.laba.server.mycommands.commands.AbstractCommand;
import test.laba.common.responses.Response;


/**
 * info command
 */
public class Info extends AbstractCommand {
    public Info() {
        super("Info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
    }

    /**
     * output information about the collection (type, initialization date, number of items, etc.) to the standard output stream.
     * @param root object contained collection values
     * @return string with information
     */
    @Override
    public Response execute(String arg, Root root) {
        return new Response("Information about collection\n" + root.infoAboutCollection());
    }
}
