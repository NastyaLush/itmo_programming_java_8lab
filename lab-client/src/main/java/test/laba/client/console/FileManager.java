package test.laba.client.console;
import test.laba.client.exception.CommandWithoutArguments;
import test.laba.client.mainClasses.Root;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;


public class FileManager  {
    protected final String envVarible = System.getenv("LABA");
    public boolean read(Root root, Console console)  {
        ParsingXML parsing = new ParsingXML(console);
        if (envVarible == null) {
            console.printError("XML file isn't found, please change file path"
                    + "in environment variable(LABA)!\n");
            return false;

        }
            if (!parsing.parsingToXML(root)) {
                return false;
            }
            console.print("Коллекция загружена!");
            return true;

    }


    public void save(Root root, Console console, SaveCollection saveCollection)  {
        try {
            FileWriter fileWriter = new FileWriter(envVarible);
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


