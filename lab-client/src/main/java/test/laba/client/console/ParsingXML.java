package test.laba.client.console;

import test.laba.client.dataClasses.Root;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * the class is responsible for parsing xml file to java object
 */
public class ParsingXML extends ConsoleParsing {
    private final FileReader fileReader;

    /**
     * the constructor accept console for print and create filereader
     * @param console console for printing
     * @throws FileNotFoundException throws if file not found
     */
    public ParsingXML(Console console) throws FileNotFoundException {
        super(console);
        if(getEnvVarible() != null) {
            fileReader = new FileReader(getEnvVariable());
        } else {
            throw new FileNotFoundException();
        }
    }

    /**
     * create java objects from xml file
     * @return object contained collection
     * @throws JAXBException throws if parsing impossible
     * @throws IOException throws if parsing impossible
     */
    public Root createJavaObjects() throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(Root.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (Root) unmarshaller.unmarshal(fileReader);
    }

    /**
     * close file reader
     * @throws IOException throws if closing impossible
     */
    public void closeFileReader() throws IOException {
        if (fileReader != null) {
            fileReader.close();
        }
    }

}
