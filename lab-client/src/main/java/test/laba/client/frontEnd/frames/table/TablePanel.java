package test.laba.client.frontEnd.frames.table;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public abstract class TablePanel extends JPanel
        implements ActionListener {
    private static final Dimension TABLE_SIZE = new Dimension(500, 70);
    private final JTable table;
    private int columnForChanging;

    public TablePanel(JTable table) {
        super();
        this.table = table;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setAutoCreateRowSorter(true);

        table.setPreferredScrollableViewportSize(TABLE_SIZE);
        table.setFillsViewportHeight(true);
        table.getSelectionModel().addListSelectionListener(new RowListener());
        add(new JScrollPane(table));

    }
    protected abstract void outputSelection();
    @Override
    public void actionPerformed(ActionEvent e) {
    }
    public int getColumnForChanging() {
        return columnForChanging;
    }
    public JTable getTable() {
        return table;
    }
    private class RowListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent event) {
            if (event.getValueIsAdjusting()) {
                return;
            }
            columnForChanging = table.getSelectedRow();
            outputSelection();
        }
    }
}
