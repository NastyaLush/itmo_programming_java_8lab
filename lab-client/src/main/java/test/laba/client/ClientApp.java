package test.laba.client;


import java.util.ResourceBundle;
import java.util.logging.Logger;

import test.laba.client.frontEnd.frames.AuthorisationFrame;
import test.laba.client.frontEnd.frames.local.Localized;
import test.laba.client.productFillers.ConsoleParsing;
import test.laba.client.productFillers.UpdateId;
import test.laba.client.util.Command;
import test.laba.client.util.Console;
import test.laba.client.util.Constants;
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

public class ClientApp implements Localized {
    public final Logger logger;
    private final Console console = new Console();
    private Map<String, Values> valuesOfCommands = null;
    private Wrapper wrapper;
    private boolean isNormalUpdateID = true;
    private final HashSet<String> executeScriptFiles = new HashSet<>();
    private ResourceBundle resourceBundle;
    private boolean isExitInExecuteScript = false;
    private String login;
    private String password;
    private ScriptConsole scriptConsole;
    private String host;
    private int port;

    ClientApp() {
        this.logger = Logger.getLogger(ClientApp.class.getName());
        logger.setLevel(Level.INFO);
    }

    public Response run(String newHost, int newPort, AuthorisationFrame authorisationFrame) throws IOException, ClassNotFoundException, InterruptedException {
        logger.fine("server runs");
        this.host = newHost;
        this.port = newPort;
        Socket socket = new Socket(host, port);
        wrapper = new Wrapper(socket);
        logger.info(Util.giveColor(Colors.GREEN, "client was connected"));
        Response response = interactivelyMode(authorisationFrame.getResponse(), authorisationFrame.isNewUser());
        logger.fine("run was executed");
        return response;
    }

    public Response interactivelyMode(Response response, Boolean isNewUser) throws IOException, ClassNotFoundException, InterruptedException {
        logger.log(Level.FINE, "The interactively Mode starts");
        Response response1 = registeringUser(response, isNewUser);
        if (!(response1 instanceof ResponseWithError)) {
            wrapper.sent(new Response(login, password, Values.COLLECTION.toString()));
            valuesOfCommands = wrapper.readWithMap();
            logger.info(Util.giveColor(Colors.BlUE, "Program in an interactive module, for giving information about opportunities write help"));
            logger.fine("the method was closed");
        }
        return response1;
    }

    public Response workCycle(Response response, ResourceBundle newResourceBundle) {
        this.resourceBundle = newResourceBundle;
        Response newResponse = new Response("null response");
        try {
            if (isExitInExecuteScript) {
                try {
                    wrapper.close();
                    Util.toColor(Colors.GREEN, "see you soon :)");
                    newResponse = new Response(Command.CLOSE.getString());
                } catch (IOException e) {
                    logger.warning(e.getMessage());
                    newResponse = new Response(Command.CLOSE.getString(), e.getMessage());
                }
            } else {
                Response answer = sendAndReceiveCommand(response);
                if (answer != null) {
                    return answer;
                }
            }
        } catch (CycleInTheScript | ClassNotFoundException e) {
            logger.warning(e.getMessage());
            newResponse =  new ResponseWithError(localisation(Constants.CYCLE_IN_THE_SCRIPT));
        } catch (IOException e) {
            try( Socket socket = new Socket(host, port)) {
                wrapper = new Wrapper(socket);
                newResponse =  workCycle(response, resourceBundle);
            } catch (IOException ex) {
                newResponse =  new ResponseWithError(localisation(Constants.SERVER_CLOSED));
            }
        }
        return newResponse;
    }
    public Response updateID(String[] command, Console console2) throws VariableException, IOException, ClassNotFoundException {
        logger.fine("update id is executing");
        long id = VariableParsing.toLongNumber(command[1], Constants.KEY.getString(), resourceBundle);
        Response response = new Response(login, password, command[0], id);
        response.setFlagUdateID(false);
        wrapper.sent(response);
        response = wrapper.readResponse();
        if (response instanceof ResponseWithError) {
            console.print(response.getCommand());
            isNormalUpdateID = false;
        } else {
            Product product = response.getProduct();
            response = new Response(login, password, Values.PRODUCT_WITH_QUESTIONS.toString(), id, new UpdateId(new ConsoleParsing(console2, resourceBundle), console2, resourceBundle).execute(product));
        }
        logger.fine("update id was execute");
        return response;
    }

    public Response sendUniqueCommand(String[] command, Console console2) throws IOException, VariableException, ClassNotFoundException, CreateError {
        logger.fine("sent unit command starts");
        Values value = valuesOfCommands.get(command[0]);
        Response response = null;
        switch (value) {
            case PRODUCT:
                response = new Response(login, password, command[0], VariableParsing.toLongNumber(Constants.ID.getString(), command[1], resourceBundle), new ConsoleParsing(console2, resourceBundle).parsProductFromConsole());
                break;
            case UNIT_OF_MEASURE:
                response = new Response(login, password, command[0], VariableParsing.toRightUnitOfMeasure(Constants.UNIT_OF_MEASURE.getString(), command[1], resourceBundle));
                break;
            case KEY:
                response = new Response(login, password, command[0], VariableParsing.toLongNumber(Constants.KEY.getString(), command[1], resourceBundle));
                break;
            case PRODUCT_WITH_QUESTIONS:
                response = updateID(command, console2);
                break;
            case PRODUCT_WITHOUT_KEY:
                response = new Response(login, password, command[0], new ConsoleParsing(console2, resourceBundle).parsProductFromConsole());
                break;
            default:
                break;
        }
        logger.fine("the unique command was send");
        return response;
    }

    public Response sendAndReceiveCommand(Response response) throws IOException, CycleInTheScript, ClassNotFoundException {
        logger.fine("send and receive starts ");
        response.setLoginAndPassword(login, password);
        if (response.getCommand().equals("execute_script")) {
            readScript(response);
            return new Response(scriptConsole.getAnswer());
        } else {
            if (isNormalUpdateID) {
                wrapper.sent(response);
                Response readiedResponse = wrapper.readResponse();
                isExitInExecuteScript = ifReadyToClose(readiedResponse.getCommand().trim());
                return readiedResponse;
            } else {
                isNormalUpdateID = true;
            }
        }
        logger.fine("send and receive finishes");
        return null;
    }

    public void sendAndReceiveCommandScript(String[] command) throws IOException, CycleInTheScript, ClassNotFoundException, VariableException, CreateError {
        logger.fine("send and receive starts "); //console
        Response response;
        if (valuesOfCommands.containsKey(command[0].trim().toLowerCase())) {
            response = sendUniqueCommand(command, scriptConsole);
            if (response == null) {
                isNormalUpdateID = false;
            }
        } else {
            response = new Response(login, password, command[0], command[1]);
        }

        if (isNormalUpdateID) {
            wrapper.sent(response);
            response = wrapper.readResponse();
            scriptConsole.print(response.getCommand());
            isExitInExecuteScript = ifReadyToClose(response.getCommand().trim());
            if (response.getCommand().equals(Values.SCRIPT.toString())) {
                readScript(response);
            }
        } else {
            isNormalUpdateID = true;
        }
        logger.fine("send and receive finishes");
    }


    public void readScript(Response response) throws CycleInTheScript {
        String fileName = response.getMessage().trim();
        BufferedReader reader = null;
        try (FileReader fr = new FileReader(fileName)) {
            addToStack(fileName);
            reader = new BufferedReader(fr);
            scriptConsole = new ScriptConsole(reader, fr);
            Util.toColor(Colors.BlUE, "Start executing script: " + fileName);
            while (reader.ready() && !isExitInExecuteScript) {
                String[] command = (reader.readLine().trim() + " ").split(" ", 2);
                if (command.length < 2) {
                    command = new String[]{command[0], ""};
                }
                try {
                    sendAndReceiveCommandScript(command);
                } catch (ClassNotFoundException | VariableException | CreateError e) {
                    logger.warning(e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            logger.warning(Util.giveColor(Colors.RED, "File not found, check out path or file rights: " + e.getMessage()));
        } catch (IOException e) {
            logger.warning(Util.giveColor(Colors.RED, "failed to execute the script"));
            cleanStack();
        } catch (ScriptError e) {
            logger.warning(Util.giveColor(Colors.RED, "the script was closed"));
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                logger.warning(e.getMessage());
            }
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

    private Response registeringUser(Response response, Boolean isNewUser) throws IOException, ClassNotFoundException {
        logger.info("the registration is started ");
        login = response.getLogin();
        password = response.getPassword();
        RegisterResponse response1;
        if (isNewUser) {
            response1 = new RegisterResponse(login, password, Values.REGISTRATION.toString());
        } else {
            response1 = new RegisterResponse(login, password, Values.AUTHORISATION.toString());
        }
        logger.info("the registration is finished ");
        return registering(response1);
    }

    private Response registering(RegisterResponse response1) throws IOException, ClassNotFoundException {
        wrapper.sent(response1);
        Response response = wrapper.readResponse();
        if (response instanceof ResponseWithError) {
            logger.info(response.getCommand());
            return new ResponseWithError(response.getCommand());
        } else {
            logger.info(response.getCommand());
            return new Response("fine");
        }
    }

    public void close(Constants constants, String message) {
       /* homeFrame.setResponse(new ResponseWithError(homeFrame.localisation(constants) + " " + message));
        homeFrame.close(homeFrame.getFrame());*/
    }

    @Override
    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }
}
