package test.laba.client.commands;

import test.laba.client.dataClasses.Root;

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
    public String execute(Root root) {
        return root.infoAboutCollection();
    }
}
