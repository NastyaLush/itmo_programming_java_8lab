package test.laba.server.BD;


import test.laba.common.dataClasses.Coordinates;
import test.laba.common.dataClasses.Location;
import test.laba.common.dataClasses.Person;
import test.laba.common.dataClasses.Product;
import test.laba.common.dataClasses.UnitOfMeasure;
import test.laba.common.exception.CreateError;
import test.laba.common.exception.VariableException;
import test.laba.common.exception.WrongUsersData;
import test.laba.common.responses.BasicResponse;
import test.laba.common.responses.Response;
import test.laba.server.mycommands.Root;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.logging.Logger;

public class BDManager extends TableOperations {
    private static final Logger LOGGER = Logger.getLogger(BDManager.class.getName());
    private final String name = "products";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public BDManager(String name, String dbHost, String dbName, String dbUser, String dbPassword) throws SQLException {
        super(name, dbHost, dbName, dbUser, dbPassword);
    }


        @Override
        public void createTable() throws SQLException {
        openConnection();
        Statement statement = getConnection().createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS " + this.name + " ("
                + "id serial PRIMARY KEY,"
                + "key BIGINT NOT NULL UNIQUE,"
                + "name varchar(100) NOT NULL,"
                + "coordinate_x INT NOT NULL CHECK(coordinate_x>-233),"
                + "coordinate_y DOUBLE precision NOT NULL,"
                + "creation_date TIMESTAMP NOT NULL,"
                + "price BIGINT NOT NULL CHECK(price>0),"
                + "manufacture_cost INT,"
                + "unit_of_measure varchar(100) NOT NULL CHECK (unit_of_measure IN ('PCS','MILLILITERS','GRAMS')),"
                + "person_name varchar(100),"
                + "person_birthday TIMESTAMP,"
                + "person_height INT CHECK(person_height>0),"
                + "location_x BIGINT,"
                + "location_y INT,"
                + "location_name varchar(100),"
                + "creatorID BIGINT NOT NULL REFERENCES users (id))"

        );

        statement.close();
        LOGGER.info("the table with products was created");
    }


    @Override
    public void clear() throws SQLException {
        Statement statement = getConnection().createStatement();
        synchronized (this) {
            statement.execute("DELETE FROM " + name);
        }
        statement.close();
    }

    public synchronized void removeLower(String login, Set<Long> keys, Root root, BDUsersManager bdUsersManager) {
        keys.forEach(key -> {
            try {
                removeKey(login, key, root, bdUsersManager);
            } catch (WrongUsersData | SQLException e) {
                LOGGER.info("something went wrong because of " + e.getMessage());
            }
        });
    }

    public void removeKey(String login, Long key, Root root, BDUsersManager bdUsersManager) throws SQLException, WrongUsersData {
        if (root.isExistProductWithKey(key)) {
            long id = bdUsersManager.getId(login);

            checkOnKeyAnaIDAndDelete(key, id);
        } else {
            throw new WrongUsersData("this key is not exist");
        }

    }

    public boolean removeLowerKey(BasicResponse response, BDUsersManager bdUsersManager) throws SQLException {
        long id = bdUsersManager.getId(response.getLogin());
        try {
            return checkOnKeyAnaIDAndDelete(((Response) response).getKey(), id);
        } catch (WrongUsersData e) {
            return false;
        }

    }
    public boolean checkOnKeyAnaIDAndDelete(Long key, Long id) throws SQLException, WrongUsersData {
        String deleteQuery = "DELETE FROM " + this.name + " WHERE key = ? AND creatorID = ?";
        String selectQuery = "SELECT * FROM " + this.name + " WHERE key = ? AND creatorID = ?";
        synchronized (this) {
            try (PreparedStatement preparedStatement = getConnection().prepareStatement(selectQuery)) {
                preparedStatement.setLong(1, (key));
                preparedStatement.setLong(2, id);
                preparedStatement.execute();
                if (preparedStatement.getResultSet().next()) {
                    PreparedStatement ps = getConnection().prepareStatement(deleteQuery);
                    ps.setLong(1, (key));
                    ps.setLong(2, id);
                    ps.execute();
                    ps.close();
                } else {
                    throw new WrongUsersData("this object not belongs to you");
                }
            }
            return true;
        }
    }

    public void updateID(Product product, Long key) throws SQLException {
        String query = "UPDATE products SET "
                + "name = ?,"
                + "coordinate_x = ?,"
                + "coordinate_y = ?,"
                + "creation_date = ?,"
                + "price = ?,"
                + "manufacture_cost = ?,"
                + "unit_of_measure = ?,"
                + "person_name = ?,"
                + "person_birthday = ?,"
                + "person_height = ?,"
                + "location_x = ?,"
                + "location_y = ?,"
                + "location_name = ?"
                + "WHERE key = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            int column = preparing(preparedStatement, product, 1);
            preparedStatement.setLong(column, key);
            synchronized (this) {
                preparedStatement.execute();
            }
        }

    }

    public long add(BasicResponse registerResponse, Long key) throws SQLException {
        String query = "INSERT INTO products VALUES ("
                + "    default,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) RETURNING id";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setLong(1, key);
            int column = preparing(preparedStatement, ((Response) registerResponse).getProduct(), 2);
            preparedStatement.setLong(column, ((Response) registerResponse).getProduct().getOwnerID());
            synchronized (this) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    resultSet.next();
                    return resultSet.getLong("id");
                }
            }
        }


    }

    public int preparing(PreparedStatement preparedStatement, Product product, int beginning) throws SQLException {
        int column = beginning;
        preparedStatement.setString(column++, product.getName());
        preparedStatement.setInt(column++, product.getCoordinates().getX());
        preparedStatement.setFloat(column++, product.getCoordinates().getY());
        preparedStatement.setTimestamp(column++, Timestamp.valueOf(product.getCreationDate().format(formatter)));
        preparedStatement.setLong(column++, product.getPrice());
        if (product.getManufactureCost() != null) {
            preparedStatement.setInt(column++, product.getManufactureCost());
        } else {
            preparedStatement.setNull(column++, Types.BIGINT);
        }
        preparedStatement.setString(column++, product.getUnitOfMeasure().toString());
        if (product.getOwner() != null) {
            preparedStatement.setString(column++, product.getOwner().getName());
            preparedStatement.setTimestamp(column++, Timestamp.valueOf(product.getOwner().getBirthday().format(formatter)));
            preparedStatement.setInt(column++, product.getOwner().getHeight());
            preparedStatement.setLong(column++, product.getOwner().getLocation().getX());
            preparedStatement.setInt(column++, product.getOwner().getLocation().getY());
            preparedStatement.setString(column++, product.getOwner().getLocation().getName());
        } else {
            preparedStatement.setNull(column++, Types.VARCHAR);
            preparedStatement.setNull(column++, Types.TIMESTAMP);
            preparedStatement.setNull(column++, Types.INTEGER);
            preparedStatement.setNull(column++, Types.BIGINT);
            preparedStatement.setNull(column++, Types.INTEGER);
            preparedStatement.setNull(column++, Types.VARCHAR);
        }
        return column;
    }

    public Root getProducts() throws SQLException, VariableException, CreateError {

        Statement statement = getConnection().createStatement();
        statement.execute("SELECT * FROM " + name);
        Root root = new Root();
        ResultSet resultSet = statement.getResultSet();
        while (resultSet.next()) {
            root.setProduct(getProduct(resultSet), resultSet.getLong("key"));
        }
        LOGGER.info("the products was added to the collection");
        statement.close();
        return root;
    }

    public Product getProduct(ResultSet resultSet) throws SQLException, VariableException, CreateError {
        Product product = new Product(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                new Coordinates(
                        resultSet.getInt("coordinate_x"),
                        resultSet.getFloat("coordinate_y")),
                resultSet.getLong("price"),
                resultSet.getInt("manufacture_cost"),
                toRightUnitOfMeasure(resultSet.getString("unit_of_measure")));
        if (resultSet.getString("person_name") != null) {
            product.setOwner(new Person(
                    resultSet.getString("person_name"),
                    ZonedDateTime.of(LocalDateTime.parse(resultSet.getString("person_birthday"), formatter), ZoneId.of("Europe/Berlin")),
                    resultSet.getInt("person_height"),
                    new Location(
                            resultSet.getLong("location_x"),
                            resultSet.getInt("location_y"),
                            resultSet.getString("location_name"))));
        }
        product.setCreationDate(ZonedDateTime.of(
                LocalDateTime.parse(resultSet.getString("creation_date"), formatter),
                ZoneId.of("Europe/Berlin")));
        product.setOwnerID(resultSet.getLong("creatorID"));
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
