package test.laba.client.console;



import test.laba.client.exception.CreateError;
import test.laba.client.exception.IDError;
import test.laba.client.exception.VariableException;
import test.laba.client.exception.exucuteError;
import test.laba.client.mainClasses.*;

import java.time.ZonedDateTime;
import java.util.HashMap;

public  class ConsoleParsing extends VariableParsing {
    protected final String TAG_NAME = "name";
    protected final String TAG_X = "x";
    protected final String TAG_Y = "y";
    protected final String TAG_COORDINATES = "coordinates";
    protected final String TAG_BIRTHDAY = "birthday";
    protected final String TAG_OWNER = "owner";
    protected final String TAG_PRICE = "price";
    protected final String TAG_MANUFACTURE_COST = "manufactureCost";
    protected final String TAG_UNIT_OF_MEASURE = "unitOfMeasure";
    protected final String TAG_LOCATION = "location";
    protected final String TAG_HEIGHT = "height";
    protected final String TAG_PRODUCT = "product";

    protected  String envVariable = System.getenv("LABA");


    public void parsProductFromConsole(Root root, String key, Console console) throws exucuteError {
        String name ;
        Long price ;
        Coordinates coordinates;
        Integer manufactureCost;
        Person owner;
        Product product = null;
        UnitOfMeasure unitOfMeasure;
        while (true) {
            console.print("Введите название продукта: ");
            name = console.scanner();
            try {
                toRightName(name,console);
                break;
            }
            catch (VariableException e) {
            }
        }
        coordinates = parsCoordinatesFromConsole(console);
        while (true) {
            console.print("Введите цену продукта, price: ");
            try {
                price = toRightPrice(console.scanner(),console);
                break;
            } catch (VariableException e) {
            }
        }
        while (true) {
            console.print("Введите поле manufactureCost: ");
            try {
                manufactureCost= toRightNumber(console.scanner(),console);
                break;
            } catch (VariableException e) {
            }
        }
        while (true) {
            console.print("Введите unitOfMeasure, возможные варианты:PCS, MILLILITERS, GRAMS, UN_INIT");
            try {
                unitOfMeasure = toRightUnitOfMeasure(console.scanner(),console);
                break;
            }
            catch (VariableException e) {
            }
        }
        owner = parsPersonFromConsole(console);
        while (true) {
            try {
                product = new Product(key, name, coordinates, price, manufactureCost, unitOfMeasure, owner, root,console);
                break;
            } catch (CreateError e) {
                console.printError(e);
                parsProductFromConsole(root, key,console);
                break;
            } catch (IDError e) {
                console.printError("Данный ID существует, повторите попытку");
                console.print("Введите ID:");
                key = console.scanner();
            }
        }
        assert product != null;
        root.setProduct(product.getId(), product);

    }
    private  Location parsLocationFromConsole(Console console) throws exucuteError {
        Long x = null;
        Integer y = null;
        String name;
        while (true) {
            console.print("Введите координату X локации: ");
            try {
                x= toRightNumberLong(console.scanner(),console);
                break;
            } catch (VariableException e) {
            }
        }
        while (true) {
            console.print("Введите координату Y локации: ");
            try {
                y = toRightNumber(console.scanner(),console);
                break;
            } catch (VariableException e) {
                console.printError("Не правильный тип данных");
            }
        }
        while (true){
            console.print("Введите название локации: ");
            name = console.scanner();
            try {
                toRightName(name,console);
                break;
            }
            catch (VariableException e) {
            }
        }
        return new Location(x, y, name,console);
    }
    private  Person parsPersonFromConsole(Console console) throws exucuteError {
        String name ;
        String birthday;
        ZonedDateTime newBirthday ;
        Integer height ;
        Location location;
        Person person;
        while (true) {
            console.print("Введите имя владельца: ");
            name = console.scanner();

            try {
                toRightName(name,console);
                break;
            }
            catch (VariableException e){

            }
        }
        while (true) {
            console.print("Введите дату рождения владельца: ");
            birthday = console.scanner();
            try {
                newBirthday= toRightBirthday(birthday,console);
                break;
            }
            catch (VariableException ignored) {
            }
        }
        while (true) {
            console.print("Введите рост владельца: ");
            try {
                height = toRightHeight(console.scanner(),console);
                break;
            } catch (VariableException ignored) {
            }
        }
        location = parsLocationFromConsole(console);
        try {
            person = new Person(name, newBirthday, height, location,console);
            return person;
        } catch (CreateError e) {
            return parsPersonFromConsole(console);
        }
    }
    private  Coordinates parsCoordinatesFromConsole(Console console) {
        Coordinates coordinates;
        Integer x ;
        Float y ;
        while (true) {
            console.print("Введите координату х: ");
            try {
                x = toRightX(console.scanner(),console);
                break;
            } catch (VariableException ignored) {
            }
        }
        while (true) {
            console.print("Введите координату y: ");
            try {
                y = toRightY(console.scanner(),console);
                break;
            } catch (VariableException ignored) {
            }
        }
        try {
            coordinates = new Coordinates(x, y,console);
            return coordinates;
        } catch (CreateError e) {
            return parsCoordinatesFromConsole(console);
        }

    }


    public String save(HashMap<Long, Product> productHashMap) {
        StringBuilder s = new StringBuilder("<root>" + '\n' + '\t');

        for (Product product : productHashMap.values()) {
            s.append(saveProduct(product));
        }
        return s.toString() + '\n' + "</root>";
    }
    private String saveLocation(Location location) {
        String s ="<location>\n\t\t\t\t";
        if(location.getX()!=null) s+="<x>" + location.getX() + "</x>"+'\n';
        if (location.getY()!=null)s+= "\t\t\t\t" + "<y>" + location.getY() + "</y>" + '\n';
        s+="\t\t\t\t" + "<name>" + location.getName() + "</name>" + '\n' + "\t\t\t</location>";
        return s;
    }
    private String saveCoordinates(Coordinates coordinates) {
        return "<coordinates>\n" +
                "\t\t\t<x>" + coordinates.getX() + "</x>\n" +
                "\t\t\t<y>" + coordinates.getY() + "</y>\n" +
                "\t\t</coordinates>";
    }
    private String saveUnitOfMeasure(UnitOfMeasure unitOfMeasure) {

        return "<unitOfMeasure>" + unitOfMeasure + "</unitOfMeasure>";
    }
    private String savePerson(Person person) {
        String s;
        s="<owner>" + "\n\t" +
                "\t\t" + "<name>" + person.getName() + "</name>" + '\n' +
                "\t\t\t" + "<birthday>" + person.getBirthday() + "</birthday>" + '\n';
        if(person.getHeight()!=null) s+="\t\t\t" + "<height>" + person.getHeight() + "</height>" + '\n';
        s+= "\t\t\t" + saveLocation(person.getLocation()) + '\n' + '\t' + '\t' + "</owner>";
        return s;
    }
    private String saveProduct(Product product) {
        String s="<product>" + '\n' +
                "\t\t" + "<name>" + product.getName() + "</name>" + '\n' +
                "\t\t" + saveCoordinates(product.getCoordinates()) + '\n' +
                "\t\t" + "<price>" + product.getPrice() + "</price>" + '\n';
        if(product.getManufactureCost()!=null)s+="\t\t" + "<manufactureCost>" + product.getManufactureCost() + "</manufactureCost>" + '\n';
        if(product.getUnitOfMeasure()!=null)
        s+="\t\t" + saveUnitOfMeasure(product.getUnitOfMeasure()) + '\n';
                s+="\t\t" + savePerson(product.getOwner()) + '\n' + '\t' +
                "</product>";
        return s;

    }



}



