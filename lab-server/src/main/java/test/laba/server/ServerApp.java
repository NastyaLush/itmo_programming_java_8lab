package test.laba.server;

import java.sql.SQLException;
import java.util.logging.Logger;

import test.laba.common.IO.Colors;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;

public class ServerApp {
    public static final Logger LOGGER = Logger.getLogger(ServerApp.class.getName());
    private final int port;
    private CommandsManager commandsManager;
    private final BufferedReader in;
    private final int capacity = 1000;
    private BDManager bdManager;
    private BDUsersManager bdUsersManager;


    public ServerApp(int port) {
        LOGGER.setLevel(Level.ALL);
        this.port = port;
        this.in = new BufferedReader(new InputStreamReader(System.in));
    }

    public void run() throws IOException {
        SocketAddress address = new InetSocketAddress(port);
        ServerSocketChannel serverSocketChannel;
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(address);
        serverSocketChannel.configureBlocking(false);

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        LOGGER.info("server works");
        bdstart();
        interactivelyModule(selector, serverSocketChannel);
    }

    public BasicResponse executeCommand(ByteBuffer byteBuffer) throws IOException, ClassNotFoundException {
        BasicResponse response = ObjectWrapper.serverDeserialize(byteBuffer);

        BasicResponse answer = authorisation(response);
        if (response.getCommand().equals(Values.REGISTRATION.toString())) {
            LOGGER.fine("registration method");
            try {
                bdUsersManager.add((RegisterResponse) response);
                LOGGER.info(Util.giveColor(Colors.BlUE, "the user was successfully authorized"));
                return new Response(Util.giveColor(Colors.BlUE, "the user was successfully authorized"));
            } catch (SQLException e) {
                e.printStackTrace();
                LOGGER.warning(Util.giveColor("the user wasn't added, because of " + e.getCause(), Colors.RED));
                return new ResponseWithError("the user wasn't authorized because of " + e.getMessage());
            }
        }
        try {
            if (isAuothorisated(response.getLogin(), response.getPassword())) {
                if (!response.getCommand().equals(Values.COLLECTION.toString()) && response instanceof Response) {
                    LOGGER.fine("execute method: " + response);
                    return commandsManager.chooseCommand((Response) response);
                }
                answer = new ResponseWithCollection(commandsManager.getCommandValues());
            } else {
                answer = new ResponseWithError("the user wasn't authorised");
            }
        } catch (SQLException | WrongUsersData e) {
            // TODoO: 12.05.2022
            e.printStackTrace();
            answer = new ResponseWithError("the user wasn't authorised: " + e.getMessage());
        }
        return answer;
    }

    public void read(SelectionKey selectionKey) throws IOException, ClassNotFoundException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer buf = readInBuf(socketChannel);
        BasicResponse response = executeCommand(buf);
        selectionKey.attach(response);
        selectionKey.interestOps(SelectionKey.OP_WRITE);
        buf.clear();
    }

    //private void BDstart(String dbHost, String dbName, String dbUser, String dbPassword){
    private void bdstart() {
        String dbHost = "pg";
        String dbName = "studs";
        String dbUser = "s336767";
        String dbPassword = "azi261";

        try {
            LOGGER.info("BD was connected");
            bdManager = new BDManager("products", dbHost, dbName, dbUser, dbPassword);
            bdUsersManager = new BDUsersManager("users", dbHost, dbName, dbUser, dbPassword);
            commandsManager = new CommandsManager(bdManager.getProducts());

        } catch (SQLException throwables) {
            // TODOoo: 11.05.2022
            throwables.printStackTrace();
        } catch (VariableException e) {
            e.printStackTrace();
            // TODO0: 11.05.2022
        } catch (CreateError createError) {
            // TODO0: 11.05.2022
            createError.printStackTrace();
        }
    }

    private ByteBuffer readInBuf(SocketChannel socketChannel) throws IOException {
        int newCapacity = capacity;
        ByteBuffer buf = ByteBuffer.allocate(capacity);
        while (socketChannel.read(buf) > 0) {
            newCapacity = newCapacity * 2;
            ByteBuffer byteBuffer = ByteBuffer.allocate(newCapacity);
            byteBuffer.put(buf.array());
            buf = byteBuffer.slice();
        }
        return buf;
    }

    public boolean write(SelectionKey selectionKey) throws IOException {
        BasicResponse response = (BasicResponse) selectionKey.attachment();
        String answer = response.getCommand();
        ByteBuffer byteBuffer = ObjectWrapper.serialize(selectionKey.attachment());
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        socketChannel.write(byteBuffer);
        selectionKey.interestOps(SelectionKey.OP_READ);
        byteBuffer.clear();
        return !"exit".equals(answer);
    }

    public void accept(ServerSocketChannel serverSocketChannel, Selector selector, SelectionKey selectionKey) {
        try {
            SocketChannel socket = serverSocketChannel.accept();
            socket.configureBlocking(false);
            socket.register(selector, SelectionKey.OP_READ);
            LOGGER.info("Connection from: " + socket);
        } catch (IOException e) {
            LOGGER.info("Unable to accept channel");
            //e.printStackTrace();
            selectionKey.cancel();
        }
    }

    private boolean consoleInput() throws IOException {
        boolean flag = false;
        String command;
        int isReadyConsole = System.in.available();
        if (isReadyConsole > 0) {
            try {
                command = in.readLine().trim().toLowerCase();
                switch (command) {
                    case "exit":
                        flag = true;
                        break;
                    case "save":
                        //commandsManager.save();
                        LOGGER.info("Collection was saved");
                        break;

                    default:
                        LOGGER.config("There is no so command");
                }
            } catch (NullPointerException e) {
                LOGGER.warning("you write null, please repeat input ");
            }

        }
        return flag;
    }

    public boolean console(Selector selector) throws IOException {
        if (consoleInput()) {
            Set<SelectionKey> keySet = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keySet.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                selectionKey.cancel();
            }
            //commandsManager.save();
            Util.toColor(Colors.GREEN, "Collection was saved\nThank you for using, goodbye");
            return false;
        }
        return true;
    }

    private void interactivelyModule(Selector selector, ServerSocketChannel serverSocketChannel) throws IOException {
        while (console(selector)) {
            int count = selector.select(1);
            if (count == 0) {
                continue;
            }
            Set<SelectionKey> keySet = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keySet.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                if (selectionKey.isAcceptable() && selectionKey.isValid()) {
                    accept(serverSocketChannel, selector, selectionKey);
                }
                if (selectionKey.isValid() && selectionKey.isReadable()) {
                    try {
                        read(selectionKey);
                    } catch (ClassNotFoundException | IOException e) {
                        LOGGER.info("The client was unconnected" + selectionKey.channel());
                        selectionKey.cancel();
                    }
                }
                if (selectionKey.isValid() && selectionKey.isWritable()) {
                    if (!write(selectionKey)) {
                        LOGGER.info("The client was unconnected" + selectionKey.channel());
                        selectionKey.cancel();
                    }
                }
                if (!selectionKey.isValid()) {
                    selectionKey.cancel();
                }
            }
        }
        selector.close();
        serverSocketChannel.close();
    }

    private boolean isAuothorisated(String login, String password) throws SQLException, WrongUsersData {
        return bdUsersManager.isAuthorisated(login, password);

    }

    private BasicResponse authorisation(BasicResponse response) {
        BasicResponse response1 = response;
        if (response.getCommand().equals(Values.AUTHORISATION.toString())) {
            LOGGER.fine("authorisation method");
            try {
                if (isAuothorisated(response.getLogin(), response.getPassword())) {
                    response1 = new Response("the user successfully authorised");
                }
            } catch (SQLException e) {
                // TODOo: 12.05.2022
                response1 = new ResponseWithError("the user wasn't authorised, because of " + e.getMessage());
                e.printStackTrace();
            } catch (WrongUsersData wrongUsersData) {
                response1 = new ResponseWithError(wrongUsersData.getMessage());
            }

        }
        return response1;
    }
}
