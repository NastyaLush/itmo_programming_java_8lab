package test.laba.common.dataClasses;



import test.laba.common.IO.Console;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Comparator;

/**
 * data class
 */
@XmlRootElement(name = "coordinates")
@XmlType(propOrder = { "x", "y" })
public class Coordinates implements Comparable<Coordinates>, Serializable {
    private Integer x; //Значение поля должно быть больше -233, Поле не может быть null
    private Float y; //Поле не может быть null


    private Console console;
    private final int minNumberOfX = -233;

    public Coordinates() {
    }
    /**
     * the constructor create location object
     */
    public Coordinates(Integer x, Float y)  {
        if (x >= minNumberOfX) {
            this.x = x;
        }
        this.y = y;

    }

    public Integer getX() {
        return x;
    }

    public Float getY() {
        return y;
    }

    public void setX(Integer x) {
        if (x >= minNumberOfX) {
            this.x = x;
        } else {
            /*console.printError("Ошибка при добавлении координаты X:значение поля x должно быть больше -233, Поле не может быть null\n"
                    + "Вы ввели: " + x);*/
            // TODO: 16.04.2022 error handlinh
        }
    }

    public void setY(Float y) {
        if (y != null) {
            this.y = y;
        } else {
            /*console.printError("Ошибка при добавлении координаты Y: Поле не может быть null\n"
                    + "Вы ввели: " + y);*/
            // TODO: 16.04.2022 error handlinh
        }

    }

    @Override
    public String toString() {
        return "Coordinates{"
                + "x=" + x
                + ", y=" + y
                + '}';
    }
    public boolean isRightCoordinates() {
        return x > minNumberOfX && y != null;
    }

    @Override
    public int compareTo(Coordinates o) {
        return Comparator.comparing(Coordinates :: getX).
        thenComparing(Coordinates :: getY).compare(this, o);
    }
}
