package test.laba.client.util;

public enum Command {
    UPDATE_ID("update_id"),
    REMOVE_KEY("remove_key"),
    REMOVE_LOWER_KEY("remove_lower_key"),
    REMOVE_LOWER("remove_lower"),
    REMOVE_ANY_BY_UNIT_OF_MEASURE("remove_any_by_unit_of_measure"),
    EXECUTE_SCRIPT("execute_script"),
    GROUP_COUNTING_BY_PRICE("group_counting_by_price"),
    AVERAGE_OF_MANUFACTURE_COST("average_of_manufacture_cost"),
    KEY("key"),
    ID("id"),
    PRODUCT_NAME("product Name"),
    COORDINATE_X("coordinate X"),
    COORDINATE_Y("coordinate Y"),
    CREATION_DATE("creation Date"),
    PRICE("price"),
    MANUFACTURE_COST("manufacture Cost"),
    UNIT_OF_MEASURE("unit Of Measure"),
    PERSON_NAME("person Name"),
    BIRTHDAY("birthday"),
    HEIGHT("height"),
    LOCATION_X("location X"),
    LOCATION_Y("location Y"),
    FILTER("filter"),
    LOCATION_NAME("location Name");

    private final String string;

    Command(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
