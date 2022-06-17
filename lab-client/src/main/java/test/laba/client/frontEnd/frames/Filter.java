package test.laba.client.frontEnd.frames;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import test.laba.client.frontEnd.frames.local.Localized;
import test.laba.client.frontEnd.frames.table.TableModule;
import test.laba.client.util.Constants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;

public abstract class Filter implements Localized {
    private static final Dimension DIALOG_SIZE = new Dimension(300, 150);
    private final TableModule tableModule;
    private final HashSet<String> selected = new HashSet<>();
    private final Component enter = Box.createRigidArea(new Dimension(4, 0));
    private ResourceBundle resourceBundle;
    private JDialog dialog;

    public Filter(TableModule tableModule, ResourceBundle resourceBundle) {
        this.tableModule = tableModule;
        this.resourceBundle = resourceBundle;
        this.dialog = new JDialog(null, tableModule.localisation(Constants.FILTER), JDialog.DEFAULT_MODALITY_TYPE);
    }

    public void actionPerformed(JFrame parent) {
        tableModule.clearFilter();
        dialog.setDefaultCloseOperation(dialog.HIDE_ON_CLOSE);
        dialog.setPreferredSize(DIALOG_SIZE);
        dialog.setLocationRelativeTo(parent);
        dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));
        createFilterButtons();

        dialog.pack();
        dialog.setVisible(true);
    }

    private void createFilterButtons() {
        JMenu choose = new JMenu(localisation(Constants.CHOOSE));
        JMenu menu = new JMenu(localisation(Constants.FILTER));
        tableModule.getHead().forEach((key, value) -> {
            JMenuItem jMenuItem = new JMenuItem(value);
            menu.add(jMenuItem);
            jMenuItem.addActionListener(e1 -> {
                choose.removeAll();
                menu.setText(jMenuItem.getText());
                tableModule.getConstValues().forEach(
                        e2 -> choose
                                .add(createJCheckBoxMenuItem(
                                        String.valueOf(e2.get(tableModule.findColumn(jMenuItem.getText())))))
                );
                choose.add(new JCheckBoxMenuItem());
            });

        });
        JMenuBar filter = new JMenuBar();
        filter.add(menu);
        JMenuBar menuBarChoose = new JMenuBar();
        menuBarChoose.add(choose);
        JButton button1 = new JButton(localisation(Constants.OK));
        button1.addActionListener(e -> {
            tableModule.getConstValues().forEach(
                    e2 -> dodo(e2, tableModule.findColumn(menu.getText()))
            );
            close();
            repaint();
        });
        JButton button2 = new JButton(localisation(Constants.CANCEL));
        button2.addActionListener(e -> close());
        JPanel topPanel = new JPanel();

        topPanel.setLayout(new FlowLayout());
        topPanel.add(filter, BorderLayout.CENTER);
        topPanel.add(enter);
        topPanel.add(menuBarChoose);
        createDownPanel(button1, button2, topPanel);
    }

    private void createDownPanel(JButton button1, JButton button2, JPanel topPanel) {
        JPanel downPanel = new JPanel();
        downPanel.setLayout(new FlowLayout());
        downPanel.add(button1, BorderLayout.CENTER);
        downPanel.add(enter);
        downPanel.add(button2);

        dialog.getContentPane().add(topPanel);
        dialog.getContentPane().add(downPanel);
    }

    private void dodo(ArrayList<?> product, int column) {
        selected.stream().filter(e -> e.equals(String.valueOf(product.get(column)))).limit(1).forEach(e2 ->
                tableModule.setFilterCollection(product));
    }

    private JCheckBoxMenuItem createJCheckBoxMenuItem(String command) {
        JCheckBoxMenuItem jCheckBoxMenuItem = new JCheckBoxMenuItem(command);
        jCheckBoxMenuItem.addActionListener(e -> {
            if (jCheckBoxMenuItem.isSelected()) {
                selected.add(jCheckBoxMenuItem.getText());
            } else {
                selected.remove(jCheckBoxMenuItem.getText());
            }
        });
        return jCheckBoxMenuItem;
    }
    private void close() {
        dialog.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        dialog.dispatchEvent(new WindowEvent(dialog, WindowEvent.WINDOW_CLOSING));
    }

    @Override
    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public abstract void repaint();

}
