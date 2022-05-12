/*
package test.laba.server.workwithfile;

import test.laba.server.mycommands.Root;
import test.laba.server.mycommands.UsersHandler;
import test.laba.server.mycommands.commands.Variable;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

*/
/**
 * the class is responsible for parsing xml file to java object
 *//*

public class ParsingXML  {
    private final FileReader fileReader;

    */
/**
     * the constructor accept console for print and create file reader
     * @throws FileNotFoundException throws if file not found
     *//*

    public ParsingXML() throws FileNotFoundException {
        if (getEnvVariable() != null) {
            fileReader = new FileReader(getEnvVariable());
        } else {
            throw new FileNotFoundException();
        }
    }

    */
/**
     * create java objects from xml file
     * @return object contained collection
     * @throws JAXBException throws if parsing impossible
     *//*

    public Object createJavaObjects() throws JAXBException {
        File file = new File(getEnvVariable());
        if (file.length() != 0) {
            JAXBContext context = JAXBContext.newInstance(Root.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return unmarshaller.unmarshal(fileReader);
        }
        return null;
    }
    public Object createUsers() throws JAXBException, FileNotFoundException {
        File file = new File(Variable.USERS);
        if (file.length() != 0) {
            JAXBContext context = JAXBContext.newInstance(UsersHandler.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return unmarshaller.unmarshal(new FileReader(file));
        }
        return null;
    }


    */
/**
     * close file reader
     * @throws IOException throws if closing impossible
     *//*

    public void closeFileReader() throws IOException {
        if (fileReader != null) {
            fileReader.close();
        }
    }
    private String getEnvVariable() {
        return Variable.ENV_VARIABLE;
    }

}
*/
