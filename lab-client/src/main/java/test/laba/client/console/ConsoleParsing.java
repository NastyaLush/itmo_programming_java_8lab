package test.laba.client.console;



import test.laba.client.exception.CreateError;
import test.laba.client.exception.VariableException;
import test.laba.client.mainClasses.Location;
import test.laba.client.mainClasses.UnitOfMeasure;
import test.laba.client.mainClasses.Coordinates;
import test.laba.client.mainClasses.Person;
import test.laba.client.mainClasses.Product;
import test.laba.client.mainClasses.Root;

import java.time.ZonedDateTime;


public  class ConsoleParsing extends VariableParsing implements Variable {

    public ConsoleParsing(Console console) {
        super(console);
    }
    public Product parsProductFromConsole(Root root) {
        String name = parsField("Введите название продукта: ", console, this::toRightName);
        Long price = parsField("Введите цену продукта, price: ", console, this::toRightPrice);
        Coordinates coordinates = parsCoordinatesFromConsole();
        Integer manufactureCost = parsField("Введите поле manufactureCost: ", console, this::toRightNumberInt);
        Person owner = parsPersonFromConsole();
        Product product = null;
        UnitOfMeasure unitOfMeasure = parsField("Введите unitOfMeasure, возможные варианты:PCS, MILLILITERS, GRAMS", console, this::toRightUnitOfMeasure);

        try {
            product = new Product(name, coordinates, price, manufactureCost, unitOfMeasure, owner, root);
        } catch (CreateError e) {
            console.printError(e);
            parsProductFromConsole(root);
        }

        return product;

    }
    private Location parsLocationFromConsole() {
        Long x = parsField("Введите координату X локации: ", console, this::toRightNumberLong);
        Integer y = parsField("Введите координату Y локации: ", console, this::toRightNumberInt);
        String name = parsField("Введите название локации: ", console, this::toRightName);

        return new Location(x, y, name, console);
    }
    private  Person parsPersonFromConsole() {
        String name = parsField("Введите имя владельца: ", console, this::toRightName);
        ZonedDateTime newBirthday = parsField("Введите дату рождения владельца: ", console, this::toRightBirthday);
        Integer height = parsField("Введите рост владельца: ", console, this::toRightHeight);
        Location location = parsLocationFromConsole();
        Person person;
        try {
            person = new Person(name, newBirthday, height, location, console);
            return person;
        } catch (CreateError e) {
            return parsPersonFromConsole();
        }
    }
    private  Coordinates parsCoordinatesFromConsole() {
        Integer x = parsField("Введите координату х: ", console, this::toRightX);
        Float y = parsField("Введите координату y: ", console, this::toRightY);

        return new Coordinates(x, y, console);

    }
    private <T> T parsField(String question, Console console, IFunction pars) {
    T value;
        while (true) {
            console.print(question);
            String simpleField = console.scanner();
            try {
                value = (T) pars.function(simpleField);
                break;
            } catch (VariableException | NumberFormatException e) {
                console.printError("Не правильный тип данных");
            }
        }
        return value;
    }
    public String getEnvVariable() {
        return Variable.ENV_VARIBLE;
    }
    private interface IFunction {
        Object function(String oldField);
    }
}



