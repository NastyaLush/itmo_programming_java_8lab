package test.laba.client.productFillers;

import java.util.ResourceBundle;
import test.laba.client.frontEnd.frames.local.Localized;
import test.laba.client.util.Console;
import test.laba.client.util.Constants;
import test.laba.client.util.VariableParsing;
import test.laba.common.exception.CreateError;
import test.laba.common.exception.VariableException;
import test.laba.common.dataClasses.Location;
import test.laba.common.dataClasses.UnitOfMeasure;
import test.laba.common.dataClasses.Coordinates;
import test.laba.common.dataClasses.Person;
import test.laba.common.dataClasses.Product;
import test.laba.client.util.Command;

import java.time.ZonedDateTime;

/**
 * the class is responsible for work with creating products from console
 */
public class ConsoleParsing implements Localized {
    private final Console console;
    private final ResourceBundle resourceBundle;

    public ConsoleParsing(Console console, ResourceBundle resourceBundle) {
        this.console = console;
        this.resourceBundle = resourceBundle;
    }

    /**
     * create java object accept data from console
     *
     * @return product object
     */
    public Product parsProductFromConsole() throws CreateError, VariableException {
        Person owner = null;
        String name = parsField(VariableParsing::toRightName, Command.PRODUCT_NAME);
        Coordinates coordinates = parsCoordinatesFromConsole();
        Long price = parsField(VariableParsing::toRightPrice, Command.PRICE);
        Integer manufactureCost = parsField(VariableParsing::toRightNumberInt, Command.MANUFACTURE_COST);
        UnitOfMeasure unitOfMeasure = parsField(VariableParsing::toRightUnitOfMeasure, Command.UNIT_OF_MEASURE);
        if (console.askQuestion()) {
            owner = parsPersonFromConsole();
        }
        Product product;
        product = new Product(name, coordinates, price, manufactureCost, unitOfMeasure, owner);
        return product;

    }

    private Location parsLocationFromConsole() throws VariableException {
        Long x = parsField(VariableParsing::toRightNumberLong, Command.LOCATION_X);
        Integer y = parsField(VariableParsing::toRightNumberInt, Command.LOCATION_Y);
        String name = parsField(VariableParsing::toRightName, Command.LOCATION_NAME);

        try {
            return new Location(x, y, name);
        } catch (VariableException e) {
            console.printError(e.getMessage());
            return null;
        }
    }

    public Person parsPersonFromConsole() throws VariableException {
        String name = parsField(VariableParsing::toRightName, Command.PERSON_NAME);
        ZonedDateTime newBirthday = parsField(VariableParsing::toRightBirthday, Command.BIRTHDAY);
        Integer height = parsField(VariableParsing::toRightHeight, Command.HEIGHT);
        Location location = parsLocationFromConsole();
        Person person;
        try {
            person = new Person(name, newBirthday, height, location);
            return person;
        } catch (CreateError e) {
            return parsPersonFromConsole();
        }
    }

    private Coordinates parsCoordinatesFromConsole() throws VariableException {
        Integer x = parsField(VariableParsing::toRightX, Command.COORDINATE_Y);
        Float y = parsField(VariableParsing::toRightY, Command.COORDINATE_Y);
        return new Coordinates(x, y);

    }

    public <T> T parsField(IFunction pars, Command field) throws VariableException {
        T value;
        String simpleField;
        simpleField = console.scanner();
        if (simpleField != null) {
            value = (T) pars.function(text(field), simpleField, resourceBundle);
        } else {
            throw new VariableException(localisation(Constants.CAN_NOT_BE_NULL));
        }
        if (value == null) {
            throw new VariableException(localisation(Constants.CAN_NOT_BE_NULL));
        }
        return value;
    }

    private String text(Command field) {
        return field.getString();
    }

    @Override
    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public interface IFunction<T> {
        T function(String oldField, String field, ResourceBundle resourceBundle) throws VariableException;
    }
}



