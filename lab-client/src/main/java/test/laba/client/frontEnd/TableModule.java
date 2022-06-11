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
    private ArrayList<String[]> list = new ArrayList<>();
    private HashMap<Integer, String> head = new HashMap<>();
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
        System.out.println("tyuiop");
        inizialization();
    }

    @Override
    public int getRowCount() {
        if (list == null) {
            list = new ArrayList<>();
        }
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return list.get(rowIndex)[columnIndex];
    }

    public String getValueAt(String rowName, int columnIndex) {
        if (head.containsValue(rowName.trim())) {
            Optional<Map.Entry<Integer, String>> row = head.entrySet().stream().filter(e -> e.getValue().equals(rowName.trim())).findFirst();
            int rowIndex = row.get().getKey();
            return list.get(columnIndex)[rowIndex];
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
        list.clear();
        products.entrySet().forEach(e -> addProduct(e.getKey(), e.getValue()));
    }

    public void addProduct(Long key, Product product) {
        String[] newProduct = new String[15];
        newProduct[0] = String.valueOf(key);
        newProduct[1] = String.valueOf(product.getId());
        newProduct[2] = product.getName();
        newProduct[3] = String.valueOf(product.getCoordinates().getX());
        newProduct[4] = String.valueOf(product.getCoordinates().getY());
        newProduct[5] = dateFormat.format(Date.from(product.getCreationDate().toInstant()));
        newProduct[6] = String.valueOf(product.getPrice());
        newProduct[7] = String.valueOf(product.getManufactureCost());
        newProduct[8] = localisation(resourceBundle, unitOfMeasure.get(product.getUnitOfMeasure()));
        if (product.getOwner() != null) {
            newProduct[9] = product.getOwner().getName();
            newProduct[10] = dateFormat.format(Date.from(product.getOwner().getBirthday().toInstant()));
            newProduct[11] = String.valueOf(product.getOwner().getHeight());
            newProduct[12] = String.valueOf(product.getOwner().getLocation().getX());
            newProduct[13] = String.valueOf(product.getOwner().getLocation().getY());
            newProduct[14] = product.getOwner().getLocation().getName();
        }
        list.add(newProduct);

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
    }


}
