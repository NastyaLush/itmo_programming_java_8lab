package test.laba.common.responses;

import test.laba.common.IO.Colors;

public class ResponseWithError extends Response {
    private static final long serialVersionUID = 10;

    public ResponseWithError(String message) {
        super(Colors.RED + message + Colors.END);
    }
}
