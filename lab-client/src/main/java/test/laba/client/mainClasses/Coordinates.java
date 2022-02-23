package test.laba.client.mainClasses;


import test.laba.client.console.Console;
import test.laba.client.exception.CreateError;
import test.laba.client.exception.VariableException;

public class Coordinates {
    private int x; //Значение поля должно быть больше -233, Поле не может быть null
    private float y; //Поле не может быть null
    private Console console;


    public Coordinates(Integer x, Float y,Console console) throws CreateError {
        try {
            if (x <= -233) {
                this.x = x;
                this.y = y;
            }
            this.console=console;
        }
        catch (Exception e){
            throw new CreateError("Ошибка при попытке создания объекта Coordinates, проверьте:\n" +
                    "значение поля x  не может быть null\n" +
                    "значение поля y не может быть null\n"+
                    "Выши значения: "+ x+" и "+y,console);

        }

    }

    public Integer getX() {
        return x;
    }

    public Float getY() {
        return y;
    }

    public void setX(Integer x) {
        if (x>=-233) {
            this.x = x;
        }
        else console.printError("Ошибка при добавлении координаты X:значение поля x должно быть больше -233, Поле не может быть null\n"+
                "Вы ввели: "+ x);
    }

    public void setY(Float y) {
        if (y!= null) {
            this.y = y;
        }
        else console.printError("Ошибка при добавлении координаты Y: Поле не может быть null\n"+
                "Вы ввели: "+y);

    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
