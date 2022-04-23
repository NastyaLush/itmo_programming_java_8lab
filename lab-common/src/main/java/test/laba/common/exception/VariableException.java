package test.laba.common.exception;


import test.laba.common.IO.Console;

public class VariableException extends Exception {
    public VariableException(String message, Console console) {
        console.printError(message);
    }
    public VariableException(String message) {
        System.out.println(message);
    }
}
