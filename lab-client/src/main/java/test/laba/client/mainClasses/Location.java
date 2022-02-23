package test.laba.client.mainClasses;


import test.laba.client.console.Console;

public class Location {
    private Long x;
    private Integer y;
    private String name; //Строка не может быть пустой, Поле не может быть null
    public Console console;

    public Location(Long x, Integer y, String s,Console console) {
        if (x!= null )this.x=x;
        if(y!=null) this.y=y;
        if(s==null || s.isEmpty() ){
            console.printError("Строка не может быть пустой, Поле не может быть null\n"+
                    "ваше значение: "+s);
        }
        name=s;
        this.console=console;
    }

    public Long getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public void setX(Long x) {
        this.x = x;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                ", name='" + name + '\'' +
                '}';
    }
}
