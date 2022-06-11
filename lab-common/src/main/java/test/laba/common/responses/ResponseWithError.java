package test.laba.common.responses;


public class ResponseWithError extends Response {
    private static final long serialVersionUID = 10;

    public ResponseWithError(String message) {
        super(message);
    }
}
