package test.laba.common.responses;

import java.io.Serializable;

public abstract class BasicResponse implements Serializable {
    private static final long serialVersionUID = 10;
    private final String command;
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

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
