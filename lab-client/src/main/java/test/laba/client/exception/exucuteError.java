package test.laba.client.exception;

import test.laba.client.console.Console;

public class exucuteError extends Throwable {
    public exucuteError(String message, Console console) throws exucuteError {
        console.printError(message);
    }
}
