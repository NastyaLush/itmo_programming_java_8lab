package test.laba.server.BD;

import test.laba.common.exception.WrongUsersData;
import test.laba.common.responses.RegisterResponse;
import test.laba.common.util.Encryption;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BDUsersManager extends TableOperations {
    private static final Logger LOGGER = Logger.getLogger(BDManager.class.getName());
    private final String name = "users";

    public BDUsersManager(String name, String dbHost, String dbName, String dbUser, String dbPassword) throws SQLException {
        super(name, dbHost, dbName, dbUser, dbPassword);
        LOGGER.setLevel(Level.FINEST);
    }


    @Override
    public void createTable() throws SQLException {
        Statement statement = getConnection().createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS " + this.name + " ("
                + "login varchar(100) UNIQUE NOT NULL,"
                + "password varchar(50) NOT NULL )"

        );

        statement.close();
        LOGGER.info("the table with users was created");
    }


    public void add(RegisterResponse registerResponse) throws SQLException {
        reOpenConnection();
        Statement statement = getConnection().createStatement();
        statement.execute("INSERT INTO " + this.name + " VALUES ('" + registerResponse.getLogin() + "','" + Encryption.coding(registerResponse.getPassword()) + "')");
        statement.close();
    }

    public boolean isAuthorisated(String login, String password) throws SQLException, WrongUsersData {
        LOGGER.fine("the authorised method started");
        reOpenConnection();
        Statement statement = getConnection().createStatement();
        try {
            statement.execute("SELECT password FROM " + this.name + " WHERE login = '" + login + "'" + "LIMIT 1");
            ResultSet resultSet = statement.getResultSet();
            //write(resultSet);
            if (resultSet.next()) {
                LOGGER.finest("the user authorised: " + resultSet.getString("password").equals(Encryption.coding(password)));
                if (resultSet.getString(1).trim().equals(Encryption.coding(password))) {
                    return true;
                } else {
                    throw new WrongUsersData("the " + login + " has another password");
                }
            } else {
                throw new WrongUsersData("this login is not exist");
            }
        } finally {
            statement.close();
        }

    }


}
