package test.laba.client.mainClasses;


import test.laba.client.console.Console;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Comparator;


@XmlRootElement(name = "coordinates")
@XmlType(propOrder = { "x", "y" })
public class Coordinates implements Comparable<Coordinates> {
    private Integer x; //Значение поля должно быть больше -233, Поле не может быть null
    private Float y; //Поле не может быть null


    private Console console;
    private final int minNumberOfX = -233;

    public Coordinates(){
    }
    @SuppressWarnings("all")
    public Coordinates(Integer x, Float y, Console console)  {
            if (x >= minNumberOfX) {
                this.x = x;
            }
            this.y = y;
            this.console = console;

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
            console.printError("Ошибка при добавлении координаты X:значение поля x должно быть больше -233, Поле не может быть null\n"
                    + "Вы ввели: " + x);
        }
    }

    public void setY(Float y) {
        if (y != null) {
            this.y = y;
        } else {
            console.printError("Ошибка при добавлении координаты Y: Поле не может быть null\n"
                    + "Вы ввели: " + y);
        }

    }

    @Override
    public String toString() {
        return "Coordinates{"
                + "x=" + x
                + ", y=" + y
                + '}';
    }
    public boolean isRightCoordinates(){
        return  x>minNumberOfX && x!=null && y!=null;
    }

    @Override
    public int compareTo(Coordinates o) {
        return Comparator.comparing(Coordinates :: getX).
        thenComparing(Coordinates :: getY).compare(this, o);
    }
}
