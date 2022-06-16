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

    public ConsoleParsing(Console console) {
        this.console = console;
    }

    /**
     * create java object accept data from console
     *
     * @return product object
     */
    public Product parsProductFromConsole(ResourceBundle resourceBundle) throws CreateError, VariableException {
        Person owner = null;
        String name = parsField(VariableParsing::toRightName, Command.PRODUCT_NAME, resourceBundle);
        Coordinates coordinates = parsCoordinatesFromConsole(resourceBundle);
        Long price = parsField(VariableParsing::toRightPrice, Command.PRICE, resourceBundle);
        Integer manufactureCost = parsField(VariableParsing::toRightNumberInt, Command.MANUFACTURE_COST, resourceBundle);
        UnitOfMeasure unitOfMeasure = parsField(VariableParsing::toRightUnitOfMeasure, Command.UNIT_OF_MEASURE, resourceBundle);
        if (console.askQuestion()) {
            owner = parsPersonFromConsole(resourceBundle);
        }
        Product product;
        product = new Product(name, coordinates, price, manufactureCost, unitOfMeasure, owner);
        return product;

    }

    private Location parsLocationFromConsole(ResourceBundle resourceBundle) throws VariableException {
        Long x = parsField(VariableParsing::toRightNumberLong, Command.LOCATION_X, resourceBundle);
        Integer y = parsField(VariableParsing::toRightNumberInt, Command.LOCATION_Y, resourceBundle);
        String name = parsField(VariableParsing::toRightName, Command.LOCATION_NAME, resourceBundle);

        try {
            return new Location(x, y, name);
        } catch (VariableException e) {
            console.printError(e.getMessage());
            return null;
        }
    }

    public Person parsPersonFromConsole(ResourceBundle resourceBundle) throws VariableException {
        String name = parsField(VariableParsing::toRightName, Command.PERSON_NAME, resourceBundle);
        ZonedDateTime newBirthday = parsField(VariableParsing::toRightBirthday, Command.BIRTHDAY, resourceBundle);
        Integer height = parsField(VariableParsing::toRightHeight, Command.HEIGHT, resourceBundle);
        Location location = parsLocationFromConsole(resourceBundle);
        Person person;
        try {
            person = new Person(name, newBirthday, height, location);
            return person;
        } catch (CreateError e) {
            return parsPersonFromConsole(resourceBundle);
        }
    }

    private Coordinates parsCoordinatesFromConsole(ResourceBundle resourceBundle) throws VariableException {
        Integer x = parsField(VariableParsing::toRightX, Command.COORDINATE_Y, resourceBundle);
        Float y = parsField(VariableParsing::toRightY, Command.COORDINATE_Y, resourceBundle);
        return new Coordinates(x, y);

    }

    public <T> T parsField(IFunction pars, Command field, ResourceBundle resourceBundle) throws VariableException {
        T value;
        String simpleField;
        simpleField = console.scanner();
        if (simpleField != null) {
            value = (T) pars.function(text(field), simpleField, resourceBundle);
        } else {
            throw new VariableException(localisation(resourceBundle, Constants.CAN_NOT_BE_NULL));
        }
        if (value == null) {
            throw new VariableException(localisation(resourceBundle, Constants.CAN_NOT_BE_NULL));
        }
        return value;
    }

    private String text(Command field) {
        return field.getString();
    }

    public interface IFunction<T> {
        T function(String oldField, String field, ResourceBundle resourceBundle) throws VariableException;
    }
}



