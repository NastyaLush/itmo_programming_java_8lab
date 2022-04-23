package test.laba.common.exception;

import test.laba.common.IO.Console;

public class CreateError extends Exception {
    public CreateError(String message, Console console) {
        console.printError(message);
    }
    public CreateError(String message) {
        System.out.println(message);
    }
}
