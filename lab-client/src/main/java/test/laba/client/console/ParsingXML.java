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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.ZonedDateTime;
import java.util.Objects;

public class ParsingXML extends ConsoleParsing {
    private boolean flag = true;
    private Console console;
    private FileReader fileReader= new FileReader(getEnvVariable());

    public ParsingXML(Console console) throws FileNotFoundException {

        this.console = console;
    }
    public Root createJavaObjects() throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(Root.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (Root) unmarshaller.unmarshal(fileReader);
    }
    public void closeFileReader() throws IOException {
        fileReader.close();
    }

}
