package test.laba.client.mainClasses;


import test.laba.client.console.*;
import test.laba.client.exception.CommandWithoutArguments;

public class Main {

    public static void main(String[] args) throws CommandWithoutArguments{
    Root root= new Root();
    Console console=new Console();
        ScriptConsole scriptConsole= new ScriptConsole();
        ConsoleParsing parsingInterface=new ConsoleParsing();
        CommandsManager commandsManager=new CommandsManager();

        FileManager fileManager= new FileManager();
        if (fileManager.read(root,console)) {
            console.interactivelyMode(root, commandsManager, fileManager, console, parsingInterface);
        }



    }
}
