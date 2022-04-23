package test.laba.server.mycommands;

import test.laba.common.IO.ScriptConsole;
import test.laba.common.commands.Root;

import java.io.BufferedReader;
import java.io.IOException;

public class work_with_script {
    public void readScript(BufferedReader reader, Root root, CommandsManager commandsManager, ScriptConsole scriptConsole) throws IOException {
      /*  String[] command;
        String answer;
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
            answer = commandsManager.chooseCommand(command);
            scriptConsole.print(answer);
            if ("Thank you for using".equals(answer)) {
                reader.close();
                break;
            }
        }
        reader.close();*/
        // TODO: 16.04.2022 work with script 
    }

}
