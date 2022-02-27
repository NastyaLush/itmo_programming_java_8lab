package test.laba.client.mainClasses;

import test.laba.client.console.Console;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class Root {
    private HashMap<Long, Product> products = new LinkedHashMap<>();
    private ZonedDateTime dateOfLastModification;




    public void toString(Console console) {
        console.print("Products: \n");
        for (Map.Entry<Long, Product> entry : products.entrySet()) {
            console.print(entry.getValue());
        }
    }
    public void toStringWitnKey(Console console) {
        console.print("Products: \n");
        for (Map.Entry<Long, Product> entry : products.entrySet()) {
            console.print(entry.getKey() + " " + entry.getValue());
        }
    }
    public HashMap<Long, Product> getProducts() {
        return products;
    }
    public ZonedDateTime getDateOfLastModification() {
        return dateOfLastModification;
    }

    public void setProducts(HashMap<Long, Product> products) {
        this.products = products;
    }
    public void setProduct(long id, Product product) {
        products.put(id, product);
        dateOfLastModification = ZonedDateTime.now();
        sort();
    }
    public void sort() {
        TreeMap<Long, Product> sortedMap = new TreeMap<>(products);
        products = new HashMap<>(sortedMap);
    }

    public boolean containsID(Long id) {
        for (Map.Entry<Long, Product> productEntry: products.entrySet()) {
            if (productEntry.getValue().getId() == id) {
                return true;
            }
        }
        return false;
    }
    public Long getKeyOnID(Long id) {
        for (Map.Entry<Long, Product> productEntry: products.entrySet()) {
            if (productEntry.getValue().getId() == id) {
                return productEntry.getKey();
            }
        }
        return null;
    }

}
