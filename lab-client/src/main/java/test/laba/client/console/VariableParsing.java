package test.laba.client.console;



import test.laba.client.exception.VariableException;
import test.laba.client.mainClasses.UnitOfMeasure;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public  class VariableParsing {
    private final int falseX = -233;

    public  void toRightName(String s, Console console) throws VariableException {
        if (s == null || s.isEmpty()) {
            throw new VariableException("Данное поле не может быть пустым", console);
        }
    }
    public  Integer toRightX(String x, Console console) throws VariableException {
        int rightX;
        try {
            rightX = Integer.parseInt(x);
        } catch (NumberFormatException e) {
            throw new VariableException("Должно быть введено целое число", console);
        }
        if (rightX < falseX) {
            throw new VariableException("Число должно быть больше -233", console);
        }
        return rightX;
    }
    public Float toRightY(String y, Console console) throws VariableException {
        float rightY;
        try {
            rightY = Float.parseFloat(y);
        } catch (NumberFormatException e) {
            throw new VariableException("Должно быть введено число", console);
        }
        return rightY;
    }
    public  Long toRightPrice(String price, Console console) throws VariableException {
        long rightPrice;
        try {
            rightPrice = Long.parseLong(price);
        } catch (NumberFormatException e) {
            throw new VariableException("Должно быть введено целое число", console);
        }
        if (rightPrice <= 0) {
            throw new VariableException("Число должно быть больше 0", console);
        }
        return rightPrice;
    }
    public  Integer toRightNumber(String man, Console console) throws VariableException {
        Integer manufactureCost = null;
        if (man == null || man.isEmpty()) {
            return manufactureCost;
        } else {

            try {
                manufactureCost = Integer.valueOf(man);
            } catch (NumberFormatException e) {
                throw new VariableException("Неправильный тип переменной, ожидалось целое число", console);

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
                throw new VariableException("Неправильный тип переменной, ожидалось целое число", console);

            }
        }
        return x;

    }
    public UnitOfMeasure toRightUnitOfMeasure(String unit, Console console) throws VariableException {
        switch (unit) {
            case "PCS" :
                return UnitOfMeasure.PCS;

            case "MILLILITERS" :
                return UnitOfMeasure.MILLILITERS;

            case "GRAMS" :
                return UnitOfMeasure.GRAMS;

            case "" :
                return UnitOfMeasure.UN_INIT;

            default : throw new VariableException("Неправильный тип данных, должно быть PCS, MILLILITERS, GRAMS", console);
        }
    }
    public Integer toRightHeight(String height, Console console) throws VariableException {
        Integer rightHeight = null;
        if (height != null) {
            try {
                rightHeight = Integer.valueOf(height);
            } catch (NumberFormatException e) {
                throw new VariableException("Должно быть введено целое число", console);
            }
            if (rightHeight <= 0) {
                throw new VariableException("Число должно быть больше 0", console);
            }
        }
        return rightHeight;
    }
    public ZonedDateTime toRightBirthday(String birthday, Console console) throws VariableException {
        ZonedDateTime birth;
        final int x3 = 3;
        final int x5 = 5;
        final int x6 = 6;
        final int x10 = 10;
        final int x0 = 0;
        final int x2 = 2;
        try {
            int date = Integer.parseInt(birthday.substring(x0, x2));
            int month = Integer.parseInt(birthday.substring(x3, x5));
            int year = Integer.parseInt(birthday.substring(x6, x10));
            int hour = 0;
            int min = 0;
            int sec = 0;
            int nsec = 0;
            birth = ZonedDateTime.of(year, month, date, hour, min, sec, nsec, ZoneId.of("Europe/Moscow"));
        } catch (Exception e) {
            throw new VariableException("Неправильный формат даты, проверьте. Формат должен соответствовать:ДД-ММ-ГГГГ.", console);
        }
        return birth;
    }
    public  Long toLongNumber(String x, Console console)  {
        String rightX = x;
        try {
            return Long.valueOf(rightX);
        } catch (NumberFormatException e) {
            console.printError("Неправильный формат данных\nВведите новый key");
            rightX = console.scanner();
            return toLongNumber(rightX, console);
        }
    }
}



