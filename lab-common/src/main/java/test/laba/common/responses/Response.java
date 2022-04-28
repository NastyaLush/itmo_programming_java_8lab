package test.laba.common.responses;

import test.laba.common.dataClasses.Product;
import test.laba.common.dataClasses.UnitOfMeasure;

import java.io.Serializable;
import java.util.Map;

public class Response implements Serializable {
    private static final long serialVersionUID = 11;
    private Map collection = null;
    private String command;
    private Product product;
    private UnitOfMeasure unitOfMeasure;
    private Long key;
    private String message;
    private boolean flag;


    public Response(String command) {
        this.command = command;
    }

    public Response(String command, UnitOfMeasure unitOfMeasure) {
        this.command = command;
        this.unitOfMeasure = unitOfMeasure;
    }

    public Response(String command, Long key) {
        this.command = command;
        this.key = key;
    }

    public Response(String command, Product product) {
        this.command = command;
        this.product = product;
    }

    public Response(String command, Long key, Product product) {
        this.command = command;
        this.product = product;
        this.key = key;
    }

    public Response(String command, String fileName) {
        this.command = command;
        this.message = fileName;
    }

    public Response(Product product) {
        this.product = product;
    }

    public Response(Map collection, String command) {
        this.collection = collection;
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public Map getCollection() {
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
                  + "collection=" + collection
                + ", command='" + command + '\''
                + ", product=" + product
                + ", unitOfMeasure=" + unitOfMeasure
                + ", key=" + key
                + ", message='" + message + '\''
                + ", flag=" + flag
                + '}';
    }
}
