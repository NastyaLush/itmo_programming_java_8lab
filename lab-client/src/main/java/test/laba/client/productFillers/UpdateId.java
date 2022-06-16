package test.laba.client.productFillers;

import java.util.ResourceBundle;
import test.laba.client.util.Command;
import test.laba.client.util.Console;
import test.laba.client.util.VariableParsing;
import test.laba.common.dataClasses.Product;
import test.laba.common.dataClasses.UnitOfMeasure;
import test.laba.common.exception.VariableException;

import java.time.ZonedDateTime;

public class UpdateId {
    private final ConsoleParsing consoleParsing;
    private final Console console;
    private final ResourceBundle resourceBundle;

    public UpdateId(ConsoleParsing consoleParsing, Console console, ResourceBundle resourceBundle) {
        this.console = console;
        this.consoleParsing = consoleParsing;
        this.resourceBundle = resourceBundle;
    }

    public Product execute(Product product) throws VariableException {
        changeNameProduct(product);
        changeCoordinates(product);
        changePrice(product);
        changeManufactureCost(product);
        changeUnitOfMeasure(product);
        changePerson(product);
        return product;
    }

    private void changeNameProduct(Product product) throws VariableException {
        if (console.askQuestion()) {
            String answer = parsFieldFromConsole(VariableParsing::toRightName, Command.PRODUCT_NAME);
            product.setName(answer);
        }
    }

    private void changeCoordinates(Product product) throws VariableException {
        if (console.askQuestion()) {
            if (console.askQuestion()) {
                Integer x = parsFieldFromConsole(VariableParsing::toRightX, Command.COORDINATE_X);
                try {
                    product.getCoordinates().setX(x);
                } catch (VariableException e) {
                    console.printError(e.getMessage());
                }
            }
            if (console.askQuestion()) {
                Float y = parsFieldFromConsole(VariableParsing::toRightY, Command.COORDINATE_Y);
                try {
                    product.getCoordinates().setY(y);
                } catch (VariableException e) {
                    console.printError(e.getMessage());
                }
            }
        }
    }

    private void changePrice(Product product) throws VariableException {
        if (console.askQuestion()) {
            Long price = parsFieldFromConsole(VariableParsing::toRightPrice, Command.PRICE);
            product.setPrice(price);

        }
    }

    private void changeManufactureCost(Product product) throws VariableException {
        if (console.askQuestion()) {
            Integer manufactureCost = parsFieldFromConsole(VariableParsing::toRightNumberInt, Command.MANUFACTURE_COST);
            product.setManufactureCost(manufactureCost);

        }
    }

    private void changeUnitOfMeasure(Product product) throws VariableException {
        if (console.askQuestion()) {
            UnitOfMeasure unitOfMeasure = parsFieldFromConsole(VariableParsing::toRightUnitOfMeasure, Command.UNIT_OF_MEASURE);
            product.setUnitOfMeasure(unitOfMeasure);

        }
    }

    private void changePerson(Product product) throws VariableException {
        if (console.askQuestion()) {
            if (!console.askQuestion()) {
                if (product.getOwner() != null) {
                    changeNamePerson(product);
                    changeBirthday(product);
                    changeHeight(product);
                    changeLocation(product);
                } else {
                    product.setOwner(consoleParsing.parsPersonFromConsole(resourceBundle));
                }
            } else {
                product.setOwner(null);
            }
        }
    }

    private void changeNamePerson(Product product) throws VariableException {
        if (console.askQuestion()) {
            String answer = parsFieldFromConsole(VariableParsing::toRightName, Command.PERSON_NAME);
            product.getOwner().setName(answer);
        }
    }

    private void changeBirthday(Product product) throws VariableException {
        if (console.askQuestion()) {
            ZonedDateTime birthday = parsFieldFromConsole(VariableParsing::toRightBirthday, Command.BIRTHDAY);
            product.getOwner().setBirthday(birthday);
        }
    }

    private void changeHeight(Product product) throws VariableException {
        if (console.askQuestion()) {
            Integer height = parsFieldFromConsole(VariableParsing::toRightHeight, Command.HEIGHT);
            product.getOwner().setHeight(height);
        }
    }

    private void changeLocation(Product product) throws VariableException {

        if (console.askQuestion()) {
            changeLocationX(product);
            changeLocationY(product);
            changeLocationName(product);
        }
    }

    private void changeLocationX(Product product) throws VariableException {
        if (console.askQuestion()) {
            Long xLocation = parsFieldFromConsole(VariableParsing::toRightNumberLong, Command.LOCATION_X);
            product.getOwner().getLocation().setX(xLocation);
        }
    }

    private void changeLocationY(Product product) throws VariableException {
        if (console.askQuestion()) {
            Integer y = parsFieldFromConsole(VariableParsing::toRightNumberInt, Command.LOCATION_Y);
            product.getOwner().getLocation().setY(y);
        }
    }

    private void changeLocationName(Product product) throws VariableException {
        if (console.askQuestion()) {
            String answer = parsFieldFromConsole(VariableParsing::toRightName, Command.LOCATION_NAME);
            product.getOwner().getLocation().setName(answer);
        }
    }

    private <T> T parsFieldFromConsole(IFunction pars, Command command) throws VariableException {
        String answer;
        T value;
        answer = console.askFullQuestion();
        value = (T) pars.function(text(command), answer, resourceBundle);
        return value;
    }

    private String text(Command field) {
        return field.getString();
    }

    private interface IFunction<T> {
        T function(String command, String fieldName, ResourceBundle resourceBundle) throws VariableException;
    }
}
