package test.laba.common.responses;


public class RegisterResponse extends BasicResponse {
    private static final long serialVersionUID = 10;
    protected  String login;
    protected  String password;

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

    public void setLoginAndPassword(String login, String password) {
        this.login = login;
        this.password = password;
    }


    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }


    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
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
