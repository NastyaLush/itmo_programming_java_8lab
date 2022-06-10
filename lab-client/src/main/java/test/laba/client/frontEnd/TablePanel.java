package test.laba.client.frontEnd;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public abstract class TablePanel extends JPanel
        implements ActionListener {
    private JTable table;
    protected int columnForChanging;

    public TablePanel(JTable table) {
        super();
        this.table = table;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        table.getSelectionModel().addListSelectionListener(new RowListener());
        /*table.getColumnModel().getSelectionModel().
                addListSelectionListener(new ColumnListener());*/
        add(new JScrollPane(table));

    }
    protected abstract void outputSelection();

    private class RowListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
            if (event.getValueIsAdjusting()) {
                return;
            }
            columnForChanging = event.getFirstIndex();
            outputSelection();
        }
    }

    /*private class ColumnListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
            if (event.getValueIsAdjusting()) {
                return;
            }
            columnForChanging = event.getFirstIndex();
            outputSelection();
        }
    }*/

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
