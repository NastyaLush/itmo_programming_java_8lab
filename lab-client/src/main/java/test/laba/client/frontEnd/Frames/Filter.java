package test.laba.client.frontEnd.Frames;

import test.laba.client.frontEnd.TableModule;
import test.laba.client.util.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;

public abstract class Filter extends AbstractFrame {
    private final TableModule tableModule;
    private final HashSet<String> selected = new HashSet<>();
    public Filter(TableModule tableModule, ResourceBundle resourceBundle){
        super(new JFrame(tableModule.localisation(resourceBundle, Constants.FILTER)), resourceBundle);
        this.tableModule = tableModule;
    }

    public void actionPerformed(ActionEvent actionEvent, JFrame parent) {
        tableModule.clearFilter();
        jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jFrame.setLocationRelativeTo(parent);
        jFrame.setMinimumSize(new Dimension(screenSize.width/5, screenSize.height/8));
        jFrame.setMaximumSize(new Dimension(screenSize.width/3, screenSize.height/3));
        jFrame.setLayout(new BoxLayout(jFrame.getContentPane(), BoxLayout.Y_AXIS));


        JMenu choose = new JMenu(local(Constants.CHOOSE));

        JMenu menu = new JMenu(local(Constants.FILTER));
        tableModule.getHead().entrySet().stream().forEach(e ->{
            JMenuItem jMenuItem = new JMenuItem(e.getValue());
            menu.add(jMenuItem);
            jMenuItem.addActionListener(e1 -> {
                choose.removeAll();
                menu.setText(jMenuItem.getText());
                tableModule.getConstValues().stream().forEach(
                        e2 -> choose
                                .add(createJCheckBoxMenuItem
                                        (String.valueOf(e2.get(tableModule.findColumn(jMenuItem.getText())))))
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
            tableModule.getConstValues().stream().forEach(
                    e2 -> dodo(e2, tableModule.findColumn(menu.getText()))
            );
            close();
            repaint();
        });

        JButton button2 = new JButton(local(Constants.CANCEL));
        button2.addActionListener(e -> close(jFrame));

        JPanel toppanel = new JPanel();
        toppanel.setLayout(new FlowLayout());
        toppanel.add(filter, BorderLayout.CENTER);
        toppanel.add(Box.createRigidArea(new Dimension(4,0)));
        toppanel.add(menuBarChoose);

        JPanel downPanel = new JPanel();
        downPanel.setLayout(new FlowLayout());
        downPanel.add(button1, BorderLayout.CENTER);
        downPanel.add(Box.createRigidArea(new Dimension(4,0)));
        downPanel.add(button2);

        jFrame.getContentPane().add(toppanel);
        jFrame.getContentPane().add(downPanel);


        jFrame.pack();
        jFrame.setVisible(true);
    }
    private void dodo(ArrayList<?> product, int column){
        selected.stream().filter(e -> e.equals(String.valueOf(product.get(column)))).limit(1).forEach( e2 ->
                tableModule.setFilterCollection(product));
    }
    private JCheckBoxMenuItem createJCheckBoxMenuItem(String command){
        JCheckBoxMenuItem jCheckBoxMenuItem = new JCheckBoxMenuItem(command);
        jCheckBoxMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jCheckBoxMenuItem.isSelected()){
                    selected.add(jCheckBoxMenuItem.getText());
                } else {
                    selected.remove(jCheckBoxMenuItem.getText());
                }
            }
        });
        return jCheckBoxMenuItem;
    }

    public abstract void repaint();

    @Override
    public void repaintForLanguage() {
    }
}
