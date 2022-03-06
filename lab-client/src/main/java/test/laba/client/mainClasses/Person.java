package test.laba.client.mainClasses;


import test.laba.client.console.Console;
import test.laba.client.exception.CreateError;
import test.laba.client.exception.VariableException;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

@XmlRootElement(name = "owner")
public class Person implements Comparable<Person> {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private java.time.ZonedDateTime birthday; //Поле не может быть null
    private Integer height; //Значение поля должно быть больше 0
    private Location location; //Поле не может быть null

    public  Person() {
    }

    @SuppressWarnings("all")
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
        String s = birthday.getYear() + "-";
        s +=  (birthday.getDayOfMonth() < numberOfSingleDigit) ? "0" + birthday.getDayOfMonth() : birthday.getDayOfMonth() + "-";
        s +=  (birthday.getMonthValue() < numberOfSingleDigit) ? "0" + birthday.getMonthValue() : birthday.getMonthValue();
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
    @XmlElement
    public void setBirthday(String birthday) {
        try {
            LocalDate parsed = LocalDate.parse(birthday, DateTimeFormatter.ISO_LOCAL_DATE);
            this.birthday  = ZonedDateTime.of(parsed, LocalTime.MIDNIGHT, ZoneId.of("Europe/Berlin"));
        } catch (DateTimeException e) {
            throw new VariableException("Неправильный формат данных, повторите ввод в формате ГГГГ-ММ-ДД");
        }
    }
    public void setHeight(Integer height) {
        this.height = height;
    }
    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isRightPerson() {

        return name != null && !"".equals(name) && birthday != null && height > 0 && location !=  null && location.isRightLocation();
    }

    @Override
    public String toString() {
        return "Person:"
                + "name=" + name
                + ", birthday=" + getBirthday()
                + ", height=" + height
                + ", location=" + location;
    }

    @Override
    public int compareTo(Person o) {
        return Comparator.comparing(Person :: getName).
                thenComparing(Person :: getBirthday).
                thenComparing(Person :: getHeight).
                thenComparing(Person :: getLocation).compare(this, o);
    }
}
