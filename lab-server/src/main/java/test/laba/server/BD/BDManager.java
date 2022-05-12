package test.laba.server.BD;


import test.laba.common.dataClasses.Coordinates;
import test.laba.common.dataClasses.Location;
import test.laba.common.dataClasses.Person;
import test.laba.common.dataClasses.Product;
import test.laba.common.dataClasses.UnitOfMeasure;
import test.laba.common.exception.CreateError;
import test.laba.common.exception.VariableException;
import test.laba.server.mycommands.Root;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class BDManager extends TableOperations {
    private static final Logger LOGGER = Logger.getLogger(BDManager.class.getName());
    private final String name = "products";

    public BDManager(String name, String dbHost, String dbName, String dbUser, String dbPassword) throws SQLException {
        super(name, dbHost, dbName, dbUser, dbPassword);
    }


    @Override
    public void createTable() throws SQLException {
        Statement statement = getConnection().createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS " + this.name + " ("
                + "id serial PRIMARY KEY,"
                + "name varchar(100) NOT NULL,"
                + "coordinate_x INT NOT NULL CHECK(coordinate_x>-233),"
                + "coordinate_y DOUBLE NOT NULL,"
                + "creation_date DATETIME NOT NULL,"
                + "price BIGINT NOT NULL CHECK(price>0),"
                + "manufacture_cost INT,"
                + "unit_of_measure varchar(100) NOT NULL CHECK (unit_of_measure IN ('PCS','MILLILITERS','GRAMS')),"
                + "person_name varchar(100) NOT NULL,"
                + "person_birthday DATETIME NOT NULL,"
                + "person_height INT NOT NULL CHECK(person_height>0),"
                + "location_x BIGINT,"
                + "location_y INT,"
                + "location_name varchar(100) NOT NULL,"
                + "creator varchar(100) NOT NULL,"
                + " FOREIGN KEY (creator)  REFERENCES users (login) ON DELETE NO ACTION)"

        );

        statement.close();
        LOGGER.info("the table with products was created");
    }

    public Root getProducts() throws SQLException, VariableException, CreateError {
        reOpenConnection();
        Statement statement = getConnection().createStatement();
        statement.execute("SELECT * FROM " + name);
        Root root = new Root();
        ResultSet resultSet = statement.getResultSet();
        while (resultSet.next()) {
            root.setProduct(getProduct(resultSet));
        }
        LOGGER.info("the products was added to the collection");
        return root;
    }

    public Product getProduct(ResultSet resultSet) throws SQLException, VariableException, CreateError {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        Product product = new Product(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                new Coordinates(
                        resultSet.getInt("coordinate_x"),
                        resultSet.getFloat("coordinate_y")),
                resultSet.getLong("price"),
                resultSet.getInt("manufacture_cost"),
                toRightUnitOfMeasure(resultSet.getString("unit_of_measure")),
                new Person(
                        resultSet.getString("person_name"),
                        ZonedDateTime.of(LocalDateTime.parse(resultSet.getString("person_birthday"), formatter), ZoneId.of("Europe/Berlin")),
                        resultSet.getInt("person_height"),
                        new Location(
                                resultSet.getLong("location_x"),
                                resultSet.getInt("location_y"),
                                resultSet.getString("location_name"))));
        product.setCreationDate(ZonedDateTime.of(
                LocalDateTime.parse(resultSet.getString("creation_date"), formatter),
                ZoneId.of("Europe/Berlin")));
        return product;
    }

    public static UnitOfMeasure toRightUnitOfMeasure(String unit) throws VariableException {
        try {
            return UnitOfMeasure.valueOf(unit.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new VariableException("Can't parse to Unit Of Measure, please chose one of: " + "PCS, MILLILITERS, GRAMS");
        }
    }


}
