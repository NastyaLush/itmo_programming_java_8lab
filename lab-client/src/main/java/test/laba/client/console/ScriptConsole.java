package test.laba.client.console;

import test.laba.client.commands.ExecuteScript;
import test.laba.client.exception.exucuteError;

import java.io.BufferedReader;
import java.io.IOException;

public class ScriptConsole extends Console{
    private boolean flag=true;
    private Console console=null;
    private BufferedReader reader=null;
    private ScriptConsole scriptConsole;
    public ScriptConsole(){
    }
    public ScriptConsole(BufferedReader reader,Console console){
        this.reader=reader;
        this.scriptConsole=new ScriptConsole();
        this.console=console;
    }
    public String scanner()  {
        String command;
        try {
            command=(reader.readLine().trim());
            return command;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "exit" ;
    }
    public void print(Object object){
        System.out.println(object);
    }
    public void  printError(Object object) throws exucuteError {
        System.out.println(ANSI_RED+object+ANSI_RESET);
        throw new exucuteError("не фортануло",console);

    }
    public void ask(Object object){System.out.println(object);}
    public boolean isFlag() {
        return flag;
    }
}
