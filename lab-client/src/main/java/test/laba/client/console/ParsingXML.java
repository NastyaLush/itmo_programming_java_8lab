package test.laba.client.console;

import test.laba.client.mainClasses.Root;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ParsingXML extends ConsoleParsing {
    private final FileReader fileReader = new FileReader(getEnvVariable());

    public ParsingXML(Console console) throws FileNotFoundException {
        super(console);
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
