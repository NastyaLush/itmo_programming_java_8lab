package test.laba.common.util;

import test.laba.common.IO.Colors;

import java.util.Collection;
import java.util.Map;

public class ResponseWithCollection extends Response {
    private static final long serialVersionUID = 11;
    private Map collection;

    public ResponseWithCollection(Map collection){
        super("collection");
        this.collection = collection;
    }

    public Map getCollection() {
        return collection;
    }
}