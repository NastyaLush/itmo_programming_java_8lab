package test.laba.client.exception;


import test.laba.client.console.Console;

public class CommandWithArguments extends RuntimeException{
    public CommandWithArguments(String message, Console console) {
        console.printError(message);
    }
}
