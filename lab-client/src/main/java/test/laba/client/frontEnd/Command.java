package test.laba.client.frontEnd;

public enum Command {
    UPDATE_ID("update_id"),
    REMOVE_KEY("remove_key"),
    REMOVE_LOWER_KEY("remove_lower_key"),
    REMOVE_LOWER("remove_lower"),
    REMOVE_ANY_BY_UNIT_OF_MEASURE("remove_any_by_unit_of_measure"),
    EXECUTE_SCRIPT("execute_script");

    private final String string;

    Command(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
