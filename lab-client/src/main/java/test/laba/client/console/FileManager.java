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
    public Root read( Console console)  {
        ParsingXML parsing = null;
        try {
            parsing = new ParsingXML(console);
        } catch (FileNotFoundException e) {
            console.printError("Файл не найден, проверьте путь");
        }
        Root root;
        try {
           root= parsing.createJavaObjects();
           for (HashMap.Entry<Long, Product> entry : root.getProducts().entrySet()){
               if(!entry.getValue().isRightProduct(root)){
                   throw new VariableException("Ошибка во входных данных файла",console);
               }
           }
           return root;
        } catch (JAXBException| IOException e) {
            console.printError("Ошибка при парсинге");
        }
        catch (VariableException e){
            console.printError("");
        }
        catch (NullPointerException e){
            console.printError("ошибка при обработке файла, проверьте переменную окружения LABA, вы ввели: "+Variable.envVarible +" и входные данные");
        }
        finally {
            try {
                parsing.closeFileReader();
            } catch (IOException e) {
                console.printError("Невозможно закрыть файл!");
            }
        }

        return null;
    }


    public void save(Root root, Console console, SaveCollection saveCollection)  {
        try {
            FileWriter fileWriter = new FileWriter(Variable.envVarible);
            fileWriter.write(saveCollection.save(root.getProducts()));
            fileWriter.close();
        } catch (IOException e) {
            console.printError("ошибка при попытке записи: " + e);
        }
    }

    public void readScript(BufferedReader reader, Root root, CommandsManager commandsManager, FileManager fileManager, Console console, ConsoleParsing parsingInterface, ScriptConsole scriptConsole) throws CommandWithoutArguments {
            String[] command;

                while (true) {
                    try {
                        command = (reader.readLine().trim() + " ").split(" ");
                        command[1] = command[1].trim();
                        commandsManager.getHistory().addToHistory(command[0]);
                        console.command(command, root, commandsManager, fileManager, scriptConsole, parsingInterface);
                    } catch (IOException e) {
                        console.printError("ошибка при выполнении скрипта");
                        break;
                    }

                }

        }

    }


