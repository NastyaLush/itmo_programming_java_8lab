/*
package test.laba.server.workwithfile;

import test.laba.server.mycommands.UsersHandler;
import test.laba.server.mycommands.commands.Variable;
import test.laba.common.exception.ParsException;
import test.laba.common.exception.VariableException;
import test.laba.server.mycommands.Root;
import test.laba.common.dataClasses.Product;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;


public class FileManager implements Variable {
    public Root read() throws VariableException, JAXBException, IOException {
        ParsingXML parsingXML = new ParsingXML();
        try {
            Root root = (Root) parsingXML.createJavaObjects();
                for (HashMap.Entry<Long, Product> entry : root.getProducts().entrySet()) {
                    if (!entry.getValue().isRightProduct()) {
                        throw new VariableException("Ошибка во входных данных файла");
                    }
                }
                return root;
            }  finally {
                close(parsingXML);
            }
    }
    public UsersHandler readUsers() throws VariableException, JAXBException, IOException {
        ParsingXML parsingXML = new ParsingXML();
        try {
            UsersHandler usersHandler = (UsersHandler) parsingXML.createUsers();
            System.out.println(usersHandler);
            return usersHandler;
        }  finally {
            close(parsingXML);
        }
    }
    private void close(ParsingXML parsingXML) throws IOException {
       parsingXML.closeFileReader();

    }
    public void save(Root root) throws IOException, ParsException {
        try (FileWriter fileWriter = new FileWriter(Variable.ENV_VARIABLE)) {
            fileWriter.write(saveCollection(root));
        }
    }
    public void saveUsers(UsersHandler usersHandler) throws ParsException, IOException {
        System.out.println(usersHandler);
        try (FileWriter fileWriter = new FileWriter(Variable.USERS)) {
            System.out.println(saveCollection(usersHandler));
            fileWriter.write(saveCollection(usersHandler));
        }
    }
    public String saveCollection(UsersHandler usersHandler) throws ParsException {
        StringWriter writer = new StringWriter();
        try {
            JAXBContext context = JAXBContext.newInstance(UsersHandler.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(usersHandler, writer);

        } catch (JAXBException e) {
            e.printStackTrace();
            throw new ParsException("Can not safe");
        }
        return writer.toString();
    }
    public String saveCollection(Root root) throws ParsException {
        StringWriter writer = new StringWriter();
        try {
            JAXBContext context = JAXBContext.newInstance(Root.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(root, writer);

        } catch (JAXBException e) {
            throw new ParsException("Can not safe");
        }
        return writer.toString();
    }

    }


*/
