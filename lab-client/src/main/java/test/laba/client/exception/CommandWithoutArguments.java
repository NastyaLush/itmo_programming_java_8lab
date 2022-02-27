package test.laba.client.exception;


import test.laba.client.console.Console;

public class CommandWithoutArguments extends RuntimeException {
    public CommandWithoutArguments(String message, Console console) {
       console.printError(message);
    }
}
