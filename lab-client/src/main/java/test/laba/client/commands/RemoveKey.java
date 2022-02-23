package test.laba.client.commands;


import test.laba.client.console.Console;
import test.laba.client.console.ConsoleParsing;
import test.laba.client.exception.exucuteError;
import test.laba.client.mainClasses.Root;

public class RemoveKey extends AbstractCommand {
    public RemoveKey(){
        super("RemoveKey","удалить элемент из коллекции по его ключу");
    }
    public void removeKey(String arg, Root root, Console console, ConsoleParsing parsingInterface) throws exucuteError {
        Long x= parsingInterface.toLongNumber(arg,console);
        try {
            root.getProducts().remove(x);
            console.print("Объект удален");
        }
        catch (UnsupportedOperationException e){
            console.printError("Данная операция не поддерживается HashMap");
        }
        catch (ClassCastException | NullPointerException e){
            console.printError("Невозможно выполнить операцию "+ e);
        }

    }
}
