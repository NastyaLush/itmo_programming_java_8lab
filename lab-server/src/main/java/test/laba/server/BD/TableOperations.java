package test.laba.server.BD;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public abstract class TableOperations {
    private static final Logger LOGGER = Logger.getLogger(TableOperations.class.getName());
    protected final String name;
    protected final String dbHost;
    protected final String dbName;
    protected final String dbUser;
    protected final String dbPassword;
    protected final String dbport;
    private Connection connection;

    public TableOperations(String name, String dbHost, String dbName, String dbUser, String dbPassword, String dbport) {
        this.name = name;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
        this.dbHost = dbHost;
        this.dbName = dbName;
        this.dbport = dbport;
    }

    protected void openConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection("jdbc:postgresql://"
                    + dbHost + ":" + dbport + '/'
                    + dbName, dbUser, dbPassword);
            System.out.println();
        }
    }

    abstract void createTable() throws SQLException; // создание таблицы

    public Connection getConnection() {
        return connection;
    }

    public void writeContains() throws SQLException {
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
        Statement statement = getConnection().createStatement();
        statement.execute("DELETE * FROM " + name);
        statement.close();
        LOGGER.info("the table " + name + " was cleared");
    }

    public void delete() throws SQLException {
        Statement statement = getConnection().createStatement();
        statement.execute("DROP TABLE " + name + " CASCADE");
        statement.close();
        LOGGER.info("the table " + name + " was deleted");
    }
}
