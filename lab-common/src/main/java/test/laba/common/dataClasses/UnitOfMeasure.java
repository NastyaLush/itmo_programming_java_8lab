package test.laba.common.dataClasses;

import java.io.Serializable;

/**
 * data class
 * the enum class wish contains possible unit of measure of products
 */
public enum UnitOfMeasure implements Serializable {
    PCS("PCS"),
    MILLILITERS("MILLILITERS"),
    GRAMS("GRAMS");

    private final String message;

    UnitOfMeasure(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
