package test.laba.client;

import test.laba.common.IO.Console;
import test.laba.common.dataClasses.Product;
import test.laba.common.dataClasses.UnitOfMeasure;
import test.laba.common.exception.VariableException;

import java.time.ZonedDateTime;

public class UpdateId {
        Console console;
        ConsoleParsing consoleParsing;


        public UpdateId(Console console, ConsoleParsing consoleParsing){
                this.console = console;
                this.consoleParsing = consoleParsing;
        }
        public Product execute(Product product){
                System.out.println(product);
                changeNameProduct(product);
                changeCoordinates(product);
                changePrice(product);
                changeManufactureCost(product);
                changeUnitOfMeasure(product);
                changePerson(product);
                return product;
        }

        private void changeNameProduct(Product product) {
                if (console.askQuestion("Хотите изменить название продукта?(yes/no/да/нет)")) {
                        String answer = parsFieldFromConsole(consoleParsing::toRightName, product::getName, "Введите измененное имя:");
                        product.setName(answer);
                }
        }
        private void changeCoordinates(Product product) {
                if (console.askQuestion("Хотите изменить координаты?(yes/no/да/нет)")) {
                        if (console.askQuestion("Хотите изменить координату X?(yes/no/да/нет)")) {
                                Integer x = parsFieldFromConsole(consoleParsing::toRightX, product.getCoordinates()::getX, "Введите координату х: ");
                                product.getCoordinates().setX(x);
                        }
                        if (console.askQuestion("Хотите изменить координату Y?(yes/no/да/нет)")) {
                                Float y = parsFieldFromConsole(consoleParsing::toRightY, product.getCoordinates()::getY, "Введите координату y: ");
                                product.getCoordinates().setY(y);
                        }
                }
        }
        private void changePrice(Product product) {
                if (console.askQuestion("Хотите изменить price?(yes/no/да/нет)")) {
                        Long price = parsFieldFromConsole(consoleParsing::toRightPrice, product::getPrice, "Введите price: ");
                        product.setPrice(price);

                }
        }
        private void changeManufactureCost(Product product) {
                if (console.askQuestion("Хотите изменить manufactureCost?(yes/no/да/нет)")) {
                        Integer manufactureCost = parsFieldFromConsole(consoleParsing::toRightNumberInt, product::getManufactureCost, "Введите manufactureCost: ");
                        product.setManufactureCost(manufactureCost);

                }
        }
        private void changeUnitOfMeasure(Product product) {
                if (console.askQuestion("Хотите изменить unitOfMeasure?(yes/no/да/нет)")) {
                        UnitOfMeasure unitOfMeasure = parsFieldFromConsole(consoleParsing::toRightUnitOfMeasure, product::getUnitOfMeasure, "Варианты: PCS, MILLILITRES, GRAMS");
                        product.setUnitOfMeasure(unitOfMeasure);

                }
        }
        private void changePerson(Product product) {
                if (console.askQuestion("Хотите изменить owner?(yes/no/да/нет)")) {
                        console.ask("Owner: " + product.getOwner());
                        if (product.getOwner() != null) {
                                changeNamePerson(product);
                                changeBirthday(product);
                                changeHeight(product);
                                changeLocation(product);
                        } else {
                                product.setOwner(consoleParsing.parsPersonFromConsole());
                        }
                }
        }

        private void changeNamePerson(Product product) {
                if (console.askQuestion("Хотите изменить имя владельца?(yes/no/да/нет)")) {
                        String answer = parsFieldFromConsole(consoleParsing::toRightName, product.getOwner()::getName, "Введите имя владельца: ");
                        product.getOwner().setName(answer);
                }
        }
        private void changeBirthday(Product product) {
                if (console.askQuestion("Хотите изменить дату рождения владельца?(yes/no/да/нет)")) {
                        ZonedDateTime birthday = parsFieldFromConsole(consoleParsing::toRightBirthday, product.getOwner()::getBirthday, "Введите дату рождения владельца в формате ДД-MM-ГГГГ или ДД-ММ-ГГГГ ЧЧ:ММ:СС: ");
                        product.getOwner().setBirthday(birthday);
                }
        }
        private void changeHeight(Product product) {
                if (console.askQuestion("Хотите изменить рост владельца?(yes/no/да/нет)")) {
                        Integer height = parsFieldFromConsole(consoleParsing::toRightHeight, product.getOwner()::getHeight, "Введите рост владельца: ");
                        product.getOwner().setHeight(height);
                }
        }
        private void changeLocation(Product product) {

                if (console.askQuestion("Хотите изменить локацию владельца?(yes/no/да/нет)")) {
                        console.ask("Location: " + product.getOwner().getLocation());
                        changeLocationX(product);
                        changeLocationY(product);
                        changeLocationName(product);
                }
        }

        private void changeLocationX(Product product) {
                if (console.askQuestion("Хотите изменить координату х локации?(yes/no/да/нет)")) {
                        Long xLocation = parsFieldFromConsole(consoleParsing::toRightNumberLong, product.getOwner().getLocation()::getX, "Введите координату х: ");
                        product.getOwner().getLocation().setX(xLocation);
                }
        }
        private void changeLocationY(Product product) {
                if (console.askQuestion("Хотите изменить координату у локации?(yes/no/да/нет)")) {
                        Integer y = parsFieldFromConsole(consoleParsing::toRightNumberInt, product.getOwner().getLocation()::getY, "Введите координату y: ");
                        product.getOwner().getLocation().setY(y);
                }
        }
        private void changeLocationName(Product product) {
                if (console.askQuestion("Хотите изменить название локации?(yes/no/да/нет)")) {
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
                        } catch (IllegalArgumentException | VariableException e) {
                                console.printError("Неправильный тип данных, повторите ввод");
                        }
                }
                return value;
        }
        private <T> T  parsFieldFromConsole(IFunction pars, String request) {
                String answer;
                T value;
                console.ask("Значение поля сейчас: " + null);
                while (true) {
                        answer = console.askFullQuestion(request);
                        try {
                                value = (T) pars.function(answer);
                                break;
                        } catch (IllegalArgumentException | VariableException e) {
                                console.printError("Неправильный тип данных, повторите ввод");
                        }
                }
                return value;
        }
        private interface GetFunction<T> {
                T getFunction();
        }
        private interface IFunction<T> {
                T function(String oldField) throws VariableException;
        }
        }
