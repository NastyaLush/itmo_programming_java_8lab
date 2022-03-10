package test.laba.client.dataClasses;

import test.laba.client.console.Console;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * the collection class, contains fields: collection and date of creation
 */
@XmlRootElement(name = "root")
@XmlAccessorType(XmlAccessType.FIELD)
public class Root {
    @XmlElement(name = "root")
    private HashMap<Long, Product> products = new HashMap<>();
    private ZonedDateTime dateOfCreation;

    /**
     * the constructor, create only date creation
     */
    public Root() {
        this.dateOfCreation = ZonedDateTime.now();
    }

    /**
     * the constructor, add collection
     * @param products argument for adding
     */
    public Root(HashMap<Long, Product> products) {
        this.products = products;
    }
    public HashMap<Long, Product> getProducts() {
        return products;
    }
    public ZonedDateTime getDateOfCreation() {
        return dateOfCreation;
    }


    public void setProducts(HashMap<Long, Product> products) {
        this.products = products;
    }
    @XmlElement
    public void setProduct(Product product) {
        products.put(createKey(), product);
        dateOfCreation = ZonedDateTime.now();
    }
    public void setProductWithKey(Product product, Long key) {
        products.put(key, product);
        dateOfCreation = ZonedDateTime.now();
    }
    @Override
    public String toString() {
        return "Root{"
                + "products=" + products
                + ", dateOfLastModification=" + dateOfCreation + '}';
    }
    /**
     *
     * @param id id for searching
     * @return key of product by id
     */
    public Long getKeyOnID(Long id) {
        for (Map.Entry<Long, Product> productEntry: products.entrySet()) {
            if (productEntry.getValue().getId() == id) {
                return productEntry.getKey();
            }
        }
        return null;
    }

    /**
     *
     * @param id id for searching
     * @return true if product of collection contains this id and false in another case
     */
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

    /**
     * the method which clear collection
     */
    public void clear() {
        products.clear();
    }

    /**
     * create the string with information about collection
     * @return string with information about collection
     */
    public String infoAboutCollection() {
        return "Class of collection: " + getProducts().getClass()
                + "\nDate of initialization: " + getDateOfCreation()
                + "\nNumber of elements: " + getProducts().size();
    }

    /**
     * remove one product if argument matches with UnitOfMeasure field of product in collection
     * @param unitOfMeasure argument for removing
     */
    public void removeAnyByUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        for (HashMap.Entry<Long, Product> prod: products.entrySet()) {
            if (unitOfMeasure == prod.getValue().getUnitOfMeasure()) {
                products.remove(prod.getKey());
                break;
            }
        }
    }

    /**
     * remove all products which less than argument
     * @param product argument for comparing
     */
    public void removeIfLess(Product product) {
        products.entrySet().removeIf(entry -> product.compareTo(entry.getValue()) > 0);
    }

    /**
     * remove all products which key less than argument
     * @param key key for comparing
     */
    public void removeIfKeyLess(Long key) {
        products.entrySet().removeIf(entry -> key > entry.getKey());
    }

    /**
     * @param key key for searching
     * @return product wich key equal to argument
     */
    public Product getProductByKey(Long key) {
        return products.get(key);
    }

    /**
     * delete product by key
     * @param deleteKey key for deleting
     */
    public void remove(Long deleteKey) {
        products.remove(deleteKey);
    }

    /**
     * create string with collection value
     * @return string
     */
    public String showCollection() {
        StringBuilder s = new StringBuilder("Products: ");
        for (Map.Entry<Long, Product> entry : products.entrySet()) {
            s.append("\n").append(entry.getValue());
        }
        return s.toString();
    }

    /**
     * print string with collection values and keys
     * @param console console for print
     */
    public void toStringWithKey(Console console) {
        console.print("Products: \n");
        for (Map.Entry<Long, Product> entry : products.entrySet()) {
            console.print(entry.getKey() + " " + entry.getValue());
        }
    }

    /**
     *
     * @return number average Of Manufacture Cost of products in collection
     */
    public int averageOfManufactureCost() {
        Integer answer = 0;
        for (HashMap.Entry<Long, Product> prod : products.entrySet()) {
            if (prod.getValue().getManufactureCost() != null) {
                answer += prod.getValue().getManufactureCost();
            }
        }
        return answer;
    }

    /**
     *
     * @return hashmap where keys- field value price in product and values- count of this products with this price
     */
    public HashMap<Long, Long> groupCountingByPrice() {
        HashMap<Long, Long> countingByPrice = new LinkedHashMap<>();
        long productsCount;
        for (HashMap.Entry<Long, Product> entry : products.entrySet()) {
            if (countingByPrice.get(entry.getValue().getPrice()) != null) {
                productsCount = countingByPrice.get(entry.getValue().getPrice());
            } else {
                productsCount = 0;
            }
            countingByPrice.put(entry.getValue().getPrice(), ++productsCount);
        }
        return countingByPrice;

    }
}
