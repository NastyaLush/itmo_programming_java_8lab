package test.laba.client.exception;

import test.laba.client.console.Console;

public class CreateError extends Exception{
    public CreateError(String message, Console console){
        console.printError(message);
    }
}
