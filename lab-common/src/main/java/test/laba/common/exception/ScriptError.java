package test.laba.common.exception;
public class ScriptError extends RuntimeException {
    public ScriptError(String message) {
        super(message);
    }
    public ScriptError() {
        super();
    }

}
