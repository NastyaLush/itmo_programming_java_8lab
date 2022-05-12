package test.laba.common.exception;

public class AlreadyExistLogin extends Exception {
    private final String message;

    public AlreadyExistLogin(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
