package test.laba.client.console;

import test.laba.client.mainClasses.Root;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ParsingXML extends ConsoleParsing {
    private FileReader fileReader;

    public ParsingXML(Console console) throws FileNotFoundException {
        super(console);
        fileReader = new FileReader(getEnvVariable());
    }

    public Root createJavaObjects() throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(Root.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (Root) unmarshaller.unmarshal(fileReader);
    }
    public void closeFileReader() throws IOException {
        if (fileReader != null) {
            fileReader.close();
        }
    }

}
