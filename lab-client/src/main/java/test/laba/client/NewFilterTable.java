package test.laba.client;

/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *//*

*//*
 * NewFilterTable.java requires no other files.
 *//*

import test.laba.common.dataClasses.*;
import test.laba.common.exception.CreateError;
import test.laba.common.exception.VariableException;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Stream;

import static com.sun.glass.ui.Cursor.setVisible;

*//*

public class TableSelectionDemo extends JPanel
        implements ActionListener {
    private JTable table;
    private JTextArea output;

    public TableSelectionDemo() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        MyTableModel myTableModel = new MyTableModel();
        Product product = new Product();
        product.setName("h");
        product.setUnitOfMeasure(UnitOfMeasure.GRAMS);
        product.setPrice(123L);
        product.setId(21);
        product.setCoordinates(new Coordinates(123, (float) 32L));
        try {
            product.setOwner(new Person("sas", ZonedDateTime.now(), 123, new Location(12L, 12, "hh")));
        } catch (CreateError e) {
            throw new RuntimeException(e);
        } catch (VariableException e) {
            throw new RuntimeException(e);
        }

        myTableModel.addProduct(123L, product);

        table = new JTable(myTableModel);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        table.getSelectionModel().addListSelectionListener(new RowListener());
        table.getColumnModel().getSelectionModel().
                addListSelectionListener(new ColumnListener());
        add(new JScrollPane(table));


        output = new JTextArea(5, 40);
        output.setEditable(false);
        add(new JScrollPane(output));
    }


    public void actionPerformed(ActionEvent event) {

    }

    private void outputSelection() {
        output.append(String.format("Lead: %d, %d. ",
                table.getSelectionModel().getLeadSelectionIndex(),
                table.getColumnModel().getSelectionModel().
                        getLeadSelectionIndex()));
        output.append("Rows:");
        for (int c : table.getSelectedRows()) {
            output.append(String.format(" %d", c));
        }
        output.append(". Columns:");
        for (int c : table.getSelectedColumns()) {
            output.append(String.format(" %d", c));
        }
        output.append(".\n");
    }

    private class RowListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
            if (event.getValueIsAdjusting()) {
                return;
            }
            output.append("ROW SELECTION EVENT. ");
            outputSelection();
        }
    }

    private class ColumnListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
            if (event.getValueIsAdjusting()) {
                return;
            }
            output.append("COLUMN SELECTION EVENT. ");
            outputSelection();
        }
    }

    public class MyTableModel extends AbstractTableModel {
        private int columnCount = 15;
        private ArrayList<Object[]> list = new ArrayList<>();

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

        @Override
        public String getColumnName(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return "key";
                case 1:
                    return "id";
                case 2:
                    return "product name";
                case 3:
                    return "coordinate x";
                case 4:
                    return "coordinate y";
                case 5:
                    return "creation_date";
                case 6:
                    return "price";
                case 7:
                    return "manufacture cost";
                case 8:
                    return "unit of measure";
                case 9:
                    return "person name";
                case 10:
                    return "birthday";
                case 11:
                    return "height";
                case 12:
                    return "location x";
                case 13:
                    return "location y";
                case 14:
                    return "location name";
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
            Object[] newProduct = new Object[15];
            newProduct[0] = key;
            newProduct[1] = product.getId();
            newProduct[2] = product.getName();
            newProduct[3] = product.getCoordinates().getX();
            newProduct[4] = product.getCoordinates().getY();
            newProduct[5] = product.getCreationDate();
            newProduct[6] = product.getPrice();
            newProduct[7] = product.getManufactureCost();
            newProduct[8] = product.getUnitOfMeasure();
            if (product.getOwner() != null) {
                newProduct[9] = product.getOwner().getName();
                newProduct[10] = product.getOwner().getBirthday();
                newProduct[11] = product.getOwner().getHeight();
                newProduct[12] = product.getOwner().getLocation().getX();
                newProduct[13] = product.getOwner().getLocation().getY();
                newProduct[14] = product.getOwner().getLocation().getName();
            }
            list.add(newProduct);

        }


    }

    private static void createAndShowGUI() {
        //Disable boldface controls.
        UIManager.put("swing.boldMetal", Boolean.FALSE);

        //Create and set up the window.
        JFrame frame = new JFrame("TableSelectionDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        TableSelectionDemo newContentPane = new TableSelectionDemo();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

}
*/
import java.awt.BorderLayout; import java.awt.event.ActionEvent; import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.JButton; import javax.swing.JFrame; import javax.swing.JLabel; import javax.swing.JPanel; import javax.swing.JScrollPane; import javax.swing.JTable; import javax.swing.JTextField; import javax.swing.RowFilter; import javax.swing.table.DefaultTableModel; import javax.swing.table.TableModel; import javax.swing.table.TableRowSorter;
public class NewFilterTable extends JFrame {

    public NewFilterTable() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        String[] columns = {"ID", "Des", "Date", "Fixed"};
        Object[][] rows = {{1, "C", new Date(), new Date()}, {2, "G", new Date(), new Date()},
                {5, "F", new Date(), new Date()}};
        TableModel model = new DefaultTableModel(rows, columns);
        JTable table = new JTable(model);
        final TableRowSorter<TableModel> sorter;
        sorter = new TableRowSorter<TableModel>(model);
        table.setRowSorter(sorter);
        getContentPane().add(new JScrollPane(table));
        JPanel pnl = new JPanel();
        pnl.add(new JLabel("Filter expression:"));
        final JTextField txtFE = new JTextField(25);
        pnl.add(txtFE);
        JButton btnSetFE = new JButton("Set Filter Expression");
        ActionListener al;
        al = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String expr = txtFE.getText();
                sorter.setRowFilter(RowFilter.regexFilter(expr));
                sorter.setSortKeys(null);
            }
        };
        btnSetFE.addActionListener(al);
        pnl.add(btnSetFE);
        getContentPane().add(pnl, BorderLayout.SOUTH);
        setSize(750, 150);
        setVisible(true);
    }

    public static void main(String[] args) {
        new NewFilterTable();
    }
}