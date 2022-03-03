package test.laba.client.commands;



import test.laba.client.console.Console;
import test.laba.client.console.ConsoleParsing;
import test.laba.client.exception.VariableException;
import test.laba.client.mainClasses.Product;
import test.laba.client.mainClasses.Root;
import test.laba.client.mainClasses.UnitOfMeasure;

import java.time.ZonedDateTime;

public class UpdateID extends AbstractCommand {
    private final Console console;
    public UpdateID(Console console) {
        super("Update_ID", "обновить значение элемента коллекции, id которого равен заданному");
        this.console = console;
    }

    public void execute(Root root, Long id, ConsoleParsing consoleParsing) {
        long key;
        // запрашиваем необходимо ли изменение и изменяем
        if (root.containsID(id)) {
            key = root.getKeyOnID(id);
            Product product = root.getProductByKey(key);
            changeNameProduct(product, consoleParsing);
            changeCoordinates(product, consoleParsing);
            changePrice(product, consoleParsing);
            changeManufactureCost(product, consoleParsing);
            changeUnitOfMeasure(product, consoleParsing);
            changePerson(product, consoleParsing);
        }


    }
    private void changeNameProduct(Product product, ConsoleParsing consoleParsing) {
        if (console.askQuestion("Хотите изменить название продукта?")) {
            String answer = parsFieldFromConsole(consoleParsing::toRightName, product::getName, "Введите измененное имя:");
            product.setName(answer);
        }
    }
    private void changeCoordinates(Product product, ConsoleParsing consoleParsing) {
        if (console.askQuestion("Хотите изменить координаты?")) {
            if (console.askQuestion("Хотите изменить координату X?")) {
                Integer x = parsFieldFromConsole(consoleParsing::toRightX, product.getCoordinates()::getX, "Введите координату х: ");
                product.getCoordinates().setX(x);
            }
            if (console.askQuestion("Хотите изменить координату Y?")) {
                Float y = parsFieldFromConsole(consoleParsing::toRightY, product.getCoordinates()::getY, "Введите координату y: ");
                product.getCoordinates().setY(y);
            }
        }
    }
    private void changePrice(Product product, ConsoleParsing consoleParsing) {
        if (console.askQuestion("Хотите изменить price?")) {
            Long price = parsFieldFromConsole(consoleParsing::toRightPrice, product::getPrice, "Введите price: ");
            product.setPrice(price);

        }
    }
    private void changeManufactureCost(Product product, ConsoleParsing consoleParsing) {
        if (console.askQuestion("Хотите изменить manufactureCost?")) {
            Integer manufactureCost = parsFieldFromConsole(consoleParsing::toRightNumberInt, product::getManufactureCost, "Введите manufactureCost: ");
            product.setManufactureCost(manufactureCost);

        }
    }
    private void changeUnitOfMeasure(Product product, ConsoleParsing consoleParsing) {
        if (console.askQuestion("Хотите изменить unitOfMeasure?")) {
            UnitOfMeasure unitOfMeasure = parsFieldFromConsole(consoleParsing::toRightUnitOfMeasure, product::getUnitOfMeasure, "Введите : unitOfMeasure");
            product.setUnitOfMeasure(unitOfMeasure);

        }
    }
    private void changePerson(Product product, ConsoleParsing consoleParsing) {
        if (console.askQuestion("Хотите изменить owner?")) {
            console.ask("Owner: " + product.getOwner());
            changeNamePerson(product, consoleParsing);
            changeBirthday(product, consoleParsing);
            changeHeight(product, consoleParsing);
            changeLocation(product, consoleParsing);
        }
    }

    private void changeNamePerson(Product product, ConsoleParsing consoleParsing) {
        if (console.askQuestion("Хотите изменить имя владельца?")) {
            String answer = parsFieldFromConsole(consoleParsing::toRightName, product.getOwner()::getName, "Введите имя владельца: ");
            product.getOwner().setName(answer);
        }
    }
    private void changeBirthday(Product product, ConsoleParsing consoleParsing) {
        if (console.askQuestion("Хотите изменить дату рождения владельца?")) {
            ZonedDateTime birthday = parsFieldFromConsole(consoleParsing::toRightBirthday, product.getOwner()::getBirthday, "Введите дату рождения владельца: ");
            product.getOwner().setBirthday(birthday);
        }
    }
    private void changeHeight(Product product, ConsoleParsing consoleParsing) {
        if (console.askQuestion("Хотите изменить рост владельца?")) {
            Integer height = parsFieldFromConsole(consoleParsing::toRightHeight, product.getOwner()::getHeight, "Введите рост владельца: ");
            product.getOwner().setHeight(height);
        }
    }
    private void changeLocation(Product product, ConsoleParsing consoleParsing) {

        if (console.askQuestion("Хотите изменить локацию владельца?")) {
            console.ask("Location: " + product.getOwner().getLocation());
            changeLocationX(product, consoleParsing);
            changeLocationY(product, consoleParsing);
            changeLocationName(product, consoleParsing);
        }
    }

    private void changeLocationX(Product product, ConsoleParsing consoleParsing) {
        if (console.askQuestion("Хотите изменить координату х локации?")) {
            Long xLocation = parsFieldFromConsole(consoleParsing::toRightNumberLong, product.getOwner().getLocation()::getX, "Введите координату х: ");
            product.getOwner().getLocation().setX(xLocation);
        }
    }
    private void changeLocationY(Product product, ConsoleParsing consoleParsing) {
        if (console.askQuestion("Хотите изменить координату у локации?")) {
            Integer y = parsFieldFromConsole(consoleParsing::toRightNumberInt, product.getOwner().getLocation()::getY, "Введите координату y: ");
            product.getOwner().getLocation().setY(y);
        }
    }
    private void changeLocationName(Product product, ConsoleParsing consoleParsing) {
        if (console.askQuestion("Хотите изменить название локации?")) {
            String answer = parsFieldFromConsole(consoleParsing::toRightName, product.getOwner().getLocation()::getName, "Введите название локации: ");
            product.getOwner().getLocation().setName(answer);
        }
    }
    private <T> T  parsFieldFromConsole(IFunction pars, GetFunction getField, String request) {
        String answer;
        T value;
        console.ask("Значение поля сейчас: " + getField.getFunction());
        while (true) {
            answer = console.askFullQuestion(request);
            try {
                value = (T) pars.function(answer);
                break;
            } catch (VariableException | IllegalArgumentException e) {
                console.printError("Неправильный тип данных, повторите ввод");
            }
        }
        return value;
    }
    private interface GetFunction<T> {
        T getFunction();
    }
    private interface IFunction<T> {
        T function(String oldField);
    }
}


