package test.laba.server.mycommands.commands;

public interface Variable {
    String ENV_VARIABLE = System.getenv("LABA");
    default String getEnvVariable() {
        return ENV_VARIABLE;
    }
}

