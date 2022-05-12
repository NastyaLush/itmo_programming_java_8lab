package test.laba.server.BD;

import test.laba.common.exception.AlreadyExistLogin;
import test.laba.common.responses.RegisterResponse;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class TableOperations {
    protected final String name;
    protected final String dbHost;
    protected final String dbName;
    protected final String dbUser;
    protected final String dbPassword;
    private Connection connection;

    //void add(Object objetct) throws SQLException; // создание связей между таблицами
    public TableOperations(String name, String dbHost, String dbName, String dbUser, String dbPassword) throws SQLException {
        this.name = name;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
        this.dbHost = dbHost;
        this.dbName = dbName;
        reOpenConnection();
        createTable();
    }

    protected void reOpenConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection("jdbc:postgresql://"
                    + dbHost + '/'
                    + dbName, dbUser, dbPassword);
        }
    }

    abstract void createTable() throws SQLException; // создание таблицы

    public Connection getConnection() {
        return connection;
    }

    public void writeContains() throws SQLException {
        reOpenConnection();
        Statement statement = getConnection().createStatement();
        statement.execute("SELECT * FROM " + name);
        ResultSet rs = statement.getResultSet();
        int columns = rs.getMetaData().getColumnCount();
        // Перебор строк с данными
        while (rs.next()) {
            for (int i = 1; i <= columns; i++) {
                System.out.print(rs.getString(i) + "\t");
            }
            System.out.println();
        }
        statement.close();
    }
    public void write(ResultSet rs) throws SQLException {
        int columns = rs.getMetaData().getColumnCount();
        // Перебор строк с данными
        while (rs.next()) {
            for (int i = 1; i <= columns; i++) {
                System.out.print("\"" + rs.getString(i) + "\t\"");
            }
            System.out.println();
        }
    }
    public void clear() throws SQLException {
        reOpenConnection();
        Statement statement = getConnection().createStatement();
        statement.execute("DELETE * FROM " + name);
        statement.close();
    }
    public abstract void add(RegisterResponse registerResponse) throws SQLException, NoSuchAlgorithmException, AlreadyExistLogin;
}
