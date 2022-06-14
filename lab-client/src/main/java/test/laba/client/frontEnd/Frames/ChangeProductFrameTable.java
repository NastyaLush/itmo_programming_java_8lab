package test.laba.client.frontEnd.Frames;

import test.laba.client.frontEnd.TableModule;
import test.laba.client.frontEnd.TablePanel;
import test.laba.client.util.Constants;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public abstract class ChangeProductFrameTable extends ChangeProductFrame {
    private final TablePanel table;
    private final TableModule tableModule;
    private long id;

    public ChangeProductFrameTable(TablePanel table, TableModule tableModule, ResourceBundle resourceBundle) {
        super(resourceBundle);
        this.table = table;
        this.tableModule = tableModule;
    }

    @Override
    protected JTextField createButtonGroupe(String name, String description, boolean saveToDelete) {
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
        return String.valueOf(table.getTable().getValueAt(table.getColumnForChanging(), tableModule.findColumn(name)));
    }

    @Override
    protected String getID(){
        return getDescription(local(Constants.ID));
    }
}
