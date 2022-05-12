package test.laba.server.mycommands.commands;

public interface Variable {
    String ENV_VARIABLE = System.getenv("LABA");
    String USERS = System.getenv("USERS");
    default String getUsers() {
        return USERS;
    }
}

