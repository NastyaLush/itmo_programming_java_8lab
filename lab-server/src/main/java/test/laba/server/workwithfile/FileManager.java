package test.laba.server.workwithfile;

import test.laba.common.IO.Console;
import test.laba.common.commands.Variable;
import test.laba.common.exception.VariableException;
import test.laba.common.commands.Root;
import test.laba.common.dataClasses.Product;
import test.laba.server.workwithfile.ParsingXML;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;


public class FileManager implements Variable {
    public Root read() throws VariableException {
        try {
            ParsingXML parsingXML = new ParsingXML();
            Root root;
            try {
                root = parsingXML.createJavaObjects();
                for (HashMap.Entry<Long, Product> entry : root.getProducts().entrySet()) {
                    if (!entry.getValue().isRightProduct(root)) {
                        throw new VariableException("Ошибка во входных данных файла");
                    }
                }
                return root;
            } catch (JAXBException | IOException e) {
               // console.printError("Ошибка при попытке парсинга");
                // TODO: create object with exception and answer
            } finally {
                close(parsingXML);
            }
        } catch (FileNotFoundException e) {
            //console.printError("Файл не найден, проверьте путь и переменную окружения LABA");
            //TODO: create object with exception and answer
        }

        return null;
    }
    private void close(ParsingXML parsingXML) {
        try {
            parsingXML.closeFileReader();
        } catch (IOException e) {
            // TODO: create object with exception and answer
           // console.printError("Невозможно закрыть файл!");
        }
    }
    public void save(Root root) throws IOException {
        try (FileWriter fileWriter = new FileWriter(Variable.ENV_VARIBLE)) {
            fileWriter.write(saveCollection(root));
        }
    }
    public String saveCollection(Root root) {
        //писать результат сериализации будем в Writer(StringWriter)
        StringWriter writer = new StringWriter();
        try {
            //создание объекта Marshaller, который выполняет сериализацию
            JAXBContext context = JAXBContext.newInstance(Root.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            // сама сериализация
            marshaller.marshal(root, writer);

        } catch (PropertyException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        //преобразовываем в строку все записанное в StringWriter
        String result = writer.toString();
        return result;
    }

    }


