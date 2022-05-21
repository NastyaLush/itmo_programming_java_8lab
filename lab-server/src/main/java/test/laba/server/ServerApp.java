package test.laba.server;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import test.laba.common.IO.Colors;
import test.laba.common.exception.AlreadyExistLogin;
import test.laba.common.exception.CreateError;
import test.laba.common.exception.VariableException;
import test.laba.common.exception.WrongUsersData;
import test.laba.common.responses.RegisterResponse;
import test.laba.common.responses.Response;
import test.laba.common.responses.ResponseWithCollection;
import test.laba.common.IO.ObjectWrapper;
import test.laba.common.responses.ResponseWithError;
import test.laba.common.responses.BasicResponse;
import test.laba.common.util.Util;
import test.laba.common.util.Values;
import test.laba.server.BD.BDManager;
import test.laba.server.BD.BDUsersManager;
import test.laba.server.mycommands.CommandsManager;
import test.laba.server.mycommands.commands.Variable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;

public class ServerApp {
    public static final Logger LOGGER = Logger.getLogger(ServerApp.class.getName());
    private final int port;
    private CommandsManager commandsManager;
    private final BufferedReader in;
    private final int capacity = 300;
    private final int countOfUsers = 100;
    private BDManager bdManager;
    private BDUsersManager bdUsersManager;
    private final ExecutorService responseReaderPool;
    private final ExecutorService responseExecutorPool;
    private final ExecutorService responseSenderPool;


    public ServerApp(int port) {
        LOGGER.setLevel(Level.ALL);
        this.port = port;
        this.in = new BufferedReader(new InputStreamReader(System.in));
        this.responseReaderPool = Executors.newFixedThreadPool(countOfUsers);
        this.responseSenderPool = Executors.newCachedThreadPool();
        this.responseExecutorPool = Executors.newCachedThreadPool();
    }

    public void run() throws IOException, VariableException, CreateError, SQLException, NoSuchAlgorithmException {
        SocketAddress address = new InetSocketAddress(port);
        ServerSocketChannel serverSocketChannel;
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(address);
        serverSocketChannel.configureBlocking(false);


        LOGGER.info("server works");
        if (Variable.HOST != null && Variable.NAME != null && Variable.LOGIN != null && Variable.PASSWORD != null) {
            bdStart(Variable.HOST, Variable.NAME, Variable.LOGIN, Variable.PASSWORD);
            interactivelyModule(serverSocketChannel);
        } else {
            LOGGER.warning(Util.giveColor(Colors.RED, "Please check environment variables : host, name, login, password and restart server again"));
        }
    }


    private void bdStart(String dbHost, String dbName, String dbUser, String dbPassword) throws SQLException, VariableException, CreateError {
        LOGGER.info("BD was connected");
        bdUsersManager = new BDUsersManager("users", dbHost, dbName, dbUser, dbPassword);
        bdUsersManager.createTable();
        bdManager = new BDManager("products", dbHost, dbName, dbUser, dbPassword);
        bdManager.createTable();
        commandsManager = new CommandsManager(bdManager, bdUsersManager);

    }


    private boolean consoleInput() throws IOException {
        boolean flag = false;
        String command;
        int isReadyConsole = System.in.available();
        if (isReadyConsole > 0) {
            try {
                command = in.readLine().trim().toLowerCase();
                if ("exit".equals(command)) {
                    flag = true;
                } else {
                    LOGGER.config("There is no so command");
                }
            } catch (NullPointerException e) {
                LOGGER.warning("you write null, please repeat input ");
            }

        }
        return flag;
    }

    public boolean console() throws IOException {
        if (consoleInput()) {
            Util.toColor(Colors.GREEN, "Thank you for using app, goodbye");
            return false;
        }
        return true;
    }

    private void interactivelyModule(ServerSocketChannel serverSocketChannel) throws IOException {
        while (console()) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel != null) {
                socketChannel.configureBlocking(false);
                responseReaderPool.submit(new Client(socketChannel));
            }
        }
        responseReaderPool.shutdownNow();
        responseSenderPool.shutdownNow();
        responseExecutorPool.shutdownNow();
        LOGGER.info("For all threads shutdown was completed");

    }

    private boolean isAuthorised(String login, String password) throws SQLException, WrongUsersData, NoSuchAlgorithmException {
        return bdUsersManager.isAuthorized(login, password);

    }


    private class Authorisator {
        private BasicResponse basicResponse;

        private boolean authorisation(BasicResponse response) throws NoSuchAlgorithmException {
            if (response.getCommand().equals(Values.AUTHORISATION.toString())) {
                LOGGER.fine("authorisation method");
                try {
                    if (isAuthorised(response.getLogin(), response.getPassword())) {
                        basicResponse = new Response("the user successfully authorised");
                        return true;
                    }
                } catch (SQLException e) {
                    basicResponse = new ResponseWithError("the user wasn't authorised, because of " + e.getMessage());
                } catch (WrongUsersData wrongUsersData) {
                    basicResponse = new ResponseWithError(wrongUsersData.getMessage());
                }
                return false;
            }
            return true;
        }

        public BasicResponse getResponse() {
            return basicResponse;
        }
    }

    private class Registrator {
        private BasicResponse basicResponse;

        public boolean registration(BasicResponse response) throws NoSuchAlgorithmException {
            if (response.getCommand().equals(Values.REGISTRATION.toString())) {
                LOGGER.fine("registration method");
                try {
                    bdUsersManager.add((RegisterResponse) response);
                    LOGGER.info(Util.giveColor(Colors.BlUE, "the user was successfully authorized"));
                    basicResponse = new Response(Util.giveColor(Colors.BlUE, "the user was successfully authorized"));
                    return true;
                } catch (SQLException | AlreadyExistLogin e) {
                    LOGGER.warning(Util.giveColor("the user wasn't added, because of " + e.getCause(), Colors.RED));
                    basicResponse = new ResponseWithError("the user wasn't authorized because of " + e.getMessage());
                    return false;
                }
            }
            return true;
        }

        public BasicResponse getBasicResponse() {
            return basicResponse;
        }
    }

    private class Executor implements Runnable {
        private BasicResponse basicResponse;
        private final ByteBuffer byteBuffer;

        Executor(ByteBuffer byteBuffer) {
            this.byteBuffer = byteBuffer;
        }

        public BasicResponse execute() throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
            BasicResponse response = ObjectWrapper.serverDeserialize(byteBuffer);
            //check authorisation
            Authorisator authorisation = new Authorisator();
            if (!authorisation.authorisation(response)) {
                return authorisation.getResponse();
            }

            Registrator registration = new Registrator();
            if (!registration.registration(response)) {
                return registration.getBasicResponse();
            } else {
                try {
                    if (isAuthorised(response.getLogin(), response.getPassword())) {
                        if (!response.getCommand().equals(Values.COLLECTION.toString()) && response instanceof Response) {
                            LOGGER.fine("execute method: " + response);
                            return commandsManager.chooseCommand((Response) response);
                        }
                        response = new ResponseWithCollection(commandsManager.getCommandValues());
                    } else {
                        response = new ResponseWithError("the user wasn't authorised");
                    }
                } catch (SQLException | WrongUsersData e) {
                    response = new ResponseWithError("the user wasn't authorised: " + e.getMessage());
                }
                return response;
            }
        }

        @Override
        public void run() {
            try {
                basicResponse = execute();
            } catch (IOException | ClassNotFoundException | NoSuchAlgorithmException e) {
                basicResponse = new ResponseWithError("the user command wasn't execute because of " + e.getMessage());
            }
        }

        public BasicResponse getBasicResponse() {
            return basicResponse;
        }
    }

    private class Client implements Runnable {

        private final SocketChannel socketChannel;
        private boolean running = true;
        private boolean isReade = false;

        Client(SocketChannel socketChannel) {
            this.socketChannel = socketChannel;
        }


        @Override
        public void run() {
            LOGGER.info(Util.giveColor(Colors.BlUE, "the new client was connected and start execute"));
            try {
                while (running && !Thread.currentThread().isInterrupted()) {
                    read();
                }
            } catch (IOException | ClassNotFoundException | NoSuchAlgorithmException e) {
                LOGGER.warning("impossible to connect with "
                        + socketChannel.socket().getLocalSocketAddress()
                        + " because of " + e.getMessage() + " the channel is closing");
                close();
            }
            close();
        }

        public void read() throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
            LOGGER.config("the read method starts");
            isReade = false;
            ByteBuffer buf = readInBuf();
            if (isReade) {
                executeCommand(buf);
                buf.clear();
            }
            LOGGER.config("the read method finished");
        }

        public void executeCommand(ByteBuffer byteBuffer) {
            Executor executor = new Executor(byteBuffer);
            responseExecutorPool.submit(() -> {
                executor.run();
                responseSenderPool.submit(() -> {
                    try {
                        if (!write(executor.getBasicResponse())) {
                            close();
                        }
                    } catch (IOException e) {
                        LOGGER.warning("impossible to run because of " + e.getMessage());
                        close();
                    }
                });
            });
        }

        private ByteBuffer readInBuf() throws IOException {
            int newCapacity = capacity;
            ByteBuffer buf = ByteBuffer.allocate(capacity);
            while (socketChannel.read(buf) > 0) {
                isReade = true;
                newCapacity = newCapacity * 2;
                ByteBuffer byteBuffer = ByteBuffer.allocate(newCapacity);
                byteBuffer.put(buf.array());
                buf = byteBuffer.slice();
            }
            return buf;
        }

        private boolean write(BasicResponse response) throws IOException {
            LOGGER.config("the write method starts");
            String answer = response.getCommand();
            ByteBuffer byteBuffer = ObjectWrapper.serialize(response);
            socketChannel.write(byteBuffer);
            byteBuffer.clear();
            LOGGER.config("the write method finished");
            return !"exit".equals(answer);
        }

        private void close() {
            try {
                LOGGER.info("the client was disconnected + " + socketChannel);
                socketChannel.close();
            } catch (IOException e) {
                LOGGER.warning("impossible to close channel because of " + e.getMessage());
            }
        }
    }

}

