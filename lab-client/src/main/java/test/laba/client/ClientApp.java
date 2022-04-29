package test.laba.client;


import test.laba.client.productFillers.ConsoleParsing;
import test.laba.client.productFillers.UpdateId;
import test.laba.client.util.Console;
import test.laba.client.util.VariableParsing;
import test.laba.client.util.Wrapper;
import test.laba.common.dataClasses.Product;
import test.laba.common.exception.CreateError;
import test.laba.common.exception.VariableException;
import test.laba.common.exception.CycleInTheScript;
import test.laba.common.responses.Response;
import test.laba.common.responses.ResponseWithError;
import test.laba.common.util.Values;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.HashSet;
import java.util.Map;

public class ClientApp {
    private final ConsoleParsing consoleParsing = new ConsoleParsing();
    private final UpdateId updateId = new UpdateId(consoleParsing);
    private Map valuesOfCommands = null;
    private Wrapper wrapper;
    private boolean isNormalUpdateID = true;
    private HashSet<String> executeScriptFiles = new HashSet<>();

    public void interactivelyMode() {
        try {
            wrapper.sent(new Response(Values.COLLECTION.toString()));
            valuesOfCommands = wrapper.readWithMap();
        } catch (IOException | ClassNotFoundException e) {
            //e.printStackTrace();
            Console.printError("Can not give collection: " + e.getMessage());
        }
        Console.print("Программа в интерактивном режиме, для получения информации о возможностях, введите help");
        String answer;
        while ((answer = Console.read()) != null) {
            try {
                String[] command = answer.split(" ", 2);
                if (command.length < 2) {
                    command = new String[]{command[0], ""};
                }
                try {
                    sendAndReceiveCommand(command);
                    // need fix
                } catch (CycleInTheScript e) {
                    Console.printError(e.getMessage());
                } catch (ClassNotFoundException e) {
                    Console.printError(e.getMessage());
                }
            } catch (IOException e) {
                Console.print("server was closed, app is finishing work :) \nSee you soon!");
                break;
            }
            if ("exit".equals(answer)) {
                Console.print("see you soon :)");
                break;
            }

        }
    }

    public Response updateID(String[] command) throws VariableException, IOException, ClassNotFoundException {
        long id = VariableParsing.toLongNumber(command[1]);
        Response response = new Response(command[0], id);
        response.setFlag(false);
        wrapper.sent(response);
        response = wrapper.readResponse();
        if (response instanceof ResponseWithError) {
            Console.print(response.getCommand());
            isNormalUpdateID = false;
        } else {
            Product product = response.getProduct();
            response = new Response(Values.PRODUCT_WITH_QUESTIONS.toString(), id, updateId.execute(product));
            response.setFlag(true);
        }
        return response;
    }
    public Response sendUniqCommand(String[] command) throws IOException {
        Values value = (Values) valuesOfCommands.get(command[0]);
        Response response = null;
        boolean isWrongArguments = true;
        while (isWrongArguments) {
            try {
                isWrongArguments = false;
                switch (value) {
                    case PRODUCT:
                        response = new Response(command[0], VariableParsing.toLongNumber(command[1]), consoleParsing.parsProductFromConsole());
                        break;
                    case UNIT_OF_MEASURE:
                        response = new Response(command[0], VariableParsing.toRightUnitOfMeasure(command[1]));
                        break;
                    case KEY:
                        response = new Response(command[0], VariableParsing.toLongNumber(command[1]));
                        break;
                    case PRODUCT_WITH_QUESTIONS:
                        response = updateID(command);
                        break;

                    case PRODUCT_WITHOUT_KEY:
                        response = new Response(command[0], consoleParsing.parsProductFromConsole());
                        break;
                    default:
                        break;
                }
            } catch (VariableException | IllegalArgumentException | CreateError | ClassNotFoundException e) {
                Console.printError("repeat writing, create error\n" + e.getMessage());
                command[1] = Console.read();
                isWrongArguments = true;
            }
        }
        return response;
    }
    public void sendAndReceiveCommand(String[] command) throws IOException, CycleInTheScript, ClassNotFoundException {
        Response response;
        if (valuesOfCommands.containsKey(command[0].trim().toLowerCase())) {
            response = sendUniqCommand(command);
        } else {
            response = new Response(command[0], command[1]);
        }


        if (isNormalUpdateID) {
            wrapper.sent(response);
            response = wrapper.readResponse();
            Console.print(response.getCommand());
            if (response.getCommand().equals(Values.SCRIPT.toString())) {
                readScript(response);
            }
        } else {
            isNormalUpdateID = true;
        }
    }


    public void run(String host, int port) throws IOException {

        try (Socket socket = new Socket(host, port)) {
            wrapper = new Wrapper(socket);
            Console.print("client was connected");
            interactivelyMode();

        }
        Console.print("goodbye");

    }



    public void readScript(Response response) throws CycleInTheScript {
        String fileName = response.getMessage();
        try (FileReader fr = new FileReader(fileName.trim())) {
            addToStack(fileName);
            BufferedReader reader = new BufferedReader(fr);
            Console.print("Start executing script: " + fileName);
            while (reader.ready()) {
                String[] command = (reader.readLine().trim() + " ").split(" ", 2);
                if (command.length < 2) {
                    command = new String[]{command[0], ""};
                }
                sendAndReceiveCommand(command);
            }
        } catch (FileNotFoundException e) {
            Console.printError("File not found, check out path or file rights: " + fileName);
        } catch (IOException e) {
            Console.printError("failed to execute the script");
            cleanStack();
        } catch (ClassNotFoundException e) {
            Console.printError("Can not sent command");
        } finally {
            deleteFromStack(fileName);
        }
    }
    public void addToStack(String filename) throws CycleInTheScript {
        if (!containsInStack(filename)) {
            executeScriptFiles.add(filename);
        } else {
            throw new CycleInTheScript("Обнаружен цикл при выполнении скрипта");
        }
    }
    public void cleanStack() {
        executeScriptFiles.clear();
    }
    public void deleteFromStack(String fileName) {
        executeScriptFiles.remove(fileName);
    }
    public boolean containsInStack(String fileName) {
        return executeScriptFiles.stream().anyMatch(x -> x.equals(fileName));
    }

}
