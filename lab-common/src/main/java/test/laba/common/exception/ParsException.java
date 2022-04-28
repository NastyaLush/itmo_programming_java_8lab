package test.laba.common.exception;

public class ParsException extends Exception {
    private final String message;
    public ParsException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
