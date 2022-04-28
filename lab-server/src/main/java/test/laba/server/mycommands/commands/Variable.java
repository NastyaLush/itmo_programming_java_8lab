package test.laba.server.mycommands.commands;

public interface Variable {
    String ENV_VARIBLE = System.getenv("LABA");
    default String getEnvVarible() {
        return ENV_VARIBLE;
    }
}
