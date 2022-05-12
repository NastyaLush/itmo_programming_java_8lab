package test.laba.common.responses;


public class RegisterResponse extends Responses {
    private static final long serialVersionUID = 11;
    protected final String login;
    protected final String password;

    public RegisterResponse(String login, String password, String command) {
        super(command);
        this.login = login;
        this.password = password;
    }

    public RegisterResponse(String command) {
        super(command);
        this.login = null;
        this.password = null;

    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "RegisterResponse{"
                + " login='" + login + '\''
                + ", password='" + password + '\''
                + ", command='" + getCommand() + '\''
                + '}';
    }
}
