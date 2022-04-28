package test.laba.common.util;

import java.util.Map;

public class ResponseWithCollection extends Response {
    private static final long serialVersionUID = 11;
    private final Map collection;

    public ResponseWithCollection(Map collection) {
        super("collection");
        this.collection = collection;
    }

    public Map getCollection() {
        return collection;
    }
}
