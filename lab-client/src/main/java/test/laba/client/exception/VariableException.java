package test.laba.client.exception;


import test.laba.client.console.Console;

public class VariableException extends RuntimeException {
    public VariableException(String message, Console console) {
        console.printError(message);
    }
}
