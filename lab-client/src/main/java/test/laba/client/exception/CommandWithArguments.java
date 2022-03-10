package test.laba.client.exception;


import test.laba.client.console.Console;

public class CommandWithArguments extends Exception {
    public CommandWithArguments(String message, Console console) {
        console.printError(message);
    }
}
