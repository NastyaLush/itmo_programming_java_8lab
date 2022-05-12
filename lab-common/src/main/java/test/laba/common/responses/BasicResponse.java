package test.laba.common.responses;

import java.io.Serializable;

public abstract class BasicResponse implements Serializable {
    private static final long serialVersionUID = 11;
    private String command;
    private String login;
    private String password;

    public BasicResponse(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
