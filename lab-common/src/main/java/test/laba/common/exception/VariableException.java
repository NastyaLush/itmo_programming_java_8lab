package test.laba.common.exception;

public class VariableException extends Exception {
    private final String message;

    public VariableException(String message) {
        this.message = message;
    }

    public VariableException(String name, String message) {
        this.message = name + ": " + message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
