package test.laba.client.console;


import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import test.laba.client.exception.CreateError;
import test.laba.client.exception.VariableException;
import test.laba.client.mainClasses.Location;
import test.laba.client.mainClasses.UnitOfMeasure;
import test.laba.client.mainClasses.Coordinates;
import test.laba.client.mainClasses.Person;
import test.laba.client.mainClasses.Product;
import test.laba.client.mainClasses.Root;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Objects;

public class ParsingXML extends ConsoleParsing {
    private boolean flag = true;
    private Console console;

    public ParsingXML(Console console) {
        this.console = console;
    }

// parsing arfter save
    public boolean parsingToXML(Root root) {
        Document doc;
        try {
            doc = buildDocument();
        } catch (Exception e) {
            console.printError("Parsing mistake: " + e);
            return false;
        }
        if (!flag) {
            return false;
        }

        Node rootNode = doc.getFirstChild();
        NodeList rootChildren = rootNode.getChildNodes();

        for (int i = 0; i < rootChildren.getLength(); i++) {
            if (rootChildren.item(i).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }

            if (tagProduct.equals(rootChildren.item(i).getNodeName())) {
                Product product = parsProduct(rootChildren.item(i), root);
                if (flag) {
                    root.setProduct(product.getId(), product);
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    private String parsName(NodeList rootChildren, int i) {
        String name = rootChildren.item(i).getTextContent();
        try {
            toRightName(name, console);
        } catch (VariableException e) {
            console.printError("Ошибка при обработки строки: ");
            flag = false;
            return null;
        }
        return name;
    }
    private Long productPrise(NodeList rootChildren, int i) {
        Long price;
        try {
            price = toRightPrice(rootChildren.item(i).getTextContent(), console);
        } catch (VariableException e) {
            console.printError("Строка: ");
            flag = false;
            return  null;
        }
        return price;
    }
    private Integer productManufactureCost(NodeList rootChildren, int i) {
        Integer manufactureCost;
        try {
            manufactureCost = toRightNumber(rootChildren.item(i).getTextContent(), console);
        } catch (VariableException e) {
            console.printError("Строка: ");
            flag = false;
            return  null;
        }
        return manufactureCost;
    }
    private UnitOfMeasure productUnitOfMeasure(NodeList rootChildren, int i) {
        UnitOfMeasure unitOfMeasure;
        try {
            unitOfMeasure = toRightUnitOfMeasure(rootChildren.item(i).getTextContent(), console);
        } catch (VariableException e) {
            console.printError("Строка: ");
            flag = false;
            return null;
        }
        return unitOfMeasure;
    }
    private Coordinates productCoordinates(NodeList rootChildren, int i) {
        Node coordinates;
        Coordinates coordinates1;
        coordinates = rootChildren.item(i);
        coordinates1 = parsCoordinates(coordinates);
        if (!flag) {
            return null;
        }
        return coordinates1;
    }
    private Product product(String name, Coordinates coordinates1, Long price, Integer manufactureCost, UnitOfMeasure unitOfMeasure, Person person, Root root) {
        Product product;
        try {
            product = new Product(name, coordinates1, price, manufactureCost, unitOfMeasure, person, root);

        } catch (CreateError e) {
            console.printError(e);
            flag = false;
            return null;
        }
        return product;
    }
    private void  defaultMethod() {
        console.printError("Ошибка, Данный тег не найден!");
        flag = false;
    }
    private Product parsProduct(Node rootNode, Root root) {
        NodeList rootChildren = rootNode.getChildNodes();
        Person person;
        Node owner = null;
        String name = null;
        Integer manufactureCost = null;
        Long price = null;
        UnitOfMeasure unitOfMeasure = UnitOfMeasure.UN_INIT;
        Coordinates coordinates1 = null;
        for (int i = 0; i < rootChildren.getLength(); i++) {
            if (rootChildren.item(i).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
                switch (rootChildren.item(i).getNodeName()) {
                    case tagName:
                        name = parsName(rootChildren, i);
                        break;
                    case tagCoordinates:
                        coordinates1 = productCoordinates(rootChildren, i);
                        break;
                    case tagOwner:
                        owner = rootChildren.item(i);
                        break;
                    case tagPrice:
                        price = productPrise(rootChildren, i);
                        break;
                    case tagManufactureCost:
                        manufactureCost = productManufactureCost(rootChildren, i);
                        break;
                    case tagUnitOfMeasure:
                        unitOfMeasure = productUnitOfMeasure(rootChildren, i);
                        break;
                    default:
                        defaultMethod();
                        break;
                }
        }
            person = parsPerson(owner);
            return product(name, coordinates1, price, manufactureCost, unitOfMeasure, person, root);
        }


    private Person parsPerson(Node owner) {
        NodeList ownerChildList;
        String ownerName = null;
        Integer height = null;
        ZonedDateTime birth = null;
        Location location1 = null;
        try {
            ownerChildList = Objects.requireNonNull(owner).getChildNodes();
        } catch (Exception e) {
            console.printError("owner не может быть пустым,в Product на " + " строке");
            flag = false;
            return null;
        }
        for (int j = 0; j < ownerChildList.getLength(); j++) {
            if (ownerChildList.item(j).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            switch (ownerChildList.item(j).getNodeName()) {
                case tagName:
                    ownerName = parsName(ownerChildList, j);
                    break;

                case tagBirthday:
                    birth = parsBirth(ownerChildList, j);
                    break;
                case tagHeight:
                    height = parsHeight(ownerChildList, j);
                    break;
                case tagLocation:
                    location1 = parsLocation(ownerChildList, j);
                    break;
                default:
                    console.printError("Данный тег не найден!");
                    flag = false;
                    return null;
            }
        }
        return parsPerson(ownerName, birth, height, location1);
    }
    private ZonedDateTime parsBirth(NodeList ownerChildList, int j) {
        ZonedDateTime birth;
        String birthday = ownerChildList.item(j).getTextContent();
        try {
            birth = toRightBirthday(birthday, console);
        } catch (VariableException e) {
            flag = false;
            return null;
        }
        return birth;
    }
    private Integer parsHeight(NodeList ownerChildList, int j) {
        Integer height;
        try {
            height = toRightHeight(ownerChildList.item(j).getTextContent(), console);
        } catch (VariableException e) {
            flag = false;
            return null;
        }
        return height;
    }
    private Location parsLocation(NodeList ownerChildList, int j) {
        Node location;
        Location location1;
        location = ownerChildList.item(j);
        location1 = parsLocation(location);
        if (!flag) {
            return null;
        }
        return location1;
    }
    private Person parsPerson(String ownerName, ZonedDateTime birth, Integer height, Location location1) {
        Person person;
        try {
            person = new Person(ownerName, birth, height, location1, console);

        } catch (CreateError e) {
            console.printError(e);
            flag = false;
            return null;
        }
        return person;
    }

    public Location parsLocation(Node location)  {
        String locationName = null;
        Location location1;
        Long x1 = null;
        Integer y1 = null;
        NodeList locationChildList = location.getChildNodes();
        for (int j = 0; j < locationChildList.getLength(); j++) {
            if (locationChildList.item(j).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            switch (locationChildList.item(j).getNodeName()) {
                case tagName:
                    locationName = parsName(locationChildList, j);
                    break;

                case tagY:
                    y1 = parsY(locationChildList, j);
                    break;

                case tagX:
                    x1 = parsX(locationChildList, j);
                    break;
                default:
                    console.printError("Тег не найден, проверьте файл!!");
                    flag = false;
                    return null;
            }
        }
        location1 = new Location(x1, y1, locationName, console);
        return location1;
    }
    private Long parsX(NodeList locationChildList, int j) {
        Long x1;
        try {
            x1 = toRightNumberLong(locationChildList.item(j).getTextContent(), console);
        } catch (VariableException e) {
            flag = false;
            return null;
        }
        return x1;
    }
    private Integer parsY(NodeList locationChildList, int j) {
        Integer y1;
        try {
            y1 = toRightNumber(locationChildList.item(j).getTextContent(), console);
        } catch (VariableException e) {
            flag = false;
            return null;
        }
        return y1;
    }

    public Coordinates parsCoordinates(Node coordinates) {
       // Coordinates coordinates1 = null;
        NodeList coordinatesChildList = coordinates.getChildNodes();
        Integer x = null;
        Float y = null;
        for (int j = 0; j < coordinatesChildList.getLength(); j++) {
            if (coordinatesChildList.item(j).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            switch (coordinatesChildList.item(j).getNodeName()) {
                case tagX:
                    try {
                        x = toRightX(coordinatesChildList.item(j).getTextContent(), console);
                    } catch (VariableException e) {
                        flag = false;
                        return null;
                    }
                    break;


                case tagY:
                    try {
                        y = toRightY(coordinatesChildList.item(j).getTextContent(), console);
                    } catch (VariableException e) {
                        flag = false;
                        return null;
                }
                    break;
                default:
                    console.printError("Тег не найден, проверьте файл!!");
                    flag = false;
                    return null;
            }
        }
        return new Coordinates(x, y, console);
    }

    public Document buildDocument() {
        try {
            File file = new File(getEnvVariable());
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                return factory.newDocumentBuilder().parse(file);
        } catch (SAXException | IOException | ParserConfigurationException e) {
            console.printError("Ошибка при построении документа: " + e);
            flag = false;
            return null;
        }

    }


}
