package test.laba.client;


import test.laba.common.IO.Console;
import test.laba.common.dataClasses.Product;
import test.laba.common.exception.VariableException;
import test.laba.common.exception.СycleInTheScript;
import test.laba.common.util.Response;
import test.laba.common.util.ResponseWithError;
import test.laba.common.util.Values;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

public class ClientApp {
    private final Console console = new Console();
    private final ConsoleParsing consoleParsing = new ConsoleParsing(console);
    private final VariableParsing variableParsing = new VariableParsing();
    private final Scanner userScanner = new Scanner(System.in);
    private final  UpdateId updateId = new UpdateId(console, consoleParsing);
    private Map valuesOfCommands = null;
    private Wrapper wrapper;
    private boolean isread = true;
    private HashSet<String> stack = new HashSet<>();
    public void interactivelyMode(){
        try {
            wrapper.sent(new Response(Values.COLLECTION.toString()));
            valuesOfCommands = wrapper.readWithMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
        console.print("Программа в интерактивном режиме, для получения информации о возможностях, введите help");
        String answer;
        while ((answer = console.read()) != null) {
            try {
                String [] command = answer.split(" ", 2);
                if(command.length < 2){
                    command = new String[]{command[0], ""};
                }
                try {
                    sendAndRecieveCommand(command);
                } catch (СycleInTheScript e) {
                    console.printError(e.getMessage());
                }
            } catch (IOException e) {
                console.print("server was closed, app is finishing work :) \nSee you soon!");
                break;
            }
            if ("exit".equals(answer)) {
                console.print("see you soon :)");
                break;
            }

        }
    }

    public void run(String host, int port) throws IOException {

        try(Socket socket = new Socket(host, port)){
            wrapper = new Wrapper(socket);
            System.out.println("client was connected");
            interactivelyMode();
        }

    }
    public void readScript(Response response) throws СycleInTheScript {
        String fileName = response.getMessage();
        try (FileReader fr = new FileReader(fileName.trim())) {
            addToStack(fileName);
            BufferedReader reader = new BufferedReader(fr);
            // TODO: 27.04.2022
            System.out.println("файл прочитан, начинаю выполнение");
            while (reader.ready()) {
                String [] command = (reader.readLine().trim() + " ").split(" ", 2);
                if(command.length < 2){
                    command = new String[]{command[0], ""};
                }
                sendAndRecieveCommand(command);
            }
        } catch (FileNotFoundException e) {
            console.printError("Файл не найден, проверьте путь или права:" + fileName);
        } catch (IOException e) {
            console.printError("не удалось выполнить скрипт");
            cleanStack();
        } finally {
            deleteFromStack(fileName);
        }
    }

    public void sendUniqCommand(String [] command ) throws IOException {
        Values value =(Values) valuesOfCommands.get(command[0]);
        boolean flag = true;
        while (flag){
            try {
                flag = false;
                switch (value) {
                    case PRODUCT:
                        Response response = new Response(command[0], variableParsing.toLongNumber(command[1]), consoleParsing.parsProductFromConsole());
                        wrapper.sentWithArguments(response);
                        break;
                    case UNIT_OF_MEASURE:
                        response = new Response(command[0], variableParsing.toRightUnitOfMeasure(command[1]));
                        wrapper.sentWithArguments(response);
                        break;
                    case KEY:
                        response = new Response(command[0], variableParsing.toLongNumber(command[1]));
                        wrapper.sentWithArguments(response);
                        break;
                    case PRODUCT_WITH_QUESTIONS:
                        long id =  variableParsing.toLongNumber(command[1]);
                        response = new Response(command[0],  id);
                        response.setFlag(false);
                        wrapper.sentWithArguments(response);
                        response = wrapper.readResponse();
                        if(response instanceof ResponseWithError){
                            // TODO: troubles with read double
                            console.print(response.getCommand());
                            isread = false;
                        }
                        else {
                            Product product = response.getProduct();
                            response = new Response(Values.PRODUCT_WITH_QUESTIONS.toString(),id , updateId.execute(product));
                            response.setFlag(true);
                            wrapper.sentWithArguments(response);
                        }
                        break;

                    // TODO: update_id
                    case PRODUCT_WITHOUT_KEY:
                        response = new Response(command[0], consoleParsing.parsProductFromConsole());
                        wrapper.sentWithArguments(response);
                        break;


                }
            } catch (VariableException | IllegalArgumentException e) {
                console.printError("repeat writing\n" + e.getMessage());
                command[1] = console.read();
                flag = true;
            }
        }
    }

    public void sendAndRecieveCommand(String [] command) throws IOException, СycleInTheScript {
        System.out.println("sendAndRecieveCommand");
        if(valuesOfCommands.containsKey(command[0].trim().toLowerCase())){
            sendUniqCommand(command);
        }
        else {
            wrapper.sent(new Response(command[0],command[1]));
        }

        if(isread){
            Response response = wrapper.readResponse();
            System.out.println(response.getCommand());
            if(response.getCommand().equals(Values.SCRIPT.toString())){
                System.out.println("if");
                readScript(response);
            } else {
                System.out.println("else");
                console.print(response.getMessage());
            }
        } else {
            isread = true;
        }
    }
    public void addToStack(String filename) throws СycleInTheScript {
        if(!containsInStack(filename)) {
            stack.add(filename);
        } else {
            throw new СycleInTheScript("Обнаружен цикл при выполнении скрипта");
        }
    }
    public void cleanStack() {
        stack.clear();
    }
    public void deleteFromStack(String fileName) {
        stack.remove(fileName);
    }
    public boolean containsInStack(String fileName) {
        return !stack.stream().allMatch(x -> !x.equals(fileName));
    }
}
