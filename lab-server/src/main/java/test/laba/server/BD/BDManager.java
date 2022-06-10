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
    private static final int PARAMETER_OFFSET_KEY = 1;
    private static final int PARAMETER_OFFSET_NAME = 1;
    private static final int PARAMETER_OFFSET_COORDINATES_X = 2;
    private static final int PARAMETER_OFFSET_COORDINATES_Y = 3;
    private static final int PARAMETER_OFFSET_TIMESTAMP = 4;
    private static final int PARAMETER_OFFSET_PRICE = 5;
    private static final int PARAMETER_OFFSET_MANUFACTURE_COST = 6;
    private static final int PARAMETER_OFFSET_UNIT_OF_MEASURE = 7;
    private static final int PARAMETER_OFFSET_PERSON_NAME = 8;
    private static final int PARAMETER_OFFSET_PERSON_BIRTHDAY = 9;
    private static final int PARAMETER_OFFSET_PERSON_HEIGHT = 10;
    private static final int PARAMETER_OFFSET_LOCATION_X = 11;
    private static final int PARAMETER_OFFSET_LOCATION_Y = 12;
    private static final int PARAMETER_OFFSET_LOCATION_NAME = 13;


    private final String name = "products";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public BDManager(String name, String dbHost, String dbName, String dbUser, String dbPassword, String bdPort) throws SQLException {
        super(name, dbHost, dbName, dbUser, dbPassword, bdPort);
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


    public void clear(Long id) throws SQLException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("DELETE FROM " + name + " WHERE creatorID=?");
        preparedStatement.setLong(1, id);
        synchronized (this) {
            preparedStatement.execute();
        }
        writeContains();
        preparedStatement.close();
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

    public boolean removeKey(String login, Long key, Root root, BDUsersManager bdUsersManager) throws SQLException, WrongUsersData {
        if (root.isExistProductWithKey(key)) {
            long id = bdUsersManager.getId(login);
            //System.out.println(id);
            String deleteQuery = "DELETE FROM " + this.name + " WHERE key = ? AND creatorID = ?";
            String selectQuery = "SELECT * FROM " + this.name + " WHERE key = ? AND creatorID = ?";
            return checkOnKeyAnaIDAndDelete(key, id, deleteQuery, selectQuery);
        } else {
            throw new WrongUsersData("this key is not exist");
        }

    }

    public boolean removeLowerKey(BasicResponse response, BDUsersManager bdUsersManager) throws SQLException {
        long id = bdUsersManager.getId(response.getLogin());
        try {
            String deleteQuery = "DELETE FROM " + this.name + " WHERE key <= ? AND creatorID = ?";
            String selectQuery = "SELECT * FROM " + this.name + " WHERE key <= ? AND creatorID = ?";
            return checkOnKeyAnaIDAndDelete(((Response) response).getKeyOrID(), id, deleteQuery, selectQuery);
        } catch (WrongUsersData e) {
            return false;
        }

    }

    public boolean checkOnKeyAnaIDAndDelete(Long key, Long id, String deleteQuery, String selectQuery) throws SQLException, WrongUsersData {
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
            int column = preparing(preparedStatement, product, 0);
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
            preparedStatement.setLong(PARAMETER_OFFSET_KEY, key);
            int column = preparing(preparedStatement, ((Response) registerResponse).getProduct(), 1);
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
        preparedStatement.setString(PARAMETER_OFFSET_NAME + beginning, product.getName());
        preparedStatement.setInt(PARAMETER_OFFSET_COORDINATES_X + beginning, product.getCoordinates().getX());
        preparedStatement.setFloat(PARAMETER_OFFSET_COORDINATES_Y + beginning, product.getCoordinates().getY());
        preparedStatement.setTimestamp(PARAMETER_OFFSET_TIMESTAMP + beginning, Timestamp.valueOf(product.getCreationDate().format(formatter)));
        preparedStatement.setLong(PARAMETER_OFFSET_PRICE + beginning, product.getPrice());
        if (product.getManufactureCost() != null) {
            preparedStatement.setInt(PARAMETER_OFFSET_MANUFACTURE_COST + beginning, product.getManufactureCost());
        } else {
            preparedStatement.setNull(PARAMETER_OFFSET_MANUFACTURE_COST + beginning, Types.BIGINT);
        }
        preparedStatement.setString(PARAMETER_OFFSET_UNIT_OF_MEASURE + beginning, product.getUnitOfMeasure().toString());
        if (product.getOwner() != null) {
            preparedStatement.setString(PARAMETER_OFFSET_PERSON_NAME + beginning, product.getOwner().getName());
            preparedStatement.setTimestamp(PARAMETER_OFFSET_PERSON_BIRTHDAY + beginning, Timestamp.valueOf(product.getOwner().getBirthday().format(formatter)));
            preparedStatement.setInt(PARAMETER_OFFSET_PERSON_HEIGHT + beginning, product.getOwner().getHeight());
            preparedStatement.setLong(PARAMETER_OFFSET_LOCATION_X + beginning, product.getOwner().getLocation().getX());
            preparedStatement.setInt(PARAMETER_OFFSET_LOCATION_Y + beginning, product.getOwner().getLocation().getY());
            preparedStatement.setString(PARAMETER_OFFSET_LOCATION_NAME + beginning, product.getOwner().getLocation().getName());
        } else {
            preparedStatement.setNull(PARAMETER_OFFSET_PERSON_NAME + beginning, Types.VARCHAR);
            preparedStatement.setNull(PARAMETER_OFFSET_PERSON_BIRTHDAY + beginning, Types.TIMESTAMP);
            preparedStatement.setNull(PARAMETER_OFFSET_PERSON_HEIGHT + beginning, Types.INTEGER);
            preparedStatement.setNull(PARAMETER_OFFSET_LOCATION_X + beginning, Types.BIGINT);
            preparedStatement.setNull(PARAMETER_OFFSET_LOCATION_Y + beginning, Types.INTEGER);
            preparedStatement.setNull(PARAMETER_OFFSET_LOCATION_NAME + beginning, Types.VARCHAR);
        }
        return PARAMETER_OFFSET_LOCATION_NAME + beginning + 1;
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
