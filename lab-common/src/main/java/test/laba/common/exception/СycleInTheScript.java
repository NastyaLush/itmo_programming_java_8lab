package test.laba.common.exception;

public class СycleInTheScript extends Exception{
    String message;
    public СycleInTheScript(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
