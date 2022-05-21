package test.laba.common.responses;

import test.laba.common.util.Values;

import java.util.Map;

public class ResponseWithCollection extends Response {
    private static final long serialVersionUID = 10;

    public ResponseWithCollection(Map<String, Values> collection) {
        super(collection, "collection");
    }

}
