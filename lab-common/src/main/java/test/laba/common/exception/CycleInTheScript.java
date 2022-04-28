package test.laba.common.exception;

public class CycleInTheScript extends Exception {
    private final String message;

    public CycleInTheScript(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

