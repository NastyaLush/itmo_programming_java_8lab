package test.laba.client.util;

public enum Constants {
    TITLE("title"),
    AUTHORISATION("authorisation"),
    LOGIN("login"),
    PASSWORD("password"),
    HOST("host"),
    PORT("port"),
    NOT_LOGIN_YET("notLoginYet"),
    SING_UP("singUp"),
    REGISTRATION("registration"),
    FILTER("filter"),
    GROUP_COUNTING_BY_PRICE("groupCountingByPrice"),
    AVERAGE_OF_MANUFACTURE_COST("averageOfManufactureCost"),
    KEY("key"),
    ID("id"),
    PRODUCT_NAME("productName"),
    COORDINATE_X("coordinateX"),
    COORDINATE_Y("coordinateY"),
    CREATION_DATE("creationDate"),
    PRICE("price"),
    MANUFACTURE_COST("manufactureCost"),
    UNIT_OF_MEASURE("unitOfMeasure"),
    PERSON_NAME("personName"),
    BIRTHDAY("birthday"),
    HEIGHT("height"),
    LOCATION_X("locationX"),
    LOCATION_Y("locationY"),
    LOCATION_NAME("locationName"),
    REMOVE_KEY("removeKey"),
    REMOVE_LOWER_KEY("removeLowerKey"),
    REMOVE_LOWER("removeLower"),
    REMOVE_ANY_BY_UNIT_OF_MEASURE("removeAnyByUnitOfMeasure"),
    OK("ok"),
    CHOOSE_DIRECTORY("chooseDirectory"),
    ERROR("error"),
    INFO("info"),
    PCS("pcs"),
    MILLILITERS("milliliters"),
    GRAMS("grams"),
    ADD_OWNER("addOwner"),
    CAN_NOT_BE_NULL("canNOtBeNull"),
    MUST_BE_BIGGER("mustBeBigger"),
    RUSSIAN("russian"),
    NORWEGIAN("norwegian"),
    FRENCH("french"),
    SPANISH("spanish"),
    LANGUAGE("language"),
    FORMAT("format"),
    HELP("help"),
    CANCEL("cancel"),
    CHOOSE("choose"),
    BACK("back"),
    MUST_BE_INTEGER_NUMBER_INT("mustBeIntegerNumberInt"),
    MUST_BE_INTEGER_NUMBER_LONG("mustBeIntegerNumberLong"),
    MUST_BE_FLOAT_NUMBER("mustBeFloatNumber"),
    WRONG_DATE("wrongDate"),
    WRONG_UNIT_OF_MEASURE("wrongUnitMeasure"),
    HOST_EXCEPTION("hostException"),
    INTERRAPTED_EXCEPTION("InterException"),
    IO_EXCEPTION("ioException"),
    THANK_YOU("thankYou"),
    SERVER_CLOSED("serverClosed"),
    CYCLE_IN_THE_SCRIPT("cycleInTheScript");

    private final String string;

    Constants(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
