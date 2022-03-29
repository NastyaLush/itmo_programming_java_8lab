package test.laba.client.console;



import test.laba.client.exception.CreateError;
import test.laba.client.exception.VariableException;
import test.laba.client.dataClasses.Location;
import test.laba.client.dataClasses.UnitOfMeasure;
import test.laba.client.dataClasses.Coordinates;
import test.laba.client.dataClasses.Person;
import test.laba.client.dataClasses.Product;
import test.laba.client.dataClasses.Root;
import java.time.ZonedDateTime;

/**
 * the class is responsible for work with creating products from console
 */
public  class ConsoleParsing extends VariableParsing implements Variable {

    public ConsoleParsing(Console console) {
        super(console);
    }

    /**
     * create java object accept data from console
     * @param root root contained collection
     * @return product object
     */
    public Product parsProductFromConsole(Root root) {
        Person owner = null;
        String name = parsField("Введите название продукта: ", console, this::toRightName);
        Coordinates coordinates = parsCoordinatesFromConsole();
        Long price = parsField("Введите цену продукта, price: ", console, this::toRightPrice);
        Integer manufactureCost = parsField("Введите поле manufactureCost: ", console, this::toRightNumberInt);
        UnitOfMeasure unitOfMeasure = parsField("Введите unitOfMeasure, возможные варианты:PCS, MILLILITERS, GRAMS", console, this::toRightUnitOfMeasure);
        if (console.askQuestion("Хотите добавить владельца?(yes/no/да/нет)")) {
            owner = parsPersonFromConsole();
        }
        Product product = null;

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
    public   Person parsPersonFromConsole() {
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
    String simpleField = null;
            try {
                console.ask(question);
                simpleField = console.scanner();
                if (simpleField != null) {
                    value = (T) pars.function(simpleField);
                } else {
                    throw new VariableException("не может быть null", console);
                }
                if (value == null) {
                    throw new VariableException("не может быть null", console);
                }
            } catch (VariableException | IllegalArgumentException e) {
                console.printError("Не правильный тип данных");
                value = parsField(question, console, pars);
            }
        return value;
    }
    protected String getEnvVariable() {
        return Variable.ENV_VARIBLE;
    }
    private interface IFunction<T> {
        T function(String oldField);
    }
}



