package test.laba.client.commands;



import test.laba.client.console.Console;
import test.laba.client.console.ConsoleParsing;
import test.laba.client.exception.VariableException;
import test.laba.client.mainClasses.Product;
import test.laba.client.mainClasses.Root;
import test.laba.client.mainClasses.UnitOfMeasure;

import java.time.ZonedDateTime;

public class UpdateID extends AbstractCommand {
    public UpdateID() {
        super("Update_ID", "обновить значение элемента коллекции, id которого равен заданному");
    }

    public void execute(Root root, Long id, Console console, ConsoleParsing consoleParsing) {
        long key;
        // запрашиваем необходимо ли изменение и изменяем
        if (root.containsID(id)) {
            key = root.getKeyOnID(id);
            Product product=root.getProductByKey(key);
            changeNameProduct(console, product, consoleParsing);
            changeCoordinates(console, product, consoleParsing);
            changePrice(console, product, consoleParsing);
            changeManufactureCost(console, product, consoleParsing);
            changeUnitOfMeasure(console, product, consoleParsing);
            changePerson(console, product, consoleParsing);
        }


    }

    private void changeNameProduct(Console console, Product product, ConsoleParsing consoleParsing) {
        boolean flag = true;
        String answer = null;
        if (console.askQuestion("Хотите изменить название продукта?")) {
            console.ask("Имя продукта: " + product.getName());
            while (flag) {
                answer = console.askFullQuestion("Введите измененное имя:");
                try {
                    consoleParsing.toRightName(answer, console);
                    flag = false;
                } catch (VariableException e) {
                    console.printError("Повторите ввод");
                }
            }
            product.setName(answer);
        }
    }
    private void changeCoordinates(Console console, Product product, ConsoleParsing consoleParsing) {
        boolean flag = true;
        String answer;
        Integer x = null;
        Float y = null;
        if (console.askQuestion("Хотите изменить координаты?")) {
            if (console.askQuestion("Хотите изменить координату X?")) {
                console.ask("Координата X: " + product.getCoordinates().getX());
                while (flag) {
                    answer = console.askFullQuestion("Введите координату х:");
                    try {
                        x = consoleParsing.toRightX(answer, console);
                        flag = false;
                    } catch (VariableException e) {
                        console.printError("Повторите ввод");
                    }
                }
                product.getCoordinates().setX(x);
            } else {
                console.ask("Координата Y: " + product.getCoordinates().getY());
                while (flag) {
                    answer = console.askFullQuestion("Введите координату y:");
                    flag = false;
                    try {
                        y = consoleParsing.toRightY(answer, console);
                    } catch (VariableException e) {
                        console.printError("Повторите ввод");
                    }
                }
                product.getCoordinates().setY(y);
            }
        }
    }
    private void changePrice(Console console, Product product, ConsoleParsing consoleParsing) {
        boolean flag = true;
        Long price = null;
        String answer;
        if (console.askQuestion("Хотите изменить price?")) {
            console.ask("Price: " + product.getPrice());
            while (flag) {
                answer = console.askFullQuestion("Введите price:");
                try {
                    price = consoleParsing.toRightPrice(answer, console);
                    flag = false;
                } catch (VariableException e) {
                    console.printError("Повторите ввод");
                }
            }
            product.setPrice(price);

        }
    }
    private void changeManufactureCost(Console console, Product product, ConsoleParsing consoleParsing) {
        boolean flag = true;
        String answer;
        Integer manufactureCost = null;
        if (console.askQuestion("Хотите изменить manufactureCost?")) {
            console.ask("ManufactureCost: " + product.getManufactureCost());
            while (flag) {
                answer = console.askFullQuestion("Введите manufactureCost:");
                try {
                    manufactureCost = consoleParsing.toRightNumberInt(answer, console);
                    flag = false;
                } catch (VariableException e) {
                    console.printError("Повторите ввод");
                }
            }
            product.setManufactureCost(manufactureCost);

        }
    }
    private void changeUnitOfMeasure(Console console, Product product, ConsoleParsing consoleParsing) {
        boolean flag = true;
        String answer;
        UnitOfMeasure unitOfMeasure = null;

        if (console.askQuestion("Хотите изменить unitOfMeasure?")) {
            console.ask("UnitOfMeasure: " + product.getUnitOfMeasure());
            while (flag) {
                answer = console.askFullQuestion(" Введите unitOfMeasure, возможные варианты: PCS, MILLILITERS, GRAMS");
                try {
                    unitOfMeasure = consoleParsing.toRightUnitOfMeasure(answer);
                    flag = false;
                } catch (VariableException | IllegalArgumentException e) {
                    console.printError("Повторите ввод");
                }
            }
            product.setUnitOfMeasure(unitOfMeasure);

        }
    }
    private void changePerson(Console console, Product product, ConsoleParsing consoleParsing) {
        changeNamePerson(console, product, consoleParsing);
        changeBirthday(console, product, consoleParsing);
        changeHeight(console, product, consoleParsing);
        changeLocation(console, product, consoleParsing);

    }

    private void changeNamePerson(Console console, Product product, ConsoleParsing consoleParsing) {
        boolean flag = true;
        String answer = null;
        if (console.askQuestion("Хотите изменить owner?")) {
            console.ask("Owner: " + product.getOwner());
            if (console.askQuestion("Хотите изменить имя владельца?")) {
                console.ask("UnitOfMeasure: " + product.getOwner().getName());
                while (flag) {
                    answer = console.askFullQuestion("Введите имя владельца");
                    try {
                        consoleParsing.toRightName(answer, console);
                        flag = false;
                    } catch (VariableException e) {
                        console.printError("Повторите ввод");
                    }
                }
                product.getOwner().setName(answer);
            }
        }
    }
    private void changeBirthday(Console console, Product product, ConsoleParsing consoleParsing) {
        boolean flag = true;
        String answer;
        ZonedDateTime birthday = null;
        if (console.askQuestion("Хотите изменить дату рождения владельца?")) {
            console.ask("Birthday: " + product.getOwner().getBirthday());
            while (flag) {
                answer = console.askFullQuestion("Введите новую дату рождения владельца:");
                try {
                    birthday = consoleParsing.toRightBirthday(answer, console);
                    flag = false;
                } catch (VariableException e) {
                    console.printError("Повторите ввод");
                }
            }
            product.getOwner().setBirthday(birthday);
        }
    }
    private void changeHeight(Console console, Product product, ConsoleParsing consoleParsing) {
        boolean flag = true;
        String answer;
        Integer height = null;
        if (console.askQuestion("Хотите изменить рост владельца?")) {
            console.ask("Height: " + product.getOwner().getHeight());
            while (flag) {
                answer = console.askFullQuestion("Введите новый рост владельца:");
                try {
                    height = consoleParsing.toRightHeight(answer, console);
                    flag = false;
                } catch (VariableException e) {
                    console.printError("Повторите ввод");
                }
            }
            product.getOwner().setHeight(height);
        }
    }
    private void changeLocation(Console console, Product product, ConsoleParsing consoleParsing) {

        if (console.askQuestion("Хотите изменить локацию владельца?")) {
            console.ask("Location: " + product.getOwner().getLocation());
            changeLocationX(console, product, consoleParsing);
            changeLocationY(console, product, consoleParsing);
            changeLocationName(console, product, consoleParsing);
        }
    }

    private void changeLocationX(Console console, Product product, ConsoleParsing consoleParsing) {
        String answer;
        boolean flag = true;
        Long price = null;
        if (console.askQuestion("Хотите изменить координату х локации?")) {
            console.ask("Location x: " + product.getOwner().getLocation().getX());
            while (flag) {
                answer = console.askFullQuestion("Введите новый координату х локации:");
                try {
                    price = consoleParsing.toRightNumberLong(answer, console);
                    flag = false;
                } catch (VariableException e) {
                    console.printError("Повторите ввод");
                }
            }
            product.getOwner().getLocation().setX(price);
        }
    }
    private void changeLocationY(Console console, Product product, ConsoleParsing consoleParsing) {
        String answer;
        boolean flag = true;
        Integer height = null;
        if (console.askQuestion("Хотите изменить координату у локации?")) {
            console.ask("Location y: " + product.getOwner().getLocation().getY());
            while (flag) {
                answer = console.askFullQuestion("Введите новый координату y локации:");
                try {
                    height = consoleParsing.toRightNumberInt(answer, console);
                    flag = false;
                } catch (VariableException e) {
                    console.printError("Повторите ввод");
                }
            }
            product.getOwner().getLocation().setY(height);
        }
    }
    private void changeLocationName(Console console, Product product, ConsoleParsing consoleParsing) {
        boolean flag = true;
        String answer = null;
        if (console.askQuestion("Хотите изменить название локации?")) {
            console.ask("Location: " + product.getOwner().getLocation().getName());
            while (flag) {
                answer = console.askFullQuestion("Введите новое название локации:");
                try {
                    consoleParsing.toRightName(answer, console);
                    flag = false;
                } catch (VariableException e) {
                    console.printError("Повторите ввод");
                }
            }
            product.getOwner().getLocation().setName(answer);
        }
    }
}


