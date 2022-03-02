package test.laba.client.mainClasses;

import test.laba.client.console.Console;

import javax.xml.bind.annotation.*;
import java.time.ZonedDateTime;
import java.util.*;

@XmlRootElement(name = "root")
@XmlAccessorType(XmlAccessType.FIELD)
public class Root {
    @XmlElement(name="root")
    private HashMap<Long, Product> products = new HashMap<>();
    private ZonedDateTime dateOfLastModification;

    public Root(){
        this.dateOfLastModification=ZonedDateTime.now();
    }
    public Root(HashMap<Long, Product> products){
        this.products=products;
    }

    public HashMap<Long, Product> getProducts() {
        return products;
    }
    public ZonedDateTime getDateOfLastModification() {
        return dateOfLastModification;
    }
    public Long getKeyOnID(Long id) {
        for (Map.Entry<Long, Product> productEntry: products.entrySet()) {
            if (productEntry.getValue().getId() == id) {
                return productEntry.getKey();
            }
        }
        return null;
    }

    public void setProducts(HashMap<Long, Product> products) {
        this.products = products;
    }
    @XmlElement
    public void setProduct( Product product) {
        products.put(createKey(), product);
        dateOfLastModification = ZonedDateTime.now();

    }

    public boolean containsID(Long id) {
        for (Map.Entry<Long, Product> productEntry: products.entrySet()){
            if (productEntry.getValue().getId() == id) {
                return true;
            }
        }
        return false;
    }

    private Long createKey(){
        long key=0;
        while (getProducts().containsKey(key)) {
            key++;
        }
        return key;
    }

    public void clear(){
        products.clear();
    }
    public String infoAboutCollection(){
        return "Class of collection: " + getProducts().getClass()
                + "\nDate of initialization: " + getDateOfLastModification()
                + "\nNumber of elements: " + getProducts().size();
    }
    public void removeAnyByUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        for (HashMap.Entry<Long, Product> prod: products.entrySet()) {
            if (unitOfMeasure == prod.getValue().getUnitOfMeasure()) {
                products.remove(prod.getKey());
                break;
            }
        }
    }
    public void remove(Long key){
        remove(key);
    }
    public void removeIfLess(Product product) {
        Iterator<Map.Entry<Long, Product>> itr = products.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<Long, Product> entry = itr.next();
            if (product.compareTo(entry.getValue()) > 0) {
                itr.remove();
            }
        }
    }
    public void removeIfKeyLess(Long key){
        Iterator<Map.Entry<Long, Product>> itr = products.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<Long, Product> entry = itr.next();
            if (key > entry.getKey()) {
                itr.remove();
            }
        }
    }
    public Product getProductByKey(Long key){
        return products.get(key);
    }


    @Override
    public String toString() {
        return "Root{" +
                "products=" + products +
                ", dateOfLastModification=" + dateOfLastModification +
                '}';
    }

    public String showCollection() {
        String s = null;
        s += "Products: ";
        for (Map.Entry<Long, Product> entry : products.entrySet()) {
            s += "\n" + entry.getValue() ;
        }
        return s;
    }
    public void toStringWitnKey(Console console) {
        console.print("Products: \n");
        for (Map.Entry<Long, Product> entry : products.entrySet()) {
            console.print(entry.getKey() + " " + entry.getValue());
        }
    }
}
