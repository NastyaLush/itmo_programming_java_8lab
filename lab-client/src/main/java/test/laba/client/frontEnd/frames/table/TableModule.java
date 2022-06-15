package test.laba.client.frontEnd.frames.table;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import test.laba.client.frontEnd.frames.local.Localized;
import test.laba.common.dataClasses.Product;
import test.laba.common.dataClasses.UnitOfMeasure;
import test.laba.client.util.Constants;

import javax.swing.table.AbstractTableModel;
import java.text.DateFormat;

public class TableModule extends AbstractTableModel implements Localized {
    private static final HashMap<UnitOfMeasure, Constants> UNIT_OF_MEASURE_CONSTANTS_HASH_MAP;
    private final int columnCount = 15;
    private final ArrayList<ArrayList<?>> values = new ArrayList<>();
    private final HashMap<Integer, String> head = new HashMap<>();
    private final HashMap<Integer, Class> classField = new HashMap<>();
    private ArrayList<ArrayList<?>> constValues = new ArrayList<>();
    private final ResourceBundle resourceBundle;
    private final DateFormat dateFormat;
    private final int keyColumn = 0;
    private final int idColumn = 1;
    private final int productNameColumn = 2;
    private final int coordinationXColumn = 3;
    private final int coordinationYColumn = 4;
    private final int creationDateColumn = 5;
    private final int priceColumn = 6;
    private final int manufactureCostColumn = 7;
    private final int unitOfMeasureColumn = 8;
    private final int personNameColumn = 9;
    private final int birthdayColumn = 10;
    private final int heightColumn = 11;
    private final int locationXColumn = 12;
    private final int locationYColumn = 13;
    private final int locationNameColumn = 14;
    static {
        UNIT_OF_MEASURE_CONSTANTS_HASH_MAP = new HashMap<>();
            UNIT_OF_MEASURE_CONSTANTS_HASH_MAP.put(UnitOfMeasure.PCS, Constants.PCS);
            UNIT_OF_MEASURE_CONSTANTS_HASH_MAP.put(UnitOfMeasure.MILLILITERS, Constants.MILLILITERS);
            UNIT_OF_MEASURE_CONSTANTS_HASH_MAP.put(UnitOfMeasure.GRAMS, Constants.GRAMS);
    }
    public TableModule(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        this.dateFormat = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, resourceBundle.getLocale());
        initialization();
    }
    @Override
    public int getRowCount() {
        if (constValues == null) {
            constValues = new ArrayList<>();
        }
        return values.size();
    }
    @Override
    public int getColumnCount() {
        return columnCount;
    }
    @Override
    public Class<Comparable> getColumnClass(int columnIndex) {
        return classField.get(columnIndex);
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return values.get(rowIndex).get(columnIndex);
    }

    @Override
    public String getColumnName(int columnIndex) {
        if (head.containsKey(columnIndex)) {
            return head.get(columnIndex);
        }
        return "";
    }

    public String delocalizationUnitOfMeasure(String name) {
        if (resourceBundle.getString(Constants.PCS.getString()).equals(name)) {
            return Constants.PCS.getString();
        }
        if (resourceBundle.getString(Constants.GRAMS.getString()).equals(name)) {
            return Constants.GRAMS.getString();
        }
        if (resourceBundle.getString(Constants.MILLILITERS.getString()).equals(name)) {
            return Constants.MILLILITERS.getString();
        }
        return null;
    }


    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }


    public void addProducts(HashMap<Long, Product> products) {
        values.clear();
        products.forEach(this::addProduct);
    }

    public void addProduct(Long key, Product product) {
        ArrayList<Object> newProduct = new ArrayList<>(columnCount);
        newProduct.add(key);
        newProduct.add(product.getId());
        newProduct.add(product.getName());
        newProduct.add(product.getCoordinates().getX());
        newProduct.add(product.getCoordinates().getY());
        newProduct.add(dateFormat.format(Date.from(product.getCreationDate().toInstant())));
        newProduct.add(product.getPrice());
        newProduct.add(product.getManufactureCost());
        newProduct.add(localisation(resourceBundle, UNIT_OF_MEASURE_CONSTANTS_HASH_MAP.get(product.getUnitOfMeasure())));
        if (product.getOwner() != null) {
            newProduct.add(product.getOwner().getName());
            newProduct.add(dateFormat.format(Date.from(product.getOwner().getBirthday().toInstant())));
            newProduct.add(product.getOwner().getHeight());
            newProduct.add(product.getOwner().getLocation().getX());
            newProduct.add(product.getOwner().getLocation().getY());
            newProduct.add(product.getOwner().getLocation().getName());
        } else {
            newProduct.add(null);
            newProduct.add(null);
            newProduct.add(null);
            newProduct.add(null);
            newProduct.add(null);
            newProduct.add(null);
        }
        values.add(newProduct);
        constValues.add(newProduct);

    }

    private void initialization() {
        head.put(keyColumn, localisation(resourceBundle, Constants.KEY));
        head.put(idColumn, localisation(resourceBundle, Constants.ID));
        head.put(productNameColumn, localisation(resourceBundle, Constants.PRODUCT_NAME));
        head.put(coordinationXColumn, localisation(resourceBundle, Constants.COORDINATE_X));
        head.put(coordinationYColumn, localisation(resourceBundle, Constants.COORDINATE_Y));
        head.put(creationDateColumn, localisation(resourceBundle, Constants.CREATION_DATE));
        head.put(priceColumn, localisation(resourceBundle, Constants.PRICE));
        head.put(manufactureCostColumn, localisation(resourceBundle, Constants.MANUFACTURE_COST));
        head.put(unitOfMeasureColumn, localisation(resourceBundle, Constants.UNIT_OF_MEASURE));
        head.put(personNameColumn, localisation(resourceBundle, Constants.PERSON_NAME));
        head.put(birthdayColumn, localisation(resourceBundle, Constants.BIRTHDAY));
        head.put(heightColumn, localisation(resourceBundle, Constants.HEIGHT));
        head.put(locationXColumn, localisation(resourceBundle, Constants.LOCATION_X));
        head.put(locationYColumn, localisation(resourceBundle, Constants.LOCATION_Y));
        head.put(locationNameColumn, localisation(resourceBundle, Constants.LOCATION_NAME));

        classField.put(keyColumn, Long.class);
        classField.put(idColumn, Long.class);
        classField.put(productNameColumn, String.class);
        classField.put(coordinationXColumn, Integer.class);
        classField.put(coordinationYColumn, Float.class);
        classField.put(creationDateColumn, String.class);
        classField.put(priceColumn, Long.class);
        classField.put(manufactureCostColumn, Integer.class);
        classField.put(unitOfMeasureColumn, String.class);
        classField.put(personNameColumn, String.class);
        classField.put(birthdayColumn, String.class);
        classField.put(heightColumn, Integer.class);
        classField.put(locationXColumn, Long.class);
        classField.put(locationYColumn, Integer.class);
        classField.put(locationNameColumn, String.class);
    }

    public HashMap<Integer, String> getHead() {
        return head;
    }

    public ArrayList<ArrayList<?>> getConstValues() {
        return constValues;
    }

    public void setFilterCollection(ArrayList<?> filter) {
        this.values.add(filter);
    }

    public void clearFilter() {
        this.values.clear();
    }


}
