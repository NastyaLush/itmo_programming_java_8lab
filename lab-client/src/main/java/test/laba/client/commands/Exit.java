package test.laba.client.commands;

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
    public void execute() {
    }
}
