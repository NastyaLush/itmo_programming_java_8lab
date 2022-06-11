package test.laba.common.responses;


public class RegisterResponse extends BasicResponse {
    private static final long serialVersionUID = 10;

    public RegisterResponse(String login, String password, String command) {
        super(command);
        setLogin(login);
        setPassword(password);
    }

    public RegisterResponse(String command) {
        super(command);
        setLogin(null);
        setPassword(null);

    }

    public void setLoginAndPassword(String newlogin, String password) {
        setLogin(newlogin);
        setPassword(password);
    }


    @Override
    public String toString() {
        return "RegisterResponse{"
                + " login='" + getLogin() + '\''
                + ", password='" + getPassword() + '\''
                + ", command='" + getCommand() + '\''
                + '}';
    }
}
