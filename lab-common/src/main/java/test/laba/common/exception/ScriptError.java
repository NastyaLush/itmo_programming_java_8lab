package test.laba.common.exception;

public class ScriptError extends RuntimeException {
    private final String message;
    public ScriptError(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
