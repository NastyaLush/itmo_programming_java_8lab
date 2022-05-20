package test.laba.common.exception;

public class NoConnectionWithDB extends RuntimeException {
    private final String message;

    public NoConnectionWithDB(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
