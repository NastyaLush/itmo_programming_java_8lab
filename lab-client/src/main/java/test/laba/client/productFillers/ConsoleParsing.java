package test.laba.client.productFillers;

import test.laba.client.util.Console;
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
public class ConsoleParsing {
    private final Console console;

    public ConsoleParsing(Console console) {
        this.console = console;
    }

    /**
     * create java object accept data from console
     *
     * @return product object
     */
    public Product parsProductFromConsole() throws CreateError {
        Person owner = null;
        String name = parsField("Введите название продукта: ", VariableParsing::toRightName, Command.PRODUCT_NAME);
        Coordinates coordinates = parsCoordinatesFromConsole();
        Long price = parsField("Введите цену продукта, price: ", VariableParsing::toRightPrice, Command.PRICE);
        Integer manufactureCost = parsField("Введите поле manufactureCost: ", VariableParsing::toRightNumberInt, Command.MANUFACTURE_COST);
        UnitOfMeasure unitOfMeasure = parsField("Введите unitOfMeasure, возможные варианты:PCS, MILLILITERS, GRAMS", VariableParsing::toRightUnitOfMeasure, Command.UNIT_OF_MEASURE);
        if (console.askQuestion("Хотите добавить владельца?(yes/no/да/нет)")) {
            owner = parsPersonFromConsole();
        }
        Product product;
        product = new Product(name, coordinates, price, manufactureCost, unitOfMeasure, owner);
        return product;

    }

    private Location parsLocationFromConsole() {
        Long x = parsField("Введите координату X локации: ", VariableParsing::toRightNumberLong, Command.LOCATION_X);
        Integer y = parsField("Введите координату Y локации: ", VariableParsing::toRightNumberInt, Command.LOCATION_Y);
        String name = parsField("Введите название локации: ", VariableParsing::toRightName, Command.LOCATION_NAME);

        try {
            return new Location(x, y, name);
        } catch (VariableException e) {
            console.printError(e.getMessage());
            return null;
        }
    }

    public Person parsPersonFromConsole() {
        String name = parsField("Введите имя владельца: ", VariableParsing::toRightName, Command.PERSON_NAME);
        ZonedDateTime newBirthday = parsField("Введите дату рождения владельца: ", VariableParsing::toRightBirthday, Command.BIRTHDAY);
        Integer height = parsField("Введите рост владельца: ", VariableParsing::toRightHeight, Command.HEIGHT);
        Location location = parsLocationFromConsole();
        Person person;
        try {
            person = new Person(name, newBirthday, height, location);
            return person;
        } catch (CreateError e) {
            return parsPersonFromConsole();
        }
    }

    private Coordinates parsCoordinatesFromConsole() {
        Integer x = parsField("Введите координату х: ", VariableParsing::toRightX, Command.COORDINATE_Y);
        Float y = parsField("Введите координату y: ", VariableParsing::toRightY, Command.COORDINATE_Y);
        return new Coordinates(x, y);

    }

    public <T> T parsField(String question, IFunction pars, Command field) {
        T value;
        String simpleField;
        try {
            console.ask(question);
            simpleField = console.scanner();
            if (simpleField != null) {
                value = (T) pars.function(text(field), simpleField);
            } else {
                throw new VariableException("не может быть null");
            }
            if (value == null) {
                throw new VariableException("не может быть null");
            }
        } catch (VariableException e) {
            console.printError("Не правильный тип данных," + e.getMessage());
            value = parsField(question, pars, field);
        }
        return value;
    }

    private String text(Command field) {
        return field.getString();
    }

    public interface IFunction<T> {
        T function(String oldField, String field) throws VariableException;
    }
}



