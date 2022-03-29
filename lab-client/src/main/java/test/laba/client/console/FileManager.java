package test.laba.client.console;
import test.laba.client.exception.CommandWithoutArguments;
import test.laba.client.exception.VariableException;
import test.laba.client.dataClasses.Product;
import test.laba.client.dataClasses.Root;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;


public class FileManager implements Variable  {
    public Root read(Console console)  {
        try {
            ParsingXML parsingXML = new ParsingXML(console);
            Root root;
            try {
                root = parsingXML.createJavaObjects();
                for (HashMap.Entry<Long, Product> entry : root.getProducts().entrySet()) {
                    if (!entry.getValue().isRightProduct(root)) {
                        throw new VariableException("Ошибка во входных данных файла", console);
                    }
                }
                return root;
            } catch (JAXBException | IOException e) {
                console.printError("Ошибка при попытке парсинга" + e);
            } catch (VariableException e) {
                console.printError("");
            } finally {
                close(parsingXML, console);
            }
        } catch (FileNotFoundException e) {
            console.printError("Файл не найден, проверьте путь и переменную окружения LABA");
        }

        return null;
    }
    private void close(ParsingXML parsingXML, Console console) {
        try {
            parsingXML.closeFileReader();
        } catch (IOException e) {
            console.printError("Невозможно закрыть файл!");
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
    public void readScript(BufferedReader reader, Root root, CommandsManager commandsManager, FileManager fileManager, ConsoleParsing consoleParsing, ScriptConsole scriptConsole) throws CommandWithoutArguments, IOException {
        String[] command;
        final int limitOfWords = 3;
        int numberOfCommand = 0;
        int numberOfArguments = 1;
        int numberOfBeginTrim = 2;
        while (reader.ready()) {
            command = (reader.readLine().trim() + " ").split(" ", limitOfWords);
            for (int i = numberOfBeginTrim; i < command.length; i++) {
                command[numberOfArguments] += " " + command[i];
                command[i] = command[i].trim();
            }
            commandsManager.getHistory().addToHistory(command[numberOfCommand]);
           if (!scriptConsole.command(command, root, commandsManager, fileManager, scriptConsole, consoleParsing)) {
               reader.close();
               break;
           }
        }
        reader.close();
        }

    }


