package test.laba.common.exception;

public class VariableException extends Exception {
    private final String message;
    public VariableException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
