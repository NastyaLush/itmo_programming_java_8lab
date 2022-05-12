package test.laba.client.productFillers;

import test.laba.client.util.Console;
import test.laba.client.util.VariableParsing;
import test.laba.server.mycommands.commands.Variable;
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
public class ConsoleParsing implements Variable {
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
        String name = parsField("Введите название продукта: ", VariableParsing::toRightName);
        Coordinates coordinates = parsCoordinatesFromConsole();
        Long price = parsField("Введите цену продукта, price: ", VariableParsing::toRightPrice);
        Integer manufactureCost = parsField("Введите поле manufactureCost: ", VariableParsing::toRightNumberInt);
        UnitOfMeasure unitOfMeasure = parsField("Введите unitOfMeasure, возможные варианты:PCS, MILLILITERS, GRAMS", VariableParsing::toRightUnitOfMeasure);
        if (console.askQuestion("Хотите добавить владельца?(yes/no/да/нет)")) {
            owner = parsPersonFromConsole();
        }
        Product product;
        product = new Product(name, coordinates, price, manufactureCost, unitOfMeasure, owner);
        return product;

    }

    private Location parsLocationFromConsole() {
        Long x = parsField("Введите координату X локации: ", VariableParsing::toRightNumberLong);
        Integer y = parsField("Введите координату Y локации: ", VariableParsing::toRightNumberInt);
        String name = parsField("Введите название локации: ", VariableParsing::toRightName);

        try {
            return new Location(x, y, name);
        } catch (VariableException e) {
            console.printError(e.getMessage());
            return null;
        }
    }

    public Person parsPersonFromConsole() {
        String name = parsField("Введите имя владельца: ", VariableParsing::toRightName);
        ZonedDateTime newBirthday = parsField("Введите дату рождения владельца: ", VariableParsing::toRightBirthday);
        Integer height = parsField("Введите рост владельца: ", VariableParsing::toRightHeight);
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
        Integer x = parsField("Введите координату х: ", VariableParsing::toRightX);
        Float y = parsField("Введите координату y: ", VariableParsing::toRightY);
        return new Coordinates(x, y);

    }

    public <T> T parsField(String question, IFunction pars) {
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

    public interface IFunction<T> {
        T function(String oldField) throws VariableException;
    }
}



