package test.laba.client.mainClasses;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.TreeMap;

public class Root {
    private HashMap<Long, Product> products = new HashMap<>();
    private ZonedDateTime DateOfLastModification;




    @Override
    public String toString() {
        return "Root{" +
                "products=" + products +
                '}';
    }
    public HashMap<Long, Product> getProducts() {
        return products;
    }
    public ZonedDateTime getDateOfLastModification() {
        return DateOfLastModification;
    }

    public void setProducts(HashMap<Long, Product> products) {
        this.products = products;
    }
    public void setProduct(long id, Product product) {
        products.put(id, product);
        DateOfLastModification=ZonedDateTime.now();
        sort();
    }
    public void sort(){
        TreeMap<Long, Product> sortedMap = new TreeMap<>(products);
        products=new HashMap<>(sortedMap);
    }
}