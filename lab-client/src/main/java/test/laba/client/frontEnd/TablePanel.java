package test.laba.client.frontEnd;

import test.laba.client.frontEnd.Frames.AbstractFrame;
import test.laba.client.util.Constants;

import java.awt.*;
import java.awt.event.*;
import java.util.ResourceBundle;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;

public abstract class TablePanel extends JPanel
        implements ActionListener {
    private JTable table;
    protected int columnForChanging;

    public TablePanel(JTable table) {
        super();
        this.table = table;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setAutoCreateRowSorter(true);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        table.getSelectionModel().addListSelectionListener(new RowListener());
        add(new JScrollPane(table));

    }

    protected abstract void outputSelection();

    private class RowListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
            if (event.getValueIsAdjusting()) {
                return;
            }
            columnForChanging = table.getSelectedRow();
            outputSelection();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public int getColumnForChanging() {
        return columnForChanging;
    }

    public JTable getTable() {
        return table;
    }




}
