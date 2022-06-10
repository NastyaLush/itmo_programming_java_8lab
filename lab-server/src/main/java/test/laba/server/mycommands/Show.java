package test.laba.server.mycommands;

import test.laba.server.mycommands.commands.AbstractCommand;
import test.laba.common.responses.Response;


/**
 * show command
 */
public class Show extends AbstractCommand {
    public Show() {
        super("Show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
    }

    /**
     * show collection values
     *
     * @param root object contained collection values
     * @return string with info about values
     */
    @Override
    public Response execute(String arg, Root root) {
        Response response = new Response(root.showCollection());
        response.setProductHashMap(root.getProducts());
        return response;
    }
}
