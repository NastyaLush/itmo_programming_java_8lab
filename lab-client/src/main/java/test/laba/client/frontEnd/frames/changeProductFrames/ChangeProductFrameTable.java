package test.laba.client.frontEnd.frames.changeProductFrames;

import test.laba.client.frontEnd.frames.table.TableModule;
import test.laba.client.frontEnd.frames.table.TablePanel;
import test.laba.client.util.Constants;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public abstract class ChangeProductFrameTable extends ChangeProductFrame {
    private final TablePanel tablePanel;
    private final TableModule tableModule;
    private long id;

    public ChangeProductFrameTable(TablePanel table, TableModule tableModule, ResourceBundle resourceBundle) {
        super(resourceBundle);
        this.tablePanel = table;
        this.tableModule = tableModule;
    }

    @Override
    protected JTextField createButtonGroupe(String name, String description, boolean saveToDelete) {
        tablePanel.getTable().getSelectedRow();
        JLabel label = new JLabel(name + "(" + description + ")");
        label.setForeground(Color.gray);
        label.setFont(labelFont);
        label.setPreferredSize(new Dimension(23, 24));

        JTextField textField = new JTextField(getDescription(name));
        textField.setPreferredSize(new Dimension(15, 24));
        Component enter = Box.createRigidArea(new Dimension(0, 13));


        if (saveToDelete) {
            ownersLabels.add(label);
            ownersLabels.add(textField);
            ownersLabels.add(enter);
        }

        mainPlusPanel.add(label);
        mainPlusPanel.add(textField);
        mainPlusPanel.add(enter);
        return textField;
    }
    @Override
    protected void addKey() {
    }

    protected JMenu unitOfMeas(String name, String description) {
        JLabel label = new JLabel(name + "(" + description + ")");
        label.setForeground(Color.gray);
        label.setFont(labelFont);
        label.setPreferredSize(new Dimension(23, 24));


        JMenu menu = createUMMenu(getDescription(name));
        System.out.println(tableModule.delocalisationUnitOfMeasure(getDescription(name)));
        menu.setName(tableModule.delocalisationUnitOfMeasure(getDescription(name)));
        JMenuBar menuBar = unitOfMeasureButton(menu);

        mainPlusPanel.add(label);
        mainPlusPanel.add(menuBar);
        mainPlusPanel.add(Box.createRigidArea(new Dimension(0, 13)));
        return menu;
    }

    protected String getDescription(String name) {
        return String.valueOf(tablePanel.getTable().getValueAt(tablePanel.getColumnForChanging(), tableModule.findColumn(name)));
    }

    @Override
    protected String getID(){
        return getDescription(local(Constants.ID));
    }
}