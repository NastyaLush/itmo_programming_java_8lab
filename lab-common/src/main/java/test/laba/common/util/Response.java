package test.laba.common.util;

import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Response implements Serializable {
    private static final long serialVersionUID = 11;
    private String message;

    public Response(String message){
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
