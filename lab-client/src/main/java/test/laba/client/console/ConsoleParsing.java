package test.laba.client.console;



import test.laba.client.exception.CreateError;
import test.laba.client.exception.VariableException;
import test.laba.client.mainClasses.Location;
import test.laba.client.mainClasses.UnitOfMeasure;
import test.laba.client.mainClasses.Coordinates;
import test.laba.client.mainClasses.Person;
import test.laba.client.mainClasses.Product;
import test.laba.client.mainClasses.Root;

import java.time.ZonedDateTime;


public  class ConsoleParsing extends VariableParsing implements Variable {
    private Console console;

    protected final String tagName = "name";
    protected final String tagX = "x";
    protected final String tagY = "y";
    protected final String tagCoordinates = "coordinates";
    protected final String tagBirthday = "birthday";
    protected final String tagOwner = "owner";
    protected final String tagPrice = "price";
    protected final String tagManufactureCost = "manufactureCost";
    protected final String tagUnitOfMeasure = "unitOfMeasure";
    protected final String tagLocation = "location";
    protected final String tagHeight = "height";
    protected final String tagProduct = "product";


    public ConsoleParsing(Console console){
        this.console=console;
    }
    public Product parsProductFromConsole(Root root, String key) {
        Long newKey;
        String name = parsProductName();
        Long price = parsProductPrise();
        Coordinates coordinates = parsCoordinatesFromConsole();
        Integer manufactureCost = parsManufactureCost(console);
        Person owner = parsPersonFromConsole(console);
        Product product = null;
        UnitOfMeasure unitOfMeasure = parsUnitOfMeasure(console);

        try {
            newKey = createKey(console, key);
            product = new Product(name, coordinates, price, manufactureCost, unitOfMeasure, owner, root);
        } catch (CreateError e) {
            console.printError(e);
            parsProductFromConsole(root,key, console);
        }

        return product;

    }
    private Location parsLocationFromConsole(Console console) {
        Long x = parsLocationX(console);
        Integer y = parsLocationY(console);
        String name = parsLocationName(console);

        return new Location(x, y, name, console);
    }
    private  Person parsPersonFromConsole(Console console) {
        String name = parsPersonName(console);
        ZonedDateTime newBirthday = parsPersonBirthday(console);
        Integer height = parsPersonHeight(console);
        Location location = parsLocationFromConsole(console);
        Person person;
        try {
            person = new Person(name, newBirthday, height, location, console);
            return person;
        } catch (CreateError e) {
            return parsPersonFromConsole(console);
        }
    }
    private  Coordinates parsCoordinatesFromConsole(Console console) {
        Coordinates coordinates;
        Integer x = parsCoordinatesX(console);
        Float y = parsCoordinatesY(console);

        return new Coordinates(x, y, console);


    }

    private String parsProductName(Console console) {
        String name = null;
        boolean flag = true;
        while (flag) {
            console.print("Введите название продукта: ");
            name = console.scanner();
            try {
                toRightName(name, console);
                flag = false;
            } catch (VariableException ignored) {
                console.printError("Не правильный тип данных");
            }
        }
        return  name;

    }
    private Long parsProductPrise(Console console) {
        boolean flag = true;
        Long price = null;
        while (flag) {
            console.print("Введите цену продукта, price: ");
            try {
                price = toRightPrice(console.scanner(), console);
                flag = false;
            } catch (VariableException ignored) {
                console.printError("Не правильный тип данных");
            }
        }
        return price;
    }
    private Integer parsManufactureCost(Console console) {
        Integer manufactureCost = null;
        boolean flag = true;
        while (flag) {
            console.print("Введите поле manufactureCost: ");
            try {
                manufactureCost = toRightNumberInt(console.scanner(), console);
                flag = false;
            } catch (VariableException ignored) {
                console.printError("Не правильный тип данных");
            }
        }
        return manufactureCost;
    }
    private UnitOfMeasure parsUnitOfMeasure(Console console) {
        UnitOfMeasure unitOfMeasure = null;
        boolean flag = true;
        while (flag) {
            console.print("Введите unitOfMeasure, возможные варианты:PCS, MILLILITERS, GRAMS");
            try {
                unitOfMeasure = toRightUnitOfMeasure(console.scanner());
                flag = false;
            } catch (VariableException | IllegalArgumentException e) {
                console.printError("Не правильный тип данных");
            }
        }
        return unitOfMeasure;
    }

    private Long parsLocationX(Console console) {
        Long x = null;
        boolean flag = true;
        while (flag) {
            console.print("Введите координату X локации: ");
            try {
                x = toRightNumberLong(console.scanner(), console);
                flag = false;
            } catch (VariableException ignored) {
                console.printError("Не правильный тип данных");
            }
        }
        return x;
    }
    private Integer parsLocationY(Console console) {
        Integer y = null;
        boolean flag = true;
        while (flag) {
            console.print("Введите координату Y локации: ");
            try {
                y = toRightNumberInt(console.scanner(), console);
                flag = false;
            } catch (VariableException e) {
                console.printError("Не правильный тип данных");
            }
        }
        return y;
    }
    private String parsLocationName(Console console) {
        String name = null;
        boolean flag = true;
            while (flag) {
                console.print("Введите название локации: ");
                name = console.scanner();
                try {
                    toRightName(name, console);
                    flag = false;
                } catch (VariableException e) {
                    flag = true;
                }
            }
        return name;
    }

    private Integer parsCoordinatesX(Console console) {
        Integer x = null;
        boolean flag = true;
        while (flag) {
            console.print("Введите координату х: ");
            try {
                x = toRightX(console.scanner(), console);
                flag = false;
            } catch (VariableException ignored) {
                flag = true;
            }
        }
        return x;
    }
    private Float parsCoordinatesY(Console console) {
        Float y = null;
        boolean flag  = true;
        while (flag) {
            console.print("Введите координату y: ");
            try {
                y = toRightY(console.scanner(), console);
                flag = false;
            } catch (VariableException ignored) {
                console.printError("Не правильный тип данных");
            }
        }
        return y;
    }

    private String parsPersonName(Console console) {
        String name = null;
        boolean flag = true;
        while (flag) {
            console.print("Введите имя владельца: ");
            name = console.scanner();
            try {
                toRightName(name, console);
                flag = false;
            } catch (VariableException ignored) {
                console.printError("Не правильный тип данных");
            }
        }
        return name;
    }
    private ZonedDateTime parsPersonBirthday(Console console) {
        String birthday;
        ZonedDateTime newBirthday = null;
        boolean flag = true;
        while (flag) {
            console.print("Введите дату рождения владельца: ");
            birthday = console.scanner();
            try {
                newBirthday = toRightBirthday(birthday, console);
                flag = false;
            } catch (VariableException ignored) {
                console.printError("Не правильный тип данных");
            }
        }
        return newBirthday;
    }
    private Integer parsPersonHeight(Console console) {
        Integer height = null;
        boolean flag = true;
        while (flag) {
            console.print("Введите рост владельца: ");
            try {
                height = toRightHeight(console.scanner(), console);
                flag = false;
            } catch (VariableException ignored) {
                console.printError("Не правильный тип данных");
            }
        }
        return height;
    }
    private Long createKey(Console console, String id) {
        String newID = id;
        Long cid = null;
        boolean flag = true;
        while (flag) {
            try {
                cid = Long.valueOf(newID);
                flag = false;
            } catch (NumberFormatException e) {
                console.printError("Неправильный формат ввода key, повторите попытку" + e);
                console.print("Введите key:");
                newID = console.scanner();
            }
        }
        return cid;
    }

    public String getEnvVariable() {
        return Variable.envVarible;
    }
}



