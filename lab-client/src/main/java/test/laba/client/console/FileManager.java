package test.laba.client.console;

import test.laba.client.commands.ExecuteScript;
import test.laba.client.exception.CommandWithoutArguments;
import test.laba.client.mainClasses.Root;

import java.io.*;


import java.io.IOException;


public class FileManager  {
    protected String envVarible = System.getenv("LABA");
    public boolean read(Root root, Console console)  {
        ParsingXML parsing = new ParsingXML();
        if (envVarible == null) {
            console.printError("XML file isn't found, please change file path" +
                    "in environment variable(LABA)!\n");
            return false;

        }
            if(!parsing.parsingToXML(root,console)){
                return false;
            }
            console.print("Коллекция загружена!");
            return true;

    }


    public void save(Root root,Console console, ConsoleParsing consoleParsing)  {
        try {
            FileWriter fileWriter = new FileWriter(envVarible);
            fileWriter.write(consoleParsing.save(root.getProducts()));
            System.out.println(fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            console.printError("ошибка при попытке записи: " + e);
        }
    }

    public void readScript(String fileName, Root root, CommandsManager commandsManager, FileManager fileManager, Console console, ConsoleParsing parsingInterface, ExecuteScript executeScript) throws CommandWithoutArguments {
        try {
            String[] command;
                File file = new File(fileName);
                FileReader fr = new FileReader(file);
                BufferedReader reader = new BufferedReader(fr);
                ScriptConsole scriptConsole=new ScriptConsole(reader,console);
                while (true) {
                    command = (reader.readLine().trim() + " ").split(" ", 3);
                    command[1] = command[1].trim();
                    commandsManager.getHistory().addToHistory(command[0]);
                    console.command(command, root, commandsManager, fileManager,scriptConsole,parsingInterface);
                    System.out.println("команда обработана");
                }

            } catch (FileNotFoundException e) {
                console.printError("Файл не найден, проверьте путь");
            } catch (IOException e) {
                console.printError("Ошибка при чтении файла");
            }
              catch (NullPointerException e) {
            console.printError("Файл не найден, проверьте путь к нему");
        }
              catch (Exception e) {
            console.printError("ошибка при обработки команд");
        }

        }

    }


