package test.laba.client.console;


import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import test.laba.client.exception.CreateError;
import test.laba.client.exception.VariableException;
import test.laba.client.mainClasses.*;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.time.ZonedDateTime;
import java.util.Objects;

public class ParsingXML extends ConsoleParsing {
    private boolean flag=true;


    public boolean parsingToXML(Root root, Console console) {
        Document doc = null;
        try {
            doc = buildDocument(console);
        } catch (Exception e) {
            console.printError("Parsing mistake: " + e);
            return false;
        }
        if(flag==false){
            return false;
        }

        Node rootNode = doc.getFirstChild();
        NodeList rootChildren = rootNode.getChildNodes();

        for (int i = 0; i < rootChildren.getLength(); i++) {
            if (rootChildren.item(i).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }

            if (TAG_PRODUCT.equals(rootChildren.item(i).getNodeName())) {
                Product product = parsProduct(rootChildren.item(i), root,console);
                if(flag!=false) {
                    root.setProduct(product.getId(), product);
                }
                else {
                    return false;
                }
            }
        }
        return true;
    }

    private Product parsProduct(Node rootNode, Root root,Console console) {
        NodeList rootChildren = rootNode.getChildNodes();
        //переменные
        Person person = null;
        Product product = null;
        ZonedDateTime birth = null;

        Node coordinates;
        Node owner = null;
        Node location;


        String ownerName = null;
        String birthday;
        Integer height = null;


        String name = null;
        Integer manufactureCost = null;
        Long price = null;
        UnitOfMeasure unitOfMeasure = UnitOfMeasure.UN_INIT;

        Coordinates coordinates1 = null;
        Location location1 = null;

        // parsing product


        for (int i = 0; i < rootChildren.getLength(); i++) {
            if (rootChildren.item(i).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            switch (rootChildren.item(i).getNodeName()) {
                case TAG_NAME -> {
                    name = rootChildren.item(i).getTextContent();
                    try {
                       toRightName(name,console);
                    } catch (VariableException e) {
                        console.printError("Ошибка при обработки строки: ");
                        flag=false;
                        return null;
                    }
                }
                case TAG_COORDINATES -> {
                    coordinates = rootChildren.item(i);
                    coordinates1 = parsCoordinates(coordinates,console);
                    if(flag==false) {
                        return null;
                    }
                }
                case TAG_OWNER -> {
                    owner = rootChildren.item(i);
                }
                case TAG_PRICE -> {
                    try {
                        price = toRightPrice(rootChildren.item(i).getTextContent(),console);
                    } catch (VariableException e) {
                        console.printError("Строка: " );
                        flag=false;
                        return  null;
                    }
                }
                case TAG_MANUFACTURE_COST -> {
                    try {
                        manufactureCost = toRightNumber(rootChildren.item(i).getTextContent(),console);
                    } catch (VariableException e) {
                        console.printError("Строка: " );
                        flag=false;
                        return  null;
                    }
                }
                case TAG_UNIT_OF_MEASURE -> {
                    try {
                        unitOfMeasure = toRightUnitOfMeasure(rootChildren.item(i).getTextContent(),console);
                    } catch (VariableException e) {
                        console.printError("Строка: ");
                        flag=false;
                        return null;
                    }
                }
            }
        }

            // parsing person

            NodeList ownerChildList = null;
            try {
                ownerChildList = Objects.requireNonNull(owner).getChildNodes();
            } catch (Exception e) {
                console.printError("owner не может быть пустым,в Product на " + " строке");
                flag=false;
                return null;
            }


            for (int j = 0; j < ownerChildList.getLength(); j++) {
                if (ownerChildList.item(j).getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }
                switch (ownerChildList.item(j).getNodeName()) {
                    case TAG_NAME -> {
                        ownerName = ownerChildList.item(j).getTextContent();
                        try {
                            toRightName(ownerName,console);
                        } catch (VariableException e) {
                            flag=false;
                            return null;
                        }
                    }
                    case TAG_BIRTHDAY -> {
                        birthday = ownerChildList.item(j).getTextContent();
                        try {
                            birth = toRightBirthday(birthday,console);
                        } catch (VariableException e) {
                            flag=false;
                            return null;
                        }
                    }
                    case TAG_HEIGHT -> {
                        try {
                            height = toRightHeight(ownerChildList.item(j).getTextContent(),console);
                        } catch (VariableException e) {
                            flag=false;
                            return null;
                        }

                    }
                    case TAG_LOCATION -> {
                        location = ownerChildList.item(j);
                        location1 = parsLocation(location,console);
                        if(flag==false) {
                            return null;
                        }
                    }
                }

            }

            try {
                person = new Person(ownerName, birth, height, location1,console);

            } catch (CreateError e) {
                console.printError(e);
                flag=false;
                return null;
            }
            try {
                product = new Product(name, coordinates1, price, manufactureCost, unitOfMeasure, person, root,console);
            } catch (CreateError e) {
                console.printError(e);
                flag=false;
                return null;
            }
            return product;
        }
    public Location parsLocation(Node location,Console console)  {
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
                case TAG_NAME -> {
                    locationName = locationChildList.item(j).getTextContent();
                    try {
                        toRightName(locationName,console);
                    } catch (VariableException e) {
                        flag=false;
                        return null;
                    }
                }
                case TAG_Y -> {
                    try {
                        y1 = toRightNumber(locationChildList.item(j).getTextContent(),console);
                    } catch (VariableException e) {
                        flag=false;
                        return null;
                    }
                }
                case TAG_X -> {
                    try {
                        x1 = toRightNumberLong(locationChildList.item(j).getTextContent(),console);
                    } catch (VariableException e) {
                        flag=false;
                        return null;
                    }
                }
            }
        }
        location1 = new Location(x1, y1, locationName,console);
        return location1;
    }
    public Coordinates parsCoordinates(Node coordinates,Console console) {
        Coordinates coordinates1 = null;
        NodeList coordinatesChildList = coordinates.getChildNodes();
        Integer x = null;
        Float y = null;
        for (int j = 0; j < coordinatesChildList.getLength(); j++) {
            if (coordinatesChildList.item(j).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            switch (coordinatesChildList.item(j).getNodeName()) {
                case TAG_X -> {
                    try {
                        x = toRightX(coordinatesChildList.item(j).getTextContent(),console);
                    } catch (VariableException e) {
                        flag=false;
                        return null;
                    }

                }
                case TAG_Y -> {
                    try {
                        y = toRightY(coordinatesChildList.item(j).getTextContent(),console);
                    } catch (VariableException e) {
                        flag=false;
                        return null;
                    }

                }
            }
        }
        try {
            coordinates1 = new Coordinates(x, y,console);
        } catch (CreateError e) {
            System.exit(1);
        }
        return coordinates1;
    }

    public Document buildDocument(Console console) {
        try {
            File file = new File(envVariable);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            return factory.newDocumentBuilder().parse(file);
        } catch (Exception e) {
            console.printError("Ошибка при построении документа: " + e);
            flag=false;
            return null;
        }

    }


}

