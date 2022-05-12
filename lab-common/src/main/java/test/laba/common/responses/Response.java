package test.laba.common.responses;

import test.laba.common.dataClasses.Product;
import test.laba.common.dataClasses.UnitOfMeasure;
import test.laba.common.util.Values;

import java.util.Map;

public class Response extends RegisterResponse {
    private static final long serialVersionUID = 11;
    private Map<String, Values> collection = null;
    private Product product;
    private UnitOfMeasure unitOfMeasure;
    private Long key;
    private String message;
    private boolean flag;



    public Response(String login, String password, String command, UnitOfMeasure unitOfMeasure) {
        super(login, password, command);
        this.unitOfMeasure = unitOfMeasure;
    }

    public Response(String login, String password, String command, Long key) {
        super(login, password, command);
        this.key = key;
    }


    public Response(String login, String password, String command, Product product) {
        super(login, password, command);
        this.product = product;
    }

    public Response(String login, String password, String command, Long key, Product product) {
        super(login, password, command);
        this.product = product;
        this.key = key;
    }

    public Response(String login, String password, String command, String fileName) {
        super(login, password, command);
        this.message = fileName;
    }

    public Response(Product product) {
        super("sent product");
        this.product = product;
    }

    public Response(Map<String, Values> collection, String command) {
        super(command);
        this.collection = collection;
    }

    public Response(String login, String password, String command) {
        super(login, password, command);
    }

    public Response(String command) {
        super(command);
    }

    public Response(String message, Product product) {
        super(message);
        this.product = product;
    }

    public Response(String command, String arg) {
        super(command);
        this.message = arg;
    }


    public Map<String, Values> getCollection() {
        return collection;
    }

    public Product getProduct() {
        return product;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public Long getKey() {
        return key;
    }

    public String getMessage() {
        return message;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    @Override
    public String toString() {
        return "Response{"
                + " login='" + login + '\''
                + ", password='" + password + '\''
                + ", collection=" + collection
                + ", product=" + product
                + ", unitOfMeasure=" + unitOfMeasure
                + ", key=" + key
                + ", message='" + message + '\''
                + ", flag=" + flag
                + ", command='" + getCommand() + '\''
                + '}';
    }
}
