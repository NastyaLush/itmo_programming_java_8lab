package test.laba.client.commands;



import test.laba.client.console.Console;
import test.laba.client.console.ConsoleParsing;
import test.laba.client.exception.VariableException;
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
            changeNameProduct(console, root, consoleParsing,  key);
            changeCoordinates(console, root, consoleParsing,  key);
            changePrice(console, root, consoleParsing,  key);
            changeManufactureCost(console, root, consoleParsing,  key);
            changeUnitOfMeasure(console, root, consoleParsing,  key);
            changePerson(console, root, consoleParsing,  key);
        }


    }

    private void changeNameProduct(Console console, Root root, ConsoleParsing consoleParsing, Long  key) {
        boolean flag = true;
        String answer = null;
        if (console.askQuestion("Хотите изменить название продукта?")) {
            console.ask("Имя продукта: " + root.getProducts().get(key).getName());
            while (flag) {
                answer = console.askFullQuestion("Введите измененное имя:");
                try {
                    consoleParsing.toRightName(answer, console);
                    flag = false;
                } catch (VariableException e) {
                    console.printError("Повторите ввод");
                }
            }
            root.getProducts().get(key).setName(answer);
        }
    }
    private void changeCoordinates(Console console, Root root, ConsoleParsing consoleParsing, Long  key) {
        boolean flag = true;
        String answer;
        Integer x = null;
        Float y = null;
        if (console.askQuestion("Хотите изменить координаты?")) {
            if (console.askQuestion("Хотите изменить координату X?")) {
                console.ask("Координата X: " + root.getProducts().get(key).getCoordinates().getX());
                while (flag) {
                    answer = console.askFullQuestion("Введите координату х:");
                    try {
                        x = consoleParsing.toRightX(answer, console);
                        flag = false;
                    } catch (VariableException e) {
                        console.printError("Повторите ввод");
                    }
                }
                root.getProducts().get(key).getCoordinates().setX(x);
            } else {
                console.ask("Координата Y: " + root.getProducts().get(key).getCoordinates().getY());
                while (flag) {
                    answer = console.askFullQuestion("Введите координату y:");
                    flag = false;
                    try {
                        y = consoleParsing.toRightY(answer, console);
                    } catch (VariableException e) {
                        console.printError("Повторите ввод");
                    }
                }
                root.getProducts().get(key).getCoordinates().setY(y);
            }
        }
    }
    private void changePrice(Console console, Root root, ConsoleParsing consoleParsing, Long  key) {
        boolean flag = true;
        Long price = null;
        String answer;
        if (console.askQuestion("Хотите изменить price?")) {
            console.ask("Price: " + root.getProducts().get(key).getPrice());
            while (flag) {
                answer = console.askFullQuestion("Введите price:");
                try {
                    price = consoleParsing.toRightPrice(answer, console);
                    flag = false;
                } catch (VariableException e) {
                    console.printError("Повторите ввод");
                }
            }
            root.getProducts().get(key).setPrice(price);

        }
    }
    private void changeManufactureCost(Console console, Root root, ConsoleParsing consoleParsing, Long  key) {
        boolean flag = true;
        String answer;
        Integer manufactureCost = null;
        if (console.askQuestion("Хотите изменить manufactureCost?")) {
            console.ask("ManufactureCost: " + root.getProducts().get(key).getManufactureCost());
            while (flag) {
                answer = console.askFullQuestion("Введите manufactureCost:");
                try {
                    manufactureCost = consoleParsing.toRightNumber(answer, console);
                    flag = false;
                } catch (VariableException e) {
                    console.printError("Повторите ввод");
                }
            }
            root.getProducts().get(key).setManufactureCost(manufactureCost);

        }
    }
    private void changeUnitOfMeasure(Console console, Root root, ConsoleParsing consoleParsing, Long  key) {
        boolean flag = true;
        String answer;
        UnitOfMeasure unitOfMeasure = null;

        if (console.askQuestion("Хотите изменить unitOfMeasure?")) {
            console.ask("UnitOfMeasure: " + root.getProducts().get(key).getUnitOfMeasure());
            while (flag) {
                answer = console.askFullQuestion(" Введите unitOfMeasure, возможные варианты: PCS, MILLILITERS, GRAMS");
                try {
                    unitOfMeasure = consoleParsing.toRightUnitOfMeasure(answer, console);
                    flag = false;
                } catch (VariableException e) {
                    console.printError("Повторите ввод");
                }
            }
            root.getProducts().get(key).setUnitOfMeasure(unitOfMeasure);

        }
    }
    private void changePerson(Console console, Root root, ConsoleParsing consoleParsing, Long  key) {
        changeNamePerson(console, root, consoleParsing,  key);
        changeBirthday(console, root, consoleParsing,  key);
        changeHeight(console, root, consoleParsing,  key);
        changeLocation(console, root, consoleParsing,  key);

    }

    private void changeNamePerson(Console console, Root root, ConsoleParsing consoleParsing, Long  key) {
        boolean flag = true;
        String answer = null;
        if (console.askQuestion("Хотите изменить owner?")) {
            console.ask("Owner: " + root.getProducts().get(key).getOwner());
            if (console.askQuestion("Хотите изменить имя владельца?")) {
                console.ask("UnitOfMeasure: " + root.getProducts().get(key).getOwner().getName());
                while (flag) {
                    answer = console.askFullQuestion("Введите имя владельца");
                    try {
                        consoleParsing.toRightName(answer, console);
                        flag = false;
                    } catch (VariableException e) {
                        console.printError("Повторите ввод");
                    }
                }
                root.getProducts().get(key).getOwner().setName(answer);
            }
        }
    }
    private void changeBirthday(Console console, Root root, ConsoleParsing consoleParsing, Long  key) {
        boolean flag = true;
        String answer;
        ZonedDateTime birthday = null;
        if (console.askQuestion("Хотите изменить дату рождения владельца?")) {
            console.ask("Birthday: " + root.getProducts().get(key).getOwner().getBirthday());
            while (flag) {
                answer = console.askFullQuestion("Введите новую дату рождения владельца:");
                try {
                    birthday = consoleParsing.toRightBirthday(answer, console);
                    flag = false;
                } catch (VariableException e) {
                    console.printError("Повторите ввод");
                }
            }
            root.getProducts().get(key).getOwner().setBirthday(birthday);
        }
    }
    private void changeHeight(Console console, Root root, ConsoleParsing consoleParsing, Long  key) {
        boolean flag = true;
        String answer ;
        Integer height = null;
        if (console.askQuestion("Хотите изменить рост владельца?")) {
            console.ask("Height: " + root.getProducts().get(key).getOwner().getHeight());
            while (flag) {
                answer = console.askFullQuestion("Введите новый рост владельца:");
                try {
                    height = consoleParsing.toRightHeight(answer, console);
                    flag = false;
                } catch (VariableException e) {
                    console.printError("Повторите ввод");
                }
            }
            root.getProducts().get(key).getOwner().setHeight(height);
        }
    }
    private void changeLocation(Console console, Root root, ConsoleParsing consoleParsing, Long  key) {

        if (console.askQuestion("Хотите изменить локацию владельца?")) {
            console.ask("Location: " + root.getProducts().get(key).getOwner().getLocation());
            changeLocationX(console, root, consoleParsing,  key);
            changeLocationY(console, root, consoleParsing,  key);
            changeLocationName(console, root, consoleParsing,  key);
        }
    }

    private void changeLocationX(Console console, Root root, ConsoleParsing consoleParsing, Long  key) {
        String answer;
        boolean flag = true;
        Long price = null;
        if (console.askQuestion("Хотите изменить координату х локации?")) {
            console.ask("Location x: " + root.getProducts().get(key).getOwner().getLocation().getX());
            while (flag) {
                answer = console.askFullQuestion("Введите новый координату х локации:");
                try {
                    price = consoleParsing.toRightNumberLong(answer, console);
                    flag = false;
                } catch (VariableException e) {
                    console.printError("Повторите ввод");
                }
            }
            root.getProducts().get(key).getOwner().getLocation().setX(price);
        }
    }
    private void changeLocationY(Console console, Root root, ConsoleParsing consoleParsing, Long  key) {
        String answer;
        boolean flag = true;
        Integer height = null;
        if (console.askQuestion("Хотите изменить координату у локации?")) {
            console.ask("Location y: " + root.getProducts().get(key).getOwner().getLocation().getY());
            while (flag) {
                answer = console.askFullQuestion("Введите новый координату y локации:");
                try {
                    height = consoleParsing.toRightNumber(answer, console);
                    flag = false;
                } catch (VariableException e) {
                    console.printError("Повторите ввод");
                }
            }
            root.getProducts().get(key).getOwner().getLocation().setY(height);
        }
    }
    private void changeLocationName(Console console, Root root, ConsoleParsing consoleParsing, Long  key) {
        boolean flag = true;
        String answer = null;
        if (console.askQuestion("Хотите изменить название локации?")) {
            console.ask("Location: " + root.getProducts().get(key).getOwner().getLocation().getName());
            while (flag) {
                answer = console.askFullQuestion("Введите новое название локации:");
                try {
                    consoleParsing.toRightName(answer, console);
                    flag = false;
                } catch (VariableException e) {
                    console.printError("Повторите ввод");
                }
            }
            root.getProducts().get(key).getOwner().getLocation().setName(answer);
        }
    }
}


