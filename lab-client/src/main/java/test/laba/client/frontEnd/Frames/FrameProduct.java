package test.laba.client.frontEnd.Frames;

import test.laba.client.util.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

public class FrameProduct extends AbstractFrame {

    public FrameProduct(JFrame jFrame, ResourceBundle resourceBundle) {
        super(jFrame, resourceBundle);
    }

    protected JMenu createUMMenu(String name) {
        JMenu menu = new JMenu(name);
        menu.setPreferredSize(new Dimension(400, 50));
        menu.setFont(new Font("Safari", Font.ITALIC, 13));
        JMenuItem pcs = new JMenuItem(localisation(resourceBundle, Constants.PCS));
        changeMenuName(pcs, menu);
        JMenuItem millilitres = new JMenuItem(localisation(resourceBundle, Constants.MILLILITERS));
        changeMenuName(millilitres, menu);
        JMenuItem grams = new JMenuItem(localisation(resourceBundle, Constants.GRAMS));
        changeMenuName(grams, menu);

        menu.add(pcs);
        menu.add(millilitres);
        menu.add(grams);
        return menu;

    }

    protected void changeMenuName(JMenuItem jMenuItem, JMenu menu) {
        jMenuItem.addActionListener((ActionEvent e) -> {
            menu.setText(jMenuItem.getText());
            menu.setName(jMenuItem.getName());
        });
    }

    protected JMenuBar unitOfMeasureButton(JMenu menu) {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menu);
        return menuBar;
    }
}
