package test.laba.common.responses;

import test.laba.common.dataClasses.Product;
import test.laba.common.dataClasses.UnitOfMeasure;
import test.laba.common.util.Values;

import java.util.HashMap;
import java.util.Map;

public class Response extends RegisterResponse {
    private static final long serialVersionUID = 10;
    private boolean addToHistory = true;
    private Map<String, Values> collection = null;
    private Product product;
    private UnitOfMeasure unitOfMeasure;
    private Long keyOrID;
    private String message;
    private boolean flagUdateID;
    private HashMap<Long,Product> productHashMap;



    public Response(String login, String password, String command, UnitOfMeasure unitOfMeasure) {
        super(login, password, command);
        this.unitOfMeasure = unitOfMeasure;
    }

    public Response(String login, String password, String command, Long keyOrID) {
        super(login, password, command);
        this.keyOrID = keyOrID;
    }


    public Response(String login, String password, String command, Product product) {
        super(login, password, command);
        this.product = product;
    }

    public Response(String login, String password, String command, Long keyOrID, Product product) {
        super(login, password, command);
        this.product = product;
        this.keyOrID = keyOrID;
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

    public Long getKeyOrID() {
        return keyOrID;
    }

    public String getMessage() {
        return message;
    }

    public void setFlagUdateID(boolean flagUdateID) {
        this.flagUdateID = flagUdateID;
    }

    public void setProductHashMap(HashMap<Long, Product> productHashMap) {
        this.productHashMap = productHashMap;
    }

    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public HashMap<Long, Product> getProductHashMap() {
        return productHashMap;
    }


    public void setProduct(Product product) {
        this.product = product;
    }

    public void setKeyOrID(Long keyOrID) {
        this.keyOrID = keyOrID;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isAddToHistory() {
        return addToHistory;
    }

    public void setAddToHistory(boolean addToHistory) {
        this.addToHistory = addToHistory;
    }

    @Override
    public String toString() {
        return "Response{"
                + " login='" + login + '\''
                + ", password='" + password + '\''
                + ", collection=" + collection
                + ", product=" + product
                + ", unitOfMeasure=" + unitOfMeasure
                + ", key=" + keyOrID
                + ", message='" + message + '\''
                + ", flag=" + flagUdateID
                + ", command='" + getCommand() + '\''
                + ", products=" + productHashMap
                + '}';
    }

    public boolean isFlagUdateID() {
        return flagUdateID;
    }
}
