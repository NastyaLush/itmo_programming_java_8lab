package test.laba.client.frontEnd;

import test.laba.common.dataClasses.Product;

import javax.swing.table.AbstractTableModel;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class TableModule extends AbstractTableModel {
private int columnCount = 15;
private ArrayList<String[]> list = new ArrayList<>();
private HashMap<Long, Product> products = null;


    @Override
    public int getRowCount() {
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
    @Override
    public String getColumnName(int columnIndex){
        switch (columnIndex){
            case 0: return "key";
            case 1: return "id";
            case 2: return "product name";
            case 3: return "coordinate x";
            case 4: return "coordinate y";
            case 5: return "creation_date";
            case 6: return "price";
            case 7: return "manufacture cost";
            case 8: return "unit of measure";
            case 9: return "person name";
            case 10: return "birthday";
            case 11: return "height";
            case 12: return "location x";
            case 13: return "location y";
            case 14: return "location name";


        }

        return "";
    }
}
