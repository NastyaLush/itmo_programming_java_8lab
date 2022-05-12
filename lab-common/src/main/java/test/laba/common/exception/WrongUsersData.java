package test.laba.common.exception;

public class WrongUsersData extends Exception {
    private final String message;

    public WrongUsersData(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
