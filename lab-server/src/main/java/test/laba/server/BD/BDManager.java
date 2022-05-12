package test.laba.server.BD;


import test.laba.common.dataClasses.Coordinates;
import test.laba.common.dataClasses.Location;
import test.laba.common.dataClasses.Person;
import test.laba.common.dataClasses.Product;
import test.laba.common.dataClasses.UnitOfMeasure;
import test.laba.common.exception.AlreadyExistLogin;
import test.laba.common.exception.CreateError;
import test.laba.common.exception.VariableException;
import test.laba.common.responses.BasicResponse;
import test.laba.common.responses.RegisterResponse;
import test.laba.common.responses.Response;
import test.laba.server.mycommands.Root;


import java.security.NoSuchAlgorithmException;
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
import java.util.logging.Logger;

public class BDManager extends TableOperations {
    private static final Logger LOGGER = Logger.getLogger(BDManager.class.getName());
    private final String name = "products";
    private final DateTimeFormatter forrmater = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public BDManager(String name, String dbHost, String dbName, String dbUser, String dbPassword) throws SQLException {
        super(name, dbHost, dbName, dbUser, dbPassword);
    }


    @Override
    public void createTable() throws SQLException {
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
    public void add(RegisterResponse registerResponse) throws SQLException, NoSuchAlgorithmException, AlreadyExistLogin {

    }


    public long add(BasicResponse registerResponse, Long key) throws SQLException {
        reOpenConnection();
        String query = "INSERT INTO products VALUES ("
                + "    default,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) RETURNING id";
        PreparedStatement preparedStatement = getConnection().prepareStatement(query);
        preparing(preparedStatement, (Response) registerResponse, key);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            resultSet.next();
            writeContains();
            System.out.println("jkl;");
            return resultSet.getLong("id");
        }


    }

    public void preparing(PreparedStatement preparedStatement, Response response, Long key) throws SQLException {
        Product product = response.getProduct();
        int i = 1;
        preparedStatement.setLong(i++, key);
        preparedStatement.setString(i++, product.getName());
        preparedStatement.setInt(i++, product.getCoordinates().getX());
        preparedStatement.setFloat(i++, product.getCoordinates().getY());
        preparedStatement.setTimestamp(i++, Timestamp.valueOf(product.getCreationDate().format(forrmater)));
        preparedStatement.setLong(i++, product.getPrice());
        if (product.getManufactureCost() != null) {
            preparedStatement.setInt(i++, product.getManufactureCost());
        } else {
            preparedStatement.setNull(i++, Types.BIGINT);
        }
        preparedStatement.setString(i++, product.getUnitOfMeasure().toString());
        if (product.getOwner() != null) {
            preparedStatement.setString(i++, product.getOwner().getName());
            preparedStatement.setTimestamp(i++, Timestamp.valueOf(product.getOwner().getBirthday().format(forrmater)));
            preparedStatement.setInt(i++, product.getOwner().getHeight());
            preparedStatement.setLong(i++, product.getOwner().getLocation().getX());
            preparedStatement.setInt(i++, product.getOwner().getLocation().getY());
            preparedStatement.setString(i++, product.getOwner().getLocation().getName());
        } else {
            preparedStatement.setNull(i++, Types.VARCHAR);
            preparedStatement.setNull(i++, Types.TIMESTAMP);
            preparedStatement.setNull(i++, Types.INTEGER);
            preparedStatement.setNull(i++, Types.BIGINT);
            preparedStatement.setNull(i++, Types.INTEGER);
            preparedStatement.setNull(i++, Types.VARCHAR);
        }
        preparedStatement.setLong(i++, product.getOwnerID());
    }

    public Root getProducts() throws SQLException, VariableException, CreateError {
        reOpenConnection();
        Statement statement = getConnection().createStatement();
        statement.execute("SELECT * FROM " + name);
        Root root = new Root();
        ResultSet resultSet = statement.getResultSet();
        while (resultSet.next()) {
            root.setProduct(getProduct(resultSet), resultSet.getLong("key"));
        }
        LOGGER.info("the products was added to the collection");
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
                    ZonedDateTime.of(LocalDateTime.parse(resultSet.getString("person_birthday"), forrmater), ZoneId.of("Europe/Berlin")),
                    resultSet.getInt("person_height"),
                    new Location(
                            resultSet.getLong("location_x"),
                            resultSet.getInt("location_y"),
                            resultSet.getString("location_name"))));
        }
        product.setCreationDate(ZonedDateTime.of(
                LocalDateTime.parse(resultSet.getString("creation_date"), forrmater),
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
