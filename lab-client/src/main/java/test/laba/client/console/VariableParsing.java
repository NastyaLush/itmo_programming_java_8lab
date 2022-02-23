package test.laba.client.console;



import test.laba.client.exception.VariableException;
import test.laba.client.exception.exucuteError;
import test.laba.client.mainClasses.UnitOfMeasure;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public  class VariableParsing {

    public  void toRightName(String s, Console console) throws VariableException {
        if (s == null || s.isEmpty()) {
            throw new VariableException("Данное поле не может быть пустым",console);
        }
    }
    public  Integer toRightX(String x, Console console) throws VariableException {
        int X;
        try {
            X = Integer.parseInt(x);
        } catch (NumberFormatException e) {
            throw new VariableException("Должно быть введено целое число",console);
        }
        if (X < -233) {
            throw new VariableException("Число должно быть больше -233",console);
        }
        return X;
    }
    public Float toRightY(String y, Console console) throws VariableException {
        float Y;
        try {
            Y = Float.parseFloat(y);
        } catch (NumberFormatException e) {
            throw new VariableException("Должно быть введено число",console);
        }
        return Y;
    }
    public  Long toRightPrice(String price, Console console) throws VariableException {
        long Price;
        try {
            Price = Long.parseLong(price);
        } catch (NumberFormatException e) {
            throw new VariableException("Должно быть введено целое число",console);
        }
        if (Price <= 0) {
            throw new VariableException("Число должно быть больше 0",console);
        }
        return Price;
    }
    public  Integer toRightNumber(String man, Console console) throws VariableException {
        Integer manufactureCost = null;
        if (man == null || man.isEmpty()) {
            return manufactureCost;
        } else {

            try {
                manufactureCost = Integer.valueOf(man);
            } catch (NumberFormatException e) {
                throw new VariableException("Неправильный тип переменной, ожидалось целое число",console);

            }
        }
        return manufactureCost;

    }
    public Long toRightNumberLong(String man, Console console) throws VariableException {
        Long x = null;
        if (man == null || man.isEmpty()) {
            return x;
        } else {

            try {
                x = Long.valueOf(man);
            } catch (NumberFormatException e) {
                throw new VariableException("Неправильный тип переменной, ожидалось целое число",console);

            }
        }
        return x;

    }
    public UnitOfMeasure toRightUnitOfMeasure(String unit, Console console) throws VariableException {
        switch (unit) {
            case "PCS" -> {
                return UnitOfMeasure.PCS;
            }
            case "MILLILITERS" -> {
                return UnitOfMeasure.MILLILITERS;
            }
            case "GRAMS" -> {
                return UnitOfMeasure.GRAMS;
            }
            case "" -> {
                return UnitOfMeasure.UN_INIT;
            }
            default -> throw new VariableException("Неправильный тип данных, должно быть PCS, MILLILITERS, GRAMS",console);
        }
    }
    public Integer toRightHeight(String height, Console console) throws VariableException {
        Integer Height=null;
        if (height != null){
            try {
                Height = Integer.valueOf(height);
            } catch (NumberFormatException e) {
                throw new VariableException("Должно быть введено целое число",console);
            }
            if (Height <= 0) {
                throw new VariableException("Число должно быть больше 0",console);
            }
        }
        return Height;
    }
    public ZonedDateTime toRightBirthday(String birthday, Console console) throws VariableException {
        ZonedDateTime birth;
        try {
            int date = Integer.parseInt(birthday.substring(0, 2));
            int month = Integer.parseInt(birthday.substring(3, 5));
            int year = Integer.parseInt(birthday.substring(6,10));
            int hour = 0;
            int min = 0;
            int sec = 0;
            int nsec = 0;
            birth= ZonedDateTime.of(year,month,date,hour,min,sec,nsec, ZoneId.of("Europe/Moscow"));
        }
        catch (Exception e){
            throw new VariableException("Неправильный формат даты, проверьте. Формат должен соответствовать:ДД-ММ-ГГГГ.",console);
        }
        return birth;
    }
    public  Long toLongNumber(String x,Console console) throws exucuteError {
        try {
            return Long.valueOf(x);
        }
        catch (NumberFormatException e){
            console.printError("Неправильный формат данных\n Введите новый key");
            x= console.scanner();
            return toLongNumber(x,console);
        }
    }
}



