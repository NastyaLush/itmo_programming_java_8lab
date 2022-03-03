package test.laba.client.console;

public interface Variable {
    String ENV_VARIBLE = System.getenv("LABA");
    default String getEnvVarible() {
        return ENV_VARIBLE;
    }
}
