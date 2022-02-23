package test.laba.client.exception;


import test.laba.client.console.Console;

public class IDError extends RuntimeException {
    public IDError(String message, Console console){
        console.printError(message);
    }
}
