package test.laba.client;


import java.util.logging.Logger;

import test.laba.client.productFillers.ConsoleParsing;
import test.laba.client.productFillers.UpdateId;
import test.laba.client.util.Console;
import test.laba.client.util.ScriptConsole;
import test.laba.client.util.VariableParsing;
import test.laba.client.util.Wrapper;
import test.laba.common.IO.Colors;
import test.laba.common.dataClasses.Product;
import test.laba.common.exception.CreateError;
import test.laba.common.exception.ScriptError;
import test.laba.common.exception.VariableException;
import test.laba.common.exception.CycleInTheScript;
import test.laba.common.responses.RegisterResponse;
import test.laba.common.responses.Response;
import test.laba.common.responses.ResponseWithError;
import test.laba.common.util.Util;
import test.laba.common.util.Values;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Level;

public class ClientApp {
    public static final Logger LOGGER = Logger.getLogger(ClientApp.class.getName());
    private final Console console = new Console();
    private Map valuesOfCommands = null;
    private Wrapper wrapper;
    private boolean isNormalUpdateID = true;
    private HashSet<String> executeScriptFiles = new HashSet<>();
    private boolean isExitInExecuteScript = false;
    private String login;
    private String password;

    ClientApp() {
        LOGGER.setLevel(Level.INFO);
    }

    public void interactivelyMode() {
        LOGGER.log(Level.FINE, "The interactively Mode starts");
        try {
            registeringUser();
            wrapper.sent(new Response(login, password, Values.COLLECTION.toString()));
            valuesOfCommands = wrapper.readWithMap();
            LOGGER.info(Util.giveColor(Colors.BlUE, "Program in an interactive module, for giving information about opportunities write help"));
            String answer;
            while ((answer = console.read()) != null) {
                try {
                    String[] command = answer.split(" ", 2);
                    if (command.length < 2) {
                        command = new String[]{command[0], ""};
                    }
                    sendAndReceiveCommand(command, console);

                } catch (IOException e) {
                    LOGGER.info(Util.giveColor(Colors.GREEN, "server was closed, app is finishing work :) \nSee you soon!"));
                    break;
                } catch (CycleInTheScript | ClassNotFoundException e) {
                    LOGGER.warning(e.getMessage());
                }
                if (ifReadyToClose(answer) || isExitInExecuteScript) {
                    wrapper.close();
                    Util.toColor(Colors.GREEN, "see you soon :)");
                    break;
                }

            }
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.warning(Util.giveColor(Colors.RED, "The client can't exist because of: " + e.getMessage()));
        }
        LOGGER.fine("the method was closed");

    }

    public Response updateID(String[] command, Console console2) throws VariableException, IOException, ClassNotFoundException {
        LOGGER.fine("update id is executing");
        long id = VariableParsing.toLongNumber(command[1]);
        Response response = new Response(login, password, command[0], id);
        response.setFlag(false);
        wrapper.sent(response);
        response = wrapper.readResponse();
        if (response instanceof ResponseWithError) {
            console.print(response.getCommand());
            isNormalUpdateID = false;
        } else {
            Product product = response.getProduct();
            response = new Response(login, password, Values.PRODUCT_WITH_QUESTIONS.toString(), id, new UpdateId(new ConsoleParsing(console2), console2).execute(product));
            response.setFlag(true);
        }
        LOGGER.fine("update id was execute");
        return response;
    }

    public Response sendUniqCommand(String[] command, Console console2) throws IOException {
        LOGGER.fine("sent unit command starts");
        Values value = (Values) valuesOfCommands.get(command[0]);
        Response response = null;
        boolean isWrongArguments = true;
        while (isWrongArguments) {
            try {
                isWrongArguments = false;
                switch (value) {
                    case PRODUCT:
                        response = new Response(login, password, command[0], VariableParsing.toLongNumber(command[1]), new ConsoleParsing(console2).parsProductFromConsole());
                        break;
                    case UNIT_OF_MEASURE:
                        response = new Response(login, password, command[0], VariableParsing.toRightUnitOfMeasure(command[1]));
                        break;
                    case KEY:
                        response = new Response(login, password, command[0], VariableParsing.toLongNumber(command[1]));
                        break;
                    case PRODUCT_WITH_QUESTIONS:
                        response = updateID(command, console2);
                        break;

                    case PRODUCT_WITHOUT_KEY:
                        response = new Response(login, password, command[0], new ConsoleParsing(console2).parsProductFromConsole());
                        break;
                    default:
                        break;
                }
            } catch (VariableException | IllegalArgumentException | CreateError | NullPointerException | ClassNotFoundException e) {
                console2.printError("repeat writing, create error\n" + e.getMessage());
                isWrongArguments = true;
                command[1] = console2.read();
            }
        }
        LOGGER.fine("the unique command was send");
        return response;
    }

    public void sendAndReceiveCommand(String[] command, Console console2) throws IOException, CycleInTheScript, ClassNotFoundException {
        LOGGER.fine("send and receive starts ");
        Response response;
        if (valuesOfCommands.containsKey(command[0].trim().toLowerCase())) {
            response = sendUniqCommand(command, console2);
        } else {
            response = new Response(login, password, command[0], command[1]);
        }

        if (isNormalUpdateID) {
            wrapper.sent(response);
            response = wrapper.readResponse();
            console.print(response.getCommand());
            isExitInExecuteScript = ifReadyToClose(response.getCommand().trim());
            if (response.getCommand().equals(Values.SCRIPT.toString())) {
                readScript(response);
            }
        } else {
            isNormalUpdateID = true;
        }
        LOGGER.fine("send and receive finishes");
    }


    public void run(String host, int port) throws IOException {
        LOGGER.fine("server runs");
        try (Socket socket = new Socket(host, port)) {
            wrapper = new Wrapper(socket);
            LOGGER.info(Util.giveColor(Colors.GREEN, "client was connected"));
            interactivelyMode();

        }
        Util.toColor(Colors.GREEN, "goodbye");
        LOGGER.fine("run was executed");

    }


    public void readScript(Response response) throws CycleInTheScript {
        String fileName = response.getMessage().trim();
        try (FileReader fr = new FileReader(fileName)) {
            addToStack(fileName);
            try (BufferedReader reader = new BufferedReader(fr)) {
                ScriptConsole scriptConsole = new ScriptConsole(reader, fr);
                Util.toColor(Colors.BlUE, "Start executing script: " + fileName);
                while (reader.ready() && !isExitInExecuteScript) {
                    String[] command = (reader.readLine().trim() + " ").split(" ", 2);
                    if (command.length < 2) {
                        command = new String[]{command[0], ""};
                    }
                    sendAndReceiveCommand(command, scriptConsole);
                }
            }
        } catch (FileNotFoundException e) {
            LOGGER.warning(Util.giveColor(Colors.RED, "File not found, check out path or file rights: " + e.getMessage()));
        } catch (IOException e) {
            LOGGER.warning(Util.giveColor(Colors.RED, "failed to execute the script"));
            cleanStack();
        } catch (ClassNotFoundException e) {
            LOGGER.warning(Util.giveColor(Colors.RED, "Can not sent command"));
        } catch (ScriptError e) {
            LOGGER.warning(Util.giveColor(Colors.RED, "the script was closed"));
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

    private boolean ifReadyToClose(String answer) {
        return "exit".equals(answer.trim());
    }

    private void registeringUser() throws IOException, ClassNotFoundException {
        String answer = console.askFullQuestion(Util.giveColor(Colors.BlUE, "Would you like to create new login and password or you are registering first?(print yes or else if your answer no)"));
        login = new ConsoleParsing(console).parsField(Util.giveColor(Colors.BlUE, "Enter your login"), VariableParsing::toRightName);
        password = new ConsoleParsing(console).parsField(Util.giveColor(Colors.BlUE, "Enter your password"), VariableParsing::toRightName);
        if ("yes".equals(answer.trim())) {
                RegisterResponse response1 = new RegisterResponse(login, password, Values.REGISTRATION.toString());
                wrapper.sent(response1);
                Response response = wrapper.readResponse();
                LOGGER.info(response.getCommand());
        } else {
                RegisterResponse response1 = new RegisterResponse(login, password, Values.AUTHORISATION.toString());
                wrapper.sent(response1);
                Response response = wrapper.readResponse();
                if (response instanceof ResponseWithError) {
                    LOGGER.info(response.getCommand());
                    registeringUser();
                } else {
                    LOGGER.info(response.getCommand());
                }
        }
        LOGGER.info("the registration is finished ");
    }


}
