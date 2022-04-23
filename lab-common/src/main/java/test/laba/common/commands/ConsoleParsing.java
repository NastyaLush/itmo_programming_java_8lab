package test.laba.common.commands;



import test.laba.common.IO.Console;
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
public  class ConsoleParsing extends VariableParsing implements Variable {

    /**
     * create java object accept data from console
     * @param root root contained collection
     * @return product object
     */
    public Product parsProductFromConsole(Root root) {
        Person owner = null;
        String name = parsField("Введите название продукта: ", this::toRightName);
        Coordinates coordinates = parsCoordinatesFromConsole();
        Long price = parsField("Введите цену продукта, price: ", this::toRightPrice);
        Integer manufactureCost = parsField("Введите поле manufactureCost: ", this::toRightNumberInt);
        UnitOfMeasure unitOfMeasure = parsField("Введите unitOfMeasure, возможные варианты:PCS, MILLILITERS, GRAMS", this::toRightUnitOfMeasure);
       /* if (console.askQuestion("Хотите добавить владельца?(yes/no/да/нет)")) {
            // TODO: 16.04.2022 create new class for this
            owner = parsPersonFromConsole();
        }*/
        Product product = null;

        try {
            product = new Product(name, coordinates, price, manufactureCost, unitOfMeasure, owner, root);
        } catch (CreateError e) {
           // console.printError(e);
            parsProductFromConsole(root);
        }

        return product;

    }
    private Location parsLocationFromConsole() {
        Long x = parsField("Введите координату X локации: ", this::toRightNumberLong);
        Integer y = parsField("Введите координату Y локации: ", this::toRightNumberInt);
        String name = parsField("Введите название локации: ", this::toRightName);

        return new Location(x, y, name);
    }
    public   Person parsPersonFromConsole() {
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
    private  Coordinates parsCoordinatesFromConsole() {
        Integer x = parsField("Введите координату х: ", this::toRightX);
        Float y = parsField("Введите координату y: ", this::toRightY);

        return new Coordinates(x, y);

    }
    private <T> T parsField(String question, IFunction pars) {
    T value;
    String simpleField = null;
            try {
                // TODO: 16.04.2022 send request
                //console.ask(question);
                // TODO: 16.04.2022 give request
                //simpleField = console.scanner();
                if (simpleField != null) {
                    value = (T) pars.function(simpleField);
                } else {
                    throw new VariableException("не может быть null");
                }
                if (value == null) {
                    throw new VariableException("не может быть null");
                }
            } catch (VariableException | IllegalArgumentException e) {
                // TODO: respone with exception 
               // console.printError("Не правильный тип данных");
                value = parsField(question, pars);
            }
        return value;
    }
    private interface IFunction<T> {
        T function(String oldField) throws VariableException;
    }
}



