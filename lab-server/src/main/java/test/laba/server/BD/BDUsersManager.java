package test.laba.server.BD;

import org.apache.commons.lang3.RandomStringUtils;
import test.laba.common.exception.AlreadyExistLogin;
import test.laba.common.exception.WrongUsersData;
import test.laba.common.responses.RegisterResponse;
import test.laba.server.Encryption;

import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BDUsersManager extends TableOperations {
    private static final Logger LOGGER = Logger.getLogger(BDManager.class.getName());
    private final String name = "users";
    private final String paper = "H@*#admkl";
    private final int maxCountOfHash = 10;
    private final int maxLengthOfSalt = 10;

    public BDUsersManager(String name, String dbHost, String dbName, String dbUser, String dbPassword) throws SQLException {
        super(name, dbHost, dbName, dbUser, dbPassword);
        LOGGER.setLevel(Level.FINEST);
    }


    @Override
    public void createTable() throws SQLException {
        openConnection();
        Statement statement = getConnection().createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS " + this.name + " ("
                + "id serial PRIMARY KEY,"
                + "login varchar(200) UNIQUE NOT NULL,"
                + "password varchar(50) NOT NULL, "
                + "salt varchar(100) UNIQUE NOT NULL,"
                + "count_hash bigint not null"
                + ")"

        );

        statement.close();
        LOGGER.info("the table with users was created");
    }


    public void add(RegisterResponse registerResponse) throws SQLException, NoSuchAlgorithmException, AlreadyExistLogin {
        PreparedStatement statement = getConnection().prepareStatement("SELECT password FROM " + this.name + " WHERE login = ? LIMIT 1");
        statement.setString(1, registerResponse.getLogin());
        statement.execute();
        ResultSet resultSet = statement.getResultSet();
        String salt = RandomStringUtils.randomAlphanumeric(maxLengthOfSalt);
        long count = Math.round(Math.random() * maxCountOfHash);
        synchronized (this) {
            int column = 0;
            if (!resultSet.next()) {
                statement = getConnection().prepareStatement("INSERT INTO " + this.name + "(login,password,salt,count_hash)" + " VALUES (?,?,?,?)" + " RETURNING id");
                statement.setString(++column, registerResponse.getLogin());
                statement.setString(++column, Encryption.coding(paper + registerResponse.getPassword() + salt, count));
                statement.setString(++column, salt);
                statement.setLong(++column, count);
                statement.execute();
            } else {
                throw new AlreadyExistLogin("this login is already exist");
            }
        }
        //writeContains();
        statement.close();
    }

    public boolean isAuthorized(String login, String password) throws SQLException, WrongUsersData, NoSuchAlgorithmException {
        LOGGER.fine("the authorised method started");


        try (PreparedStatement statement = getConnection().prepareStatement("SELECT password, salt, count_hash FROM " + this.name + " WHERE login = ? LIMIT 1")) {
            statement.setString(1, login);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                if (resultSet.getString("password").trim().equals(
                        Encryption.coding(paper
                                        + password
                                        + resultSet.getString("salt").trim(),
                                resultSet.getLong("count_hash")))) {
                    return true;
                } else {
                    throw new WrongUsersData("the " + login + " has another password");
                }
            } else {
                throw new WrongUsersData("this login is not exist");
            }
        }

    }

    public Long getId(String login) throws SQLException {
        try (PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM " + this.name + " WHERE login = ? LIMIT 1")) {
            statement.setString(1, login);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();
            return resultSet.getLong(1);
        }
    }

}
