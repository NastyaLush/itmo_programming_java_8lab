package test.laba.client.frontEnd;

import test.laba.client.frontEnd.Frames.Localasiable;
import test.laba.common.dataClasses.Product;
import test.laba.common.dataClasses.UnitOfMeasure;
import test.laba.client.util.Constants;

import javax.swing.table.AbstractTableModel;
import java.text.DateFormat;
import java.util.*;

public class TableModule extends AbstractTableModel implements Localasiable {
    private int columnCount = 15;
    private ArrayList<ArrayList<?>> values = new ArrayList<>();
    private HashMap<Integer, String> head = new HashMap<>();
    private HashMap<Integer, Class> classField = new HashMap<>();
    private ArrayList<ArrayList<?>> constValues = new ArrayList<>();
    protected final ResourceBundle resourceBundle;
    private final DateFormat dateFormat;
    private final HashMap<UnitOfMeasure, Constants> unitOfMeasure = new HashMap<UnitOfMeasure, Constants>() {{
        put(UnitOfMeasure.PCS, Constants.PCS);
        put(UnitOfMeasure.MILLILITERS, Constants.MILLILITERS);
        put(UnitOfMeasure.GRAMS, Constants.GRAMS);
    }};

    public TableModule(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        this.dateFormat = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, resourceBundle.getLocale());
        inizialization();
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
    public Class<?> getColumnClass(int columnIndex) {
        return classField.get(columnIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return values.get(rowIndex).get(columnIndex);
    }

    public Object getValueAt(String rowName, int columnIndex) {
        if (head.containsValue(rowName.trim())) {
            Optional<Map.Entry<Integer, String>> row = head.entrySet().stream().filter(e -> e.getValue().equals(rowName.trim())).findFirst();
            int rowIndex = row.get().getKey();
            return  constValues.get(columnIndex).get(rowIndex);
        }
        return null;
    }

    @Override
    public String getColumnName(int columnIndex) {
        if (head.containsKey(columnIndex)) {
            return head.get(columnIndex);
        }
        return "";
    }


    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }


    public void addProducts(HashMap<Long, Product> products) {
        values.clear();
        products.entrySet().forEach(e -> addProduct(e.getKey(), e.getValue()));
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
        newProduct.add(localisation(resourceBundle, unitOfMeasure.get(product.getUnitOfMeasure())));
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

    private void inizialization() {
        head.put(0, localisation(resourceBundle, Constants.KEY));
        head.put(1, localisation(resourceBundle, Constants.ID));
        head.put(2, localisation(resourceBundle, Constants.PRODUCT_NAME));
        head.put(3, localisation(resourceBundle, Constants.COORDINATE_X));
        head.put(4, localisation(resourceBundle, Constants.COORDINATE_Y));
        head.put(5, localisation(resourceBundle, Constants.CREATION_DATE));
        head.put(6, localisation(resourceBundle, Constants.PRICE));
        head.put(7, localisation(resourceBundle, Constants.MANUFACTURE_COST));
        head.put(8, localisation(resourceBundle, Constants.UNIT_OF_MEASURE));
        head.put(9, localisation(resourceBundle, Constants.PERSON_NAME));
        head.put(10, localisation(resourceBundle, Constants.BIRTHDAY));
        head.put(11, localisation(resourceBundle, Constants.HEIGHT));
        head.put(12, localisation(resourceBundle, Constants.LOCATION_X));
        head.put(13, localisation(resourceBundle, Constants.LOCATION_Y));
        head.put(14, localisation(resourceBundle, Constants.LOCATION_NAME));

        classField.put(0, Long.class);
        classField.put(1, Long.class);
        classField.put(2, String.class);
        classField.put(3, Integer.class);
        classField.put(4, Float.class);
        classField.put(5, String.class);
        classField.put(6, Long.class);
        classField.put(7, Integer.class);
        classField.put(8, String.class);
        classField.put(9, String.class);
        classField.put(10, String.class);
        classField.put(11, Integer.class);
        classField.put(12, Long.class);
        classField.put(13, Integer.class);
        classField.put(14, String.class);
    }

    public HashMap<Integer, String> getHead() {
        return head;
    }

    public ArrayList<ArrayList<?>> getValues() {
        return values;
    }

    public ArrayList<ArrayList<?>> getConstValues() {
        return constValues;
    }

    public void setFilterCollection(ArrayList<?> filter) {
        this.values.add(filter);
    }
    public void clearFilter(){
        this.values.clear();
    }
}
