package test.laba.common.dataClasses;



import test.laba.common.exception.CreateError;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.Objects;

@XmlRootElement(name = "product")
@XmlType(propOrder = {"id", "name", "coordinates", "price", "manufactureCost", "unitOfMeasure", "owner" })
public class  Product implements Comparable<Product>, Serializable {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long price; //Поле не может быть null, Значение поля должно быть больше 0
    private Integer manufactureCost;
    private UnitOfMeasure unitOfMeasure; //Поле не может быть null
    private Person owner; //Поле может быть null

    @SuppressWarnings("all")
    /**
     * the constructor which create only creation date
     */
    public Product() {
        this.creationDate = ZonedDateTime.now();
        this.owner = null;
    }

    /**
     * the constructor fill all fields and generate id
     * @param name product name, type string, not null
     * @param coordinates coordinates, not null
     * @param price long price, not null and more than zero
     * @param manufactureCost integer manufactureCost
     * @param unitOfMeasure unit of measure, not null
     * @param owner person, not null
     * @throws CreateError if variables are not right throws createError
     */
    public Product(String name, Coordinates coordinates, Long price, Integer manufactureCost, UnitOfMeasure unitOfMeasure, Person owner) throws CreateError {
        if (name == null || name.isEmpty() || coordinates == null || price == null || price <= 0  || unitOfMeasure == null) {
            throw new CreateError("Ошибка при создании объекта Product, обратите внимание:\n"
                    + "    Поле name может быть null и не может быть пустым\n"
                    + "    Поле coordinates не может быть null\n"
                    + "    Поле price не может быть null и значение поля должно быть больше 0\n"
                    + "    Поле unitOfMeasure не может быть null\n"
                    + "    Поле owner может быть null\n");
        } else {
            //createID(root);
            this.name = name;
            this.coordinates = coordinates;
            this.price = price;
            this.creationDate = ZonedDateTime.now();
            if (manufactureCost != null) {
                this.manufactureCost = manufactureCost;
            }
            this.unitOfMeasure = unitOfMeasure;
            this.owner = owner;
        }
    }

    //сделан костыль!!!!!!!!!!! надо будет чекнуть и исправить
/*
    public Product(long id, String name, Coordinates coordinates, ZonedDateTime creationDate, Long price, Integer manufactureCost, UnitOfMeasure unitOfMeasure, Person owner) throws CreateError {
        if (name == null || name.isEmpty() || coordinates == null || price == null || price <= 0  || unitOfMeasure == null) {
            throw new CreateError("Ошибка при создании объекта Product, обратите внимание:\n"
                    + "    Поле name может быть null и не может быть пустым\n"
                    + "    Поле coordinates не может быть null\n"
                    + "    Поле price не может быть null и значение поля должно быть больше 0\n"
                    + "    Поле unitOfMeasure не может быть null\n"
                    + "    Поле owner может быть null\n");
        } else {
            this.id = id;
            this.name = name;
            this.coordinates = coordinates;
            this.creationDate = creationDate;
            this.price = price;
            this.manufactureCost = manufactureCost;
            this.unitOfMeasure = unitOfMeasure;
            this.owner = owner;
        }
    }
*/

    public Product(long id, String name, Coordinates coordinates, Long price, Integer manufactureCost, UnitOfMeasure unitOfMeasure, Person owner) throws CreateError {
        if (name == null || name.isEmpty() || coordinates == null || price == null || price <= 0  || unitOfMeasure == null) {
            throw new CreateError("Ошибка при создании объекта Product, обратите внимание:\n"
                    + "    Поле name может быть null и не может быть пустым\n"
                    + "    Поле coordinates не может быть null\n"
                    + "    Поле price не может быть null и значение поля должно быть больше 0\n"
                    + "    Поле unitOfMeasure не может быть null\n"
                    + "    Поле owner может быть null\n");
        } else {
            this.id = id;
            this.name = name;
            this.coordinates = coordinates;
            this.price = price;
            this.manufactureCost = manufactureCost;
            this.unitOfMeasure = unitOfMeasure;
            this.owner = owner;
        }
    }

    @Override
    public String toString() {
        return "Product:"
                + "id=" + id
                + ", name=" + name
                + ", coordinates=" + coordinates
                + ", creationDate=" + creationDate.getYear() + "-" + creationDate.getMonthValue() + "-" + creationDate.getDayOfMonth()
                + ", price=" + price
                + ", manufactureCost=" + manufactureCost
                + ", unitOfMeasure=" + unitOfMeasure
                + ", owner=" + owner + '\n';
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public void setManufactureCost(Integer manufactureCost) {
        this.manufactureCost = manufactureCost;
    }

    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate.toLocalDate();
    }

    public Long getPrice() {
        return price;
    }

    public Integer getManufactureCost() {
        if (manufactureCost != null) {
            return manufactureCost;
        }
        return null;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public Person getOwner() {
        return owner;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        Product product = (Product) o;
        return getId() == product.getId() && getName().equals(product.getName()) && getCoordinates().equals(product.getCoordinates()) && getCreationDate().equals(product.getCreationDate()) && getPrice().equals(product.getPrice()) && getManufactureCost().equals(product.getManufactureCost()) && getUnitOfMeasure() == product.getUnitOfMeasure() && getOwner().equals(product.getOwner());
    }
    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getCoordinates(), getCreationDate(), getPrice(), getManufactureCost(), getUnitOfMeasure(), getOwner());
    }

    @Override
    public int compareTo(Product o) {
        return Comparator.comparing(Product :: getName).
                thenComparing(Product :: getPrice).
                thenComparing(Product :: getManufactureCost).
                thenComparing(Product :: getUnitOfMeasure).
                thenComparing(Product :: getCoordinates).
                thenComparing(Product :: getOwner).compare(this, o);

    }


    /**
     * check product on match criteria of fields
     * @return true or false
     */
    public boolean isRightProduct() {
        boolean s = id > 0 && name != null && !"".equals(name) && coordinates != null && price != null && price > 0 && unitOfMeasure != null && coordinates.isRightCoordinates();
        if (owner != null) {
            return s && owner.isRightPerson();
        }
        return s;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }
}

