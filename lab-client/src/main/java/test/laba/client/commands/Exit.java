package test.laba.client.commands;


import test.laba.client.console.Console;

public class Exit extends AbstractCommand {
    public Exit() {
        super("Exit", "завершить программу (без сохранения в файл)");
    }
    public void execute(Console console) {
        console.print("Работа программы завершена");
    }
}
