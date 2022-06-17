package test.laba.client.frontEnd.frames.table;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import javax.swing.text.NumberFormatter;
import test.laba.client.frontEnd.frames.local.Localized;
import test.laba.common.dataClasses.Product;
import test.laba.common.dataClasses.UnitOfMeasure;
import test.laba.client.util.Constants;

import javax.swing.table.AbstractTableModel;
import java.text.DateFormat;

public class TableModule extends AbstractTableModel implements Localized {
    private static final int KEY_COLUMN = 0;
    private static final int ID_COLUMN = 1;
    private static final int PRODUCT_NAME_COLUMN = 2;
    private static final int COORDINATION_X_COLUMN = 3;
    private static final int COORDINATION_Y_COLUMN = 4;
    private static final int CREATION_DATE_COLUMN = 5;
    private static final int PRICE_COLUMN = 6;
    private static final int MANUFACTURE_COST_COLUMN = 7;
    private static final int UNIT_OF_MEASURE_COLUMN = 8;
    private static final int PERSON_NAME_COLUMN = 9;
    private static final int BIRTHDAY_COLUMN = 10;
    private static final int HEIGHT_COLUMN = 11;
    private static final int LOCATION_X_COLUMN = 12;
    private static final int LOCATION_Y_COLUMN = 13;
    private static final int LOCATION_NAME_COLUMN = 14;
    private static final HashMap<UnitOfMeasure, Constants> UNIT_OF_MEASURE_CONSTANTS_HASH_MAP;
    private final int columnCount = 15;
    private final ArrayList<ArrayList<?>> values = new ArrayList<>();
    private final HashMap<Integer, String> head = new HashMap<>();
    private final HashMap<Integer, Class<?>> classField = new HashMap<>();
    private ArrayList<ArrayList<?>> constValues = new ArrayList<>();
    private final ResourceBundle resourceBundle;
    private final DateFormat dateFormat;
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
    public Class<Comparable<?>> getColumnClass(int columnIndex) {
        return (Class<Comparable<?>>) classField.get(columnIndex);
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(values.size()>0) {
            return values.get(rowIndex).get(columnIndex);
        } else {
            return null;
        }
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
        constValues.clear();
        products.forEach(this::addProduct);
    }

    public void addProduct(Long key, Product product) {
       NumberFormat numberFormatter = NumberFormat.getNumberInstance(resourceBundle.getLocale());
        ArrayList<Object> newProduct = new ArrayList<>(columnCount);
        newProduct.add(numberFormatter.format(key));
        newProduct.add(numberFormatter.format(product.getId()));
        newProduct.add(product.getName());
        newProduct.add(numberFormatter.format(product.getCoordinates().getX()));
        newProduct.add(product.getCoordinates().getY());
        newProduct.add(dateFormat.format(Date.from(product.getCreationDate().toInstant())));
        newProduct.add(numberFormatter.format(product.getPrice()));
        newProduct.add(numberFormatter.format(product.getManufactureCost()));
        newProduct.add(localisation(UNIT_OF_MEASURE_CONSTANTS_HASH_MAP.get(product.getUnitOfMeasure())));
        if (product.getOwner() != null) {
            newProduct.add(product.getOwner().getName());
            newProduct.add(dateFormat.format(Date.from(product.getOwner().getBirthday().toInstant())));
            newProduct.add(numberFormatter.format(product.getOwner().getHeight()));
            newProduct.add(numberFormatter.format(product.getOwner().getLocation().getX()));
            newProduct.add(numberFormatter.format(product.getOwner().getLocation().getY()));
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
        head.put(KEY_COLUMN, localisation(Constants.KEY));
        head.put(ID_COLUMN, localisation(Constants.ID));
        head.put(PRODUCT_NAME_COLUMN, localisation(Constants.PRODUCT_NAME));
        head.put(COORDINATION_X_COLUMN, localisation(Constants.COORDINATE_X));
        head.put(COORDINATION_Y_COLUMN, localisation(Constants.COORDINATE_Y));
        head.put(CREATION_DATE_COLUMN, localisation(Constants.CREATION_DATE));
        head.put(PRICE_COLUMN, localisation(Constants.PRICE));
        head.put(MANUFACTURE_COST_COLUMN, localisation(Constants.MANUFACTURE_COST));
        head.put(UNIT_OF_MEASURE_COLUMN, localisation(Constants.UNIT_OF_MEASURE));
        head.put(PERSON_NAME_COLUMN, localisation(Constants.PERSON_NAME));
        head.put(BIRTHDAY_COLUMN, localisation(Constants.BIRTHDAY));
        head.put(HEIGHT_COLUMN, localisation(Constants.HEIGHT));
        head.put(LOCATION_X_COLUMN, localisation(Constants.LOCATION_X));
        head.put(LOCATION_Y_COLUMN, localisation(Constants.LOCATION_Y));
        head.put(LOCATION_NAME_COLUMN, localisation(Constants.LOCATION_NAME));

        classField.put(KEY_COLUMN, Long.class);
        classField.put(ID_COLUMN, Long.class);
        classField.put(PRODUCT_NAME_COLUMN, String.class);
        classField.put(COORDINATION_X_COLUMN, Integer.class);
        classField.put(COORDINATION_Y_COLUMN, Float.class);
        classField.put(CREATION_DATE_COLUMN, String.class);
        classField.put(PRICE_COLUMN, Long.class);
        classField.put(MANUFACTURE_COST_COLUMN, Integer.class);
        classField.put(UNIT_OF_MEASURE_COLUMN, String.class);
        classField.put(PERSON_NAME_COLUMN, String.class);
        classField.put(BIRTHDAY_COLUMN, String.class);
        classField.put(HEIGHT_COLUMN, Integer.class);
        classField.put(LOCATION_X_COLUMN, Long.class);
        classField.put(LOCATION_Y_COLUMN, Integer.class);
        classField.put(LOCATION_NAME_COLUMN, String.class);
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
    public String[] unitOfMeasureContains() {
        return UNIT_OF_MEASURE_CONSTANTS_HASH_MAP.values().stream().map(e -> localisation(e)).toArray(String[]::new);
    }


    @Override
    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }
}
