package test.laba.client.util;


import test.laba.common.exception.VariableException;
import test.laba.common.dataClasses.UnitOfMeasure;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * pars variables from string to right vision
 */
public final class VariableParsing {
    private static final int FALSE_X = -233;

    private VariableParsing() {
    }

    /**
     * pars string name to right field name (not null)
     *
     * @param oldName name for parsing
     * @return string with right name
     * @throws VariableException throws when parsing is impossible
     */
    public static String toRightName(String field, String oldName) throws VariableException {
        String name = oldName.trim();
        if (name.isEmpty()) {
            throw new VariableException(field, "Данное поле не может быть пустым");
        }
        return name;
    }

    /**
     * pars string x to coordination x ( not null and more than -233)
     *
     * @param x number for parsing
     * @return integer
     * @throws VariableException throws when parsing is impossible
     */
    public static Integer toRightX(String name, String x) throws VariableException {
        int rightX;
        try {
            rightX = Integer.parseInt(x);
        } catch (NumberFormatException e) {
            throw new VariableException(name, "Должно быть введено целое число типа int");
        }
        if (rightX <= FALSE_X) {
            throw new VariableException(name, "Число должно быть больше -233");
        }
        return rightX;
    }

    /**
     * pars string y to coordination y ( not null)
     *
     * @param y number for parsing
     * @return float
     * @throws VariableException throws when parsing is impossible
     */
    public static Float toRightY(String name, String y) throws VariableException {
        float rightY;
        try {
            rightY = Float.parseFloat(y);
        } catch (NumberFormatException e) {
            throw new VariableException(name, "Должно быть введено число типа float");
        }
        return rightY;
    }

    /**
     * pars string price to product price, the number must be integer(type long) and more than zero
     *
     * @param price number for parsing
     * @return long
     * @throws VariableException throws when parsing is impossible
     */
    public static Long toRightPrice(String name, String price) throws VariableException {
        long rightPrice;
        try {
            rightPrice = Long.parseLong(price);
        } catch (NumberFormatException e) {
            throw new VariableException(name, "Должно быть введено целое число типа long, вы ввели: " + price);
        }
        if (rightPrice <= 0) {
            throw new VariableException(name, "Число должно быть больше 0, вы ввели: " + price);
        }
        return rightPrice;
    }

    /**
     * pars string number to int number
     *
     * @param number number for parsing
     * @return integer
     * @throws VariableException throws when parsing is impossible
     */
    public static Integer toRightNumberInt(String name, String number) throws VariableException {
        int manufactureCost;
        try {
            manufactureCost = Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new VariableException(name, "Неправильный тип переменной, ожидалось целое число типа int, вы ввели: " + number);

        }
        return manufactureCost;
    }

    /**
     * pars string number to integer long number
     *
     * @param number number for parsing
     * @return long
     * @throws VariableException throws when parsing is impossible
     */
    public static Long toRightNumberLong(String name, String number) throws VariableException {
        try {
            return Long.valueOf(number);
        } catch (NumberFormatException e) {
            throw new VariableException(name, "Неправильный тип переменной, ожидалось целое число типа long, вы ввели: " + number);

        }
    }

    /**
     * pars string argument to unitOfMeasure type of UnitOfMeasure
     *
     * @param unit unit for parsing to unit of measure
     * @return UnitOfMeasure
     * @throws VariableException throws when parsing is impossible
     */
    public static UnitOfMeasure toRightUnitOfMeasure(String name, String unit) throws VariableException {
        try {
            System.out.println(unit);
            return UnitOfMeasure.valueOf(unit.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new VariableException(name, "Can't parse to Unit Of Measure, please chose one of: " + "PCS, MILLILITERS, GRAMS");
        }
    }

    /**
     * pars string height to integer height, which must be more zero
     *
     * @param height number for parsing
     * @return UnitOfMeasure
     * @throws VariableException throws when parsing is impossible
     */
    public static Integer toRightHeight(String name, String height) throws VariableException {
        Integer rightHeight = null;
        if (height != null) {
            try {
                rightHeight = Integer.valueOf(height);
            } catch (NumberFormatException e) {
                throw new VariableException(name, "Должно быть введено целое число, вы ввели: " + rightHeight);
            }
            if (rightHeight <= 0) {
                throw new VariableException(name, "Число должно быть больше 0, вы ввели: " + rightHeight);
            }
        }
        return rightHeight;
    }

    /**
     * pars string birthday to birthday type of ZonedDAteTime with pattern "dd-MM-yyyy"
     *
     * @param birthday string for parsing to ZonedDateTime
     * @return ZonedDateTime
     * @throws VariableException throws when parsing is impossible
     */
    public static ZonedDateTime toRightBirthday(String name, String birthday) throws VariableException {
        ZonedDateTime birth;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate parsed = LocalDate.parse(birthday, formatter);
            birth = ZonedDateTime.of(parsed, LocalTime.MIDNIGHT, ZoneId.of("Europe/Berlin"));
        } catch (DateTimeException e) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                LocalDateTime parsed = LocalDateTime.parse(birthday, formatter);
                birth = ZonedDateTime.of(parsed, ZoneId.of("Europe/Berlin"));
            } catch (DateTimeException exception) {
                throw new VariableException(name, "Неправильный формат данных, повторите ввод в формате ДД-MM-ГГГГ или ДД-ММ-ГГГГ ЧЧ:ММ:СС, вы ввели: " + birthday);
            }
        }
        return birth;
    }

    /**
     * pars string to long number
     *
     * @param number number for parsing
     * @return long number
     */
    public static Long toLongNumber(String name, String number) throws VariableException {
        try {
            return Long.valueOf(number.trim());
        } catch (NumberFormatException e) {
            throw new VariableException(name, "Неправильный формат данных. Вы ввели:\"" + number + "\", ожидалось число типа long");
        }
    }

}



