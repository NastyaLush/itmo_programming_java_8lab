package test.laba.client.mainClasses;

import test.laba.client.console.Console;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@XmlRootElement(name = "root")
@XmlAccessorType(XmlAccessType.FIELD)
public class Root {
    @XmlElement(name = "root")
    private HashMap<Long, Product> products = new HashMap<>();
    private ZonedDateTime dateOfLastModification;

    public Root() {
        this.dateOfLastModification = ZonedDateTime.now();
    }
    public Root(HashMap<Long, Product> products) {
        this.products = products;
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
    public void setProduct(Product product) {
        products.put(createKey(), product);
        dateOfLastModification = ZonedDateTime.now();
    }
    public void setProductWithKey(Product product, Long key) {
        products.put(key, product);
        dateOfLastModification = ZonedDateTime.now();
    }
    public boolean containsID(Long id) {
        for (Map.Entry<Long, Product> productEntry: products.entrySet()) {
            if (productEntry.getValue().getId() == id) {
                return true;
            }
        }
        return false;
    }
    private Long createKey() {
        long key = 0;
        while (getProducts().containsKey(key)) {
            key++;
        }
        return key;
    }

    public void clear() {
        products.clear();
    }
    public String infoAboutCollection() {
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
    public void removeIfLess(Product product) {
        products.entrySet().removeIf(entry -> product.compareTo(entry.getValue()) > 0);
    }
    public void removeIfKeyLess(Long key) {
        products.entrySet().removeIf(entry -> key > entry.getKey());
    }
    public Product getProductByKey(Long key) {
        return products.get(key);
    }
    public void remove(Long deleteKey) {
        products.remove(deleteKey);
    }


    @Override
    public String toString() {
        return "Root{"
                + "products=" + products
                + ", dateOfLastModification=" + dateOfLastModification + '}';
    }

    public String showCollection() {
        StringBuilder s = new StringBuilder("Products: ");
        for (Map.Entry<Long, Product> entry : products.entrySet()) {
            s.append("\n").append(entry.getValue());
        }
        return s.toString();
    }
    public void toStringWithKey(Console console) {
        console.print("Products: \n");
        for (Map.Entry<Long, Product> entry : products.entrySet()) {
            console.print(entry.getKey() + " " + entry.getValue());
        }
    }
    public int averageOfManufactureCost() {
        Integer answer = 0;
        try {
            for (HashMap.Entry<Long, Product> prod : products.entrySet()) {
                answer += prod.getValue().getManufactureCost();
            }
        } catch (NullPointerException e) {
            answer += 0;
        }
        return answer;
    }
    public HashMap<Long, Long> groupCountingByPrice() {
        HashMap<Long, Long> countingByPrice = new LinkedHashMap<>();
        long productsCount;
        for (HashMap.Entry<Long, Product> entry : products.entrySet()) {
            try {
                productsCount = countingByPrice.get(entry.getValue().getPrice());
            } catch (NullPointerException e) {
                productsCount = 0;
            }
            countingByPrice.put(entry.getValue().getPrice(), ++productsCount);
        }
        return countingByPrice;

    }
}
