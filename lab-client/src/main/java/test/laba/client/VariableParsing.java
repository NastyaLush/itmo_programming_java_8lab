package test.laba.client;



import test.laba.common.exception.VariableException;
import test.laba.common.IO.Console;
import test.laba.common.dataClasses.UnitOfMeasure;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * pars variables from string to right vision
 */
public  class VariableParsing {
    private final int falseX = -233;

    /**
     * the constructor accepts object for print
     * @param console console for work
     */

    /**
     * pars string name to right field name (not null)
     * @param oldName name for parsing
     * @return string with right name
     * @throws VariableException throws when parsing is impossible
     */
    public  String toRightName(String oldName) throws VariableException {
        String name = oldName.trim();
        if (name == null || name.isEmpty()) {
            throw new VariableException("Данное поле не может быть пустым");
        }
        return name;
    }

    /**
     * pars string x to coordination x ( not null and more than -233)
     * @param x number for parsing
     * @return integer
     * @throws VariableException throws when parsing is impossible
     */
    public  Integer toRightX(String x) throws VariableException {
        int rightX;
        try {
            rightX = Integer.parseInt(x);
        } catch (NumberFormatException e) {
            throw new VariableException("Должно быть введено целое число типа int");
        }
        if (rightX <= falseX) {
            throw new VariableException("Число должно быть больше -233");
        }
        return rightX;
    }

    /**
     * pars string y to coordination y ( not null)
     * @param y number for parsing
     * @return float
     * @throws VariableException throws when parsing is impossible
     */
    public Float toRightY(String y) throws VariableException {
        float rightY;
        try {
            rightY = Float.parseFloat(y);
        } catch (NumberFormatException e) {
            throw new VariableException("Должно быть введено число типа float");
        }
        return rightY;
    }

    /**
     * pars string price to product price, the number must be integer(type long) and more than zero
     * @param price number for parsing
     * @return long
     * @throws VariableException throws when parsing is impossible
     */
    public  Long toRightPrice(String price) throws VariableException {
        long rightPrice;
        try {
            rightPrice = Long.parseLong(price);
        } catch (NumberFormatException e) {
            throw new VariableException("Должно быть введено целое число типа long, вы ввели: " + price);
        }
        if (rightPrice <= 0) {
            throw new VariableException("Число должно быть больше 0, вы ввели: " + price);
        }
        return rightPrice;
    }

    /**
     * pars string number to int number
     * @param number number for parsing
     * @return integer
     * @throws VariableException throws when parsing is impossible
     */
    public  Integer toRightNumberInt(String number) throws VariableException {
        int manufactureCost;
            try {
                manufactureCost = Integer.valueOf(number);
            } catch (NumberFormatException e) {
                throw new VariableException("Неправильный тип переменной, ожидалось целое число типа int, вы ввели: " + number);

            }
        return manufactureCost;
    }

    /**
     * pars string number to integer long number
     * @param number number for parsing
     * @return long
     * @throws VariableException throws when parsing is impossible
     */
    public Long toRightNumberLong(String number) throws VariableException {
        try {
            return Long.valueOf(number);
        } catch (NumberFormatException e) {
            throw new VariableException("Неправильный тип переменной, ожидалось целое число типа long, вы ввели: " + number);

        }
    }

    /**
     * pars string argument to unitOfMeasure type of UnitOfMeasure
     * @param unit unit for parsing to unit of measure
     * @return UnitOfMeasure
     * @throws VariableException throws when parsing is impossible
     */
    public UnitOfMeasure toRightUnitOfMeasure(String unit) throws VariableException {
        try{
            return UnitOfMeasure.valueOf(unit.toUpperCase());
        } catch (IllegalArgumentException e){
            throw  new VariableException("Can't parse to Unit Of Measure, please chose one of: " + "PCS, MILLILITERS, GRAMS" );
        }
    }

    /**
     * pars string height to integer height, which must be more zero
     * @param height number for parsing
     * @return UnitOfMeasure
     * @throws VariableException throws when parsing is impossible
     */
    public Integer toRightHeight(String height) throws VariableException {
        Integer rightHeight = null;
        if (height != null) {
            try {
                rightHeight = Integer.valueOf(height);
            } catch (NumberFormatException e) {
                throw new VariableException("Должно быть введено целое число, вы ввели: " + rightHeight);
            }
            if (rightHeight <= 0) {
                throw new VariableException("Число должно быть больше 0, вы ввели: " + rightHeight);
            }
        }
        return rightHeight;
    }

    /**
     * pars string birthday to birthday type of ZonedDAteTime with pattern "dd-MM-yyyy"
     * @param birthday string for parsing to ZonedDateTime
     * @return ZonedDateTime
     * @throws VariableException throws when parsing is impossible
     */
    public ZonedDateTime toRightBirthday(String birthday) throws VariableException {
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
                throw new VariableException("Неправильный формат данных, повторите ввод в формате ДД-MM-ГГГГ или ДД-ММ-ГГГГ ЧЧ:ММ:СС, вы ввели: " + birthday);
            }
        }
        return birth;
    }

    /**
     * pars string to long number
     * @param number number for parsing
     * @return long number
     */
    public  Long toLongNumber(String number) throws VariableException {
       String oldX = number;
        try {
            return Long.valueOf(oldX.trim());
        } catch (NumberFormatException e) {
            throw new VariableException("Неправильный формат данных. Вы ввели:\"" + oldX + "\", ожидалось число типа long");
            // TODO: 16.04.2022 create normal method
        }
    }
    public Long createKey(String id) {
        String newID = id;
        return Long.valueOf(newID);
    }
}



