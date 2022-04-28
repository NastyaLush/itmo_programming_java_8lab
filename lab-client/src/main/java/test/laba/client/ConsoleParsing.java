package test.laba.client;

import test.laba.common.IO.Console;
import test.laba.common.commands.Variable;
import test.laba.common.exception.CreateError;
import test.laba.common.exception.VariableException;
import test.laba.common.dataClasses.Location;
import test.laba.common.dataClasses.UnitOfMeasure;
import test.laba.common.dataClasses.Coordinates;
import test.laba.common.dataClasses.Person;
import test.laba.common.dataClasses.Product;

import java.time.ZonedDateTime;

/**
 * the class is responsible for work with creating products from console
 */
public class ConsoleParsing extends VariableParsing implements Variable {
    private Console console;

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
        String name = parsField("Введите название продукта: ", this::toRightName);
        Coordinates coordinates = parsCoordinatesFromConsole();
        Long price = parsField("Введите цену продукта, price: ", this::toRightPrice);
        Integer manufactureCost = parsField("Введите поле manufactureCost: ", this::toRightNumberInt);
        UnitOfMeasure unitOfMeasure = parsField("Введите unitOfMeasure, возможные варианты:PCS, MILLILITERS, GRAMS", this::toRightUnitOfMeasure);
        if (console.askQuestion("Хотите добавить владельца?(yes/no/да/нет)")) {
            owner = parsPersonFromConsole();
        }
        Product product;
        product = new Product(name, coordinates, price, manufactureCost, unitOfMeasure, owner);
        return product;

    }

    private Location parsLocationFromConsole() {
        Long x = parsField("Введите координату X локации: ", this::toRightNumberLong);
        Integer y = parsField("Введите координату Y локации: ", this::toRightNumberInt);
        String name = parsField("Введите название локации: ", this::toRightName);

        try {
            return new Location(x, y, name);
        } catch (VariableException e) {
            console.printError(e.getMessage());
            return null;
        }
    }

    public Person parsPersonFromConsole() {
        String name = parsField("Введите имя владельца: ", this::toRightName);
        ZonedDateTime newBirthday = parsField("Введите дату рождения владельца: ", this::toRightBirthday);
        Integer height = parsField("Введите рост владельца: ", this::toRightHeight);
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
        Integer x = parsField("Введите координату х: ", this::toRightX);
        Float y = parsField("Введите координату y: ", this::toRightY);
        return new Coordinates(x, y);

    }

    private <T> T parsField(String question, IFunction pars) {
        T value;
        String simpleField;
        try {
            console.ask(question);
            simpleField = console.scanner();
            if (simpleField != null) {
                value = (T) pars.function(simpleField);
            } else {
                throw new VariableException("не может быть null");
            }
            if (value == null) {
                throw new VariableException("не может быть null");
            }
        } catch (VariableException e) {
            console.printError("Не правильный тип данных," + e.getMessage());
            value = parsField(question, pars);
        }
        return value;
    }

    private interface IFunction<T> {
        T function(String oldField) throws VariableException;
    }
}



