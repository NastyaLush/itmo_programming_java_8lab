package test.laba.client.commands;

import test.laba.client.dataClasses.Root;

/**
 * exit command
 */
public class Exit extends AbstractCommand {
    public Exit() {
        super("Exit", "завершить программу (без сохранения в файл)");
    }

    /**
     * do nothing
     */
    public String execute(String arg, Root root) {
        return "Thank you for using";
    }
}
