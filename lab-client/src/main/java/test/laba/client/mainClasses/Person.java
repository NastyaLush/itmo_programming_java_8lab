package test.laba.client.mainClasses;


import test.laba.client.console.Console;
import test.laba.client.exception.CreateError;

import java.time.ZonedDateTime;

public class Person {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private java.time.ZonedDateTime birthday; //Поле не может быть null
    private Integer height; //Значение поля должно быть больше 0
    private Location location; //Поле не может быть null

    public Person(String name, ZonedDateTime birthday, Integer height, Location location, Console console) throws CreateError {
        if (name == null || name.isEmpty() || height <= 0  || location == null) {
            throw new CreateError(" ошибка при создании объекта Person: проверьте данные"
                   + " \nПоле name не может быть null или быть пустым"
                    + "\nПоле birthday не может быть null"
                    + "\nЗначение поля height должно быть больше 0"
                    + "\nПоле Location не может быть null", console);
        } else {
            this.name = name;
            this.birthday = birthday;
            this.height = height;
            this.location = location;
        }
    }

    public String getName() {
        return name;
    }
    public String getBirthday() {
        final int numberOfSingleDigit = 10;
        String s = (birthday.getDayOfMonth() < numberOfSingleDigit) ? "0" + birthday.getDayOfMonth() : birthday.getDayOfMonth() + "-";
        s += (birthday.getMonthValue() < numberOfSingleDigit) ? "0" + birthday.getMonthValue() : birthday.getMonthValue();
        s += "-" + birthday.getYear();
        return s;
    }
    public Integer getHeight() {
        return height;
    }
    public Location getLocation() {
        return location;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setBirthday(ZonedDateTime birthday) {
        this.birthday = birthday;
    }
    public void setHeight(Integer height) {
        this.height = height;
    }
    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Person:"
                + "name=" + name
                + ", birthday=" + getBirthday()
                + ", height=" + height
                + ", location=" + location;
    }
}
