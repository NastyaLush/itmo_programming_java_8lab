package test.laba.server.mycommands.commands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public final class Variable {

    public static final String ENV_VARIABLE = System.getenv("LABA");
    public static final String USERS = System.getenv("USERS");
    public static final String HOST = System.getenv("host");
    public static final String PASSWORD = System.getenv("password");
    public static final String LOGIN = System.getenv("login");
    public static final String NAME = System.getenv("name");

    private Variable() {
    }

    public static String getContains(String path) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        return bufferedReader.readLine();
    }
}

