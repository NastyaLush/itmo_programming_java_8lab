package test.laba.server.mycommands;

import test.laba.common.responses.Response;
import test.laba.common.responses.ResponseWithError;
import test.laba.common.responses.Responses;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;

@XmlRootElement(name = "Users")
public class UsersHandler {
    private HashMap<String, String> users = new HashMap<>();

    public UsersHandler() {
    }

    public Responses addUser(String login, String password) {
        if (!users.containsKey(login)) {
            users.put(login, password);
            return new Response("user was successfully added");
        }
        return new ResponseWithError("this login is exists, please return request");
    }

    public void setUsers(HashMap<String, String> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "UsersHandler{"
               + "users=" + users
               + '}';
    }

    public HashMap<String, String> getUsers() {
        return users;
    }
}
