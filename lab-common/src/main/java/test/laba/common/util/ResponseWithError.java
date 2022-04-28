package test.laba.common.util;

import test.laba.common.IO.Colors;


public class ResponseWithError extends Response {
    private static final long serialVersionUID = 11;

    public ResponseWithError(String message) {
        super(Colors.RED + message + Colors.END);
    }
}
