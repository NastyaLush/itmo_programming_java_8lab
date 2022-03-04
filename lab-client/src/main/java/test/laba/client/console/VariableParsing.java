package test.laba.client.console;



import test.laba.client.exception.VariableException;
import test.laba.client.mainClasses.UnitOfMeasure;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


public  class VariableParsing {
    protected final Console console;
    private final int falseX = -233;

    public VariableParsing(Console console) {
        this.console = console;
    }

    public  String toRightName(String s) throws VariableException {
        if (s == null || s.isEmpty()) {
            throw new VariableException("Данное поле не может быть пустым", console);
        }
        return s;
    }
    public  Integer toRightX(String x) throws VariableException {
        int rightX;
        try {
            rightX = Integer.parseInt(x);
        } catch (NumberFormatException e) {
            throw new VariableException("Должно быть введено целое число типа int", console);
        }
        if (rightX <= falseX) {
            throw new VariableException("Число должно быть больше -233", console);
        }
        return rightX;
    }
    public Float toRightY(String y) throws VariableException {
        float rightY;
        try {
            rightY = Float.parseFloat(y);
        } catch (NumberFormatException e) {
            throw new VariableException("Должно быть введено число типа float", console);
        }
        return rightY;
    }
    public  Long toRightPrice(String price) throws VariableException {
        long rightPrice;
        try {
            rightPrice = Long.parseLong(price);
        } catch (NumberFormatException e) {
            throw new VariableException("Должно быть введено целое число типа long, вы ввели: " + price, console);
        }
        if (rightPrice <= 0) {
            throw new VariableException("Число должно быть больше 0, вы ввели: " + price, console);
        }
        return rightPrice;
    }
    public  Integer toRightNumberInt(String man) throws VariableException {
        Integer manufactureCost = null;
        if (man == null || man.isEmpty()) {
            return manufactureCost;
        } else {

            try {
                manufactureCost = Integer.valueOf(man);
            } catch (NumberFormatException e) {
                throw new VariableException("Неправильный тип переменной, ожидалось целое число типа int, вы ввели: " + man, console);

            }
        }
        return manufactureCost;
    }
    public Long toRightNumberLong(String man) throws VariableException {
        Long x = null;
        if (man == null || man.isEmpty()) {
            return x;
        } else {
            try {
                x = Long.valueOf(man);
            } catch (NumberFormatException e) {
                throw new VariableException("Неправильный тип переменной, ожидалось целое число типа long, вы ввели: " + man, console);

            }
        }
        return x;

    }
    public UnitOfMeasure toRightUnitOfMeasure(String unit) throws VariableException {
           return UnitOfMeasure.valueOf(unit.toUpperCase());
    }
    public Integer toRightHeight(String height) throws VariableException {
        Integer rightHeight = null;
        if (height != null) {
            try {
                rightHeight = Integer.valueOf(height);
            } catch (NumberFormatException e) {
                throw new VariableException("Должно быть введено целое число, вы ввели: " + rightHeight, console);
            }
            if (rightHeight <= 0) {
                throw new VariableException("Число должно быть больше 0, вы ввели: " + rightHeight, console);
            }
        }
        return rightHeight;
    }
    public ZonedDateTime toRightBirthday(String birthday) throws VariableException {
        ZonedDateTime birth;
        try {
            LocalDate parsed = LocalDate.parse(birthday, DateTimeFormatter.ISO_LOCAL_DATE);
            birth = ZonedDateTime.of(parsed, LocalTime.MIDNIGHT, ZoneId.of("Europe/Berlin"));
        } catch (DateTimeException e) {
            throw new VariableException("Неправильный формат данных, повторите ввод в формате ГГГГ-ММ-ДД, вы ввели: " + birthday, console);
        }
        return birth;
    }
    public  Long toLongNumber(String x)  {
       String oldX = x;
        try {
            return Long.valueOf(oldX.trim());
        } catch (NumberFormatException e) {
            console.printError("Неправильный формат данных, вы ввели:" + oldX + ")\nВведите новый key");
            oldX = console.scanner();
            return toLongNumber(oldX);
        }
    }
    public Long createKey(String id) {
        String newID = id;
        Long cid = null;
        boolean flag = true;
        while (flag) {
            try {
                cid = Long.valueOf(newID);
                flag = false;
            } catch (NumberFormatException e) {
                console.printError("Неправильный формат ввода key, повторите попытку, вы ввели: " + id + e);
                console.print("Введите key:");
                newID = console.scanner();
            }
        }
        return cid;
    }
}



