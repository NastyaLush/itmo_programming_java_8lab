package test.laba.common.util;

import test.laba.common.IO.Colors;
import test.laba.common.dataClasses.Product;
import test.laba.common.dataClasses.UnitOfMeasure;

import java.io.BufferedReader;
import java.io.File;
import java.io.Serializable;
import java.util.Map;

public class Response implements Serializable {
    private static final long serialVersionUID = 11;
    private final Map collection = null;
    private String command;
    private Product product;
    private UnitOfMeasure unitOfMeasure;
    private Long key;
    private String message;
    private boolean flag;
    private BufferedReader bufferedReader;
    private File file;


    public Response(String command){
        this.command = command;
    }
    public Response (String command, UnitOfMeasure unitOfMeasure){
        this.command = command;
        this.unitOfMeasure = unitOfMeasure;
    }
    public Response (String command, Long key){
        this.command = command;
        this.key = key;
    }
    public Response (String command, Product product){
        this.command = command;
        this.product = product;
    }
    public Response (String command, Long key, Product product){
        this.command = command;
        this.product = product;
        this.key = key;
    }
    public Response (String command, String fileName){
        this.command = command;
        this.message = fileName;
    }
    public Response (Product product){
        this.product = product;
    }

    public String getCommand() {
        return command;
    }
    public Map getCollection(){
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

    public File getFile() {
        return file;
    }
}
