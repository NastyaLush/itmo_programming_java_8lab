package test.laba.client.frontEnd.frames;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import test.laba.client.frontEnd.frames.table.TableModule;
import test.laba.client.util.Constants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;

public abstract class Filter extends AbstractFrame {
    private final TableModule tableModule;
    private final HashSet<String> selected = new HashSet<>();
    private final Component enter = Box.createRigidArea(new Dimension(4, 0));

    public Filter(TableModule tableModule, ResourceBundle resourceBundle) {
        super(new JFrame(tableModule.localisation(resourceBundle, Constants.FILTER)), resourceBundle);
        this.tableModule = tableModule;
    }

    public void actionPerformed(JFrame parent) {
        tableModule.clearFilter();
        getFrame().setDefaultCloseOperation(getFrame().HIDE_ON_CLOSE);
        getFrame().setLocationRelativeTo(parent);
        getFrame().setLayout(new BoxLayout(getFrame().getContentPane(), BoxLayout.Y_AXIS));
        //getFrame().setPreferredSize(new Dimension(200, 200));
        createFilterButtons();

        getFrame().pack();
        getFrame().setVisible(true);
    }

    private void createFilterButtons() {
        JMenu choose = new JMenu(local(Constants.CHOOSE));
        JMenu menu = new JMenu(local(Constants.FILTER));
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
        JButton button1 = new JButton(local(Constants.OK));
        button1.addActionListener(e -> {
            tableModule.getConstValues().forEach(
                    e2 -> dodo(e2, tableModule.findColumn(menu.getText()))
            );
            close();
            repaint();
        });
        JButton button2 = new JButton(local(Constants.CANCEL));
        button2.addActionListener(e -> close(getFrame()));
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

        getFrame().getContentPane().add(topPanel);
        getFrame().getContentPane().add(downPanel);
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

    public abstract void repaint();

    @Override
    public void repaintForLanguage() {
    }
}
