package test.laba.client.console;
import test.laba.client.exception.CommandWithoutArguments;
import test.laba.client.exception.VariableException;
import test.laba.client.mainClasses.Product;
import test.laba.client.mainClasses.Root;

import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;


public class FileManager implements Variable  {
    public Root read(Console console)  {
        ParsingXML parsing = null;
        try {
            parsing = new ParsingXML(console);
        } catch (FileNotFoundException e) {
            console.printError("Файл не найден, проверьте путь");
        }
        Root root;
        try {
           root = parsing.createJavaObjects();
           for (HashMap.Entry<Long, Product> entry : root.getProducts().entrySet()) {
               if (!entry.getValue().isRightProduct(root)) {
                   throw new VariableException("Ошибка во входных данных файла", console);
               }
           }
           return root;
        } catch (JAXBException | IOException e) {
            console.printError("Ошибка при парсинге");
        } catch (VariableException e) {
            console.printError("");
        } catch (NullPointerException e) {
            console.printError("ошибка при обработке файла, проверьте переменную окружения LABA, вы ввели: " + Variable.ENV_VARIBLE + " и входные данные");
        } finally {
            try {
                parsing.closeFileReader();
            } catch (IOException e) {
                console.printError("Невозможно закрыть файл!");
            }
        }

        return null;
    }


    public void save(Root root, SaveCollection saveCollection) throws IOException {
        try (FileWriter fileWriter = new FileWriter(Variable.ENV_VARIBLE)) {
            fileWriter.write(saveCollection.save(root.getProducts()));
        }
    }

    public void readScript(BufferedReader reader, Root root, CommandsManager commandsManager, FileManager fileManager, ConsoleParsing parsingInterface, ScriptConsole scriptConsole) throws CommandWithoutArguments, IOException {
            String[] command;
            int limit = 3;
            int numberOfCommand = 0;
            int numberOfArguments = 1;
            int numberOfBeginTrim = 2;
                while (true) {
                    command = (reader.readLine().trim() + " ").split(" ",limit);
                    for (int i = numberOfBeginTrim; i < command.length; i++) {
                        command[numberOfArguments] += " " + command[i];
                        command[i] = command[i].trim();
                    }
                    commandsManager.getHistory().addToHistory(command[numberOfCommand]);
                    scriptConsole.command(command, root, commandsManager, fileManager, scriptConsole, parsingInterface);
                }


        }

    }


