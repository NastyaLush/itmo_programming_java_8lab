package test.laba.server.mycommands.commands;


public final class Variable {
    public static final String HOST = System.getenv("host");
    public static final String PASSWORD = System.getenv("password");
    public static final String LOGIN = System.getenv("login");
    public static final String NAME = System.getenv("name");

    private Variable() {
    }

}

