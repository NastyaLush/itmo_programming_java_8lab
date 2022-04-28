package test.laba.common.responses;

import java.util.Map;

public class ResponseWithCollection extends Response {
    private static final long serialVersionUID = 11;

    public ResponseWithCollection(Map collection) {
        super(collection, "collection");
    }

}
