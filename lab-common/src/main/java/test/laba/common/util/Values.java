package test.laba.common.util;

public enum Values {
    PRODUCT("product"),
    UNIT_OF_MEASURE("unit of measure"),
    KEY("key"),
    PRODUCT_WITH_QUESTIONS("update_id"),
    PRODUCT_WITHOUT_KEY("Product without key"),
    COLLECTION("get collection"),
    SCRIPT("script");

    private final String message;

    Values(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
