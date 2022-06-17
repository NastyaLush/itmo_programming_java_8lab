package test.laba.client.frontEnd.frames.changeProductFrames;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import javax.swing.Box;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;
import test.laba.client.frontEnd.frames.HomeFrame;
import test.laba.client.frontEnd.frames.table.TableModule;
import test.laba.client.frontEnd.frames.table.TablePanel;
import test.laba.client.util.Constants;

import java.util.ResourceBundle;

public abstract class ChangeProductTableDialog extends ChangeProductDialog {
    private final TablePanel tablePanel;
    private final TableModule tableModule;
    private final Dimension unitOfMeasureSize = new Dimension(23, 24);
    private final Dimension labelSize = new Dimension(23, 24);
    private final Dimension textLabelSize = new Dimension(15, 24);
    private final Component enter = Box.createRigidArea(new Dimension(0, 13));

    public ChangeProductTableDialog(TablePanel table, TableModule tableModule, ResourceBundle resourceBundle, HomeFrame homeFrame) {
        super(resourceBundle, homeFrame);
        this.tablePanel = table;
        this.tableModule = tableModule;
    }

    @Override
    protected JTextField createButtonGroup(String name, String description, boolean saveToDelete) {
        tablePanel.getTable().getSelectedRow();
        JLabel label = new JLabel(name + "(" + description + ")");
        label.setForeground(Color.gray);
        label.setFont(getLabelFont());
        label.setPreferredSize(labelSize);

        JTextField textField = new JTextField(getDescription(name));
        textField.setPreferredSize(textLabelSize);

        addLabels(saveToDelete, label, textField, enter);

        return textField;
    }
    @Override
    protected JFormattedTextField createBirthady(String name, String description, boolean saveToDelete){
        tablePanel.getTable().getSelectedRow();
        JLabel label = new JLabel(name + "(" + description + ")");
        label.setForeground(Color.gray);
        label.setFont(getLabelFont());
        label.setPreferredSize(labelSize);

        JFormattedTextField textField = new JFormattedTextField(getDescription(name));
        //textField.setValue(getDescription(name));
        textField.setPreferredSize(textLabelSize);

        addLabels(saveToDelete, label, textField, enter);
        return textField;
    }

    @Override
    protected void addKey() {
    }

    @Override
    protected JMenu unitOfMeas(String name, String description) {
        JLabel label = new JLabel(name + "(" + description + ")");
        label.setForeground(Color.gray);
        label.setFont(getLabelFont());
        label.setPreferredSize(unitOfMeasureSize);


        JMenu menu = createUMMenu(getDescription(name));
        System.out.println(tableModule.delocalizationUnitOfMeasure(getDescription(name)));
        menu.setName(tableModule.delocalizationUnitOfMeasure(getDescription(name)));
        JMenuBar menuBar = unitOfMeasureButton(menu);

        getMainPlusPanel().add(label);
        getMainPlusPanel().add(menuBar);
        getMainPlusPanel().add(enter);
        return menu;
    }

    protected String getDescription(String name) {
        return String.valueOf(tablePanel.getTable().getValueAt(tablePanel.getColumnForChanging(), tableModule.findColumn(name)));
    }

    @Override
    protected String getID() {
        return getDescription(localisation(Constants.ID));
    }
}
