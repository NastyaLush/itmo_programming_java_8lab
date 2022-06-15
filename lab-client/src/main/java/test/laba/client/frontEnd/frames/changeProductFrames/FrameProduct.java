package test.laba.client.frontEnd.frames.changeProductFrames;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import test.laba.client.frontEnd.frames.local.LanguageAbstractClass;
import test.laba.client.util.Constants;

import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

public abstract class FrameProduct extends LanguageAbstractClass {
    private final Font umFont = new Font("Safari", Font.ITALIC, 13);
    private final Dimension umSize = new Dimension(400, 50);

    public FrameProduct(JFrame jFrame, ResourceBundle resourceBundle) {
        super(jFrame, resourceBundle);
    }


    public JMenu createUMMenu(String name) {
        JMenu menu = new JMenu(name);
        menu.setPreferredSize(umSize);
        menu.setFont(umFont);
        JMenuItem pcs = new JMenuItem(localisation(getResourceBundle(), Constants.PCS));
        changeMenuName(pcs, menu);
        JMenuItem millilitres = new JMenuItem(localisation(getResourceBundle(), Constants.MILLILITERS));
        changeMenuName(millilitres, menu);
        JMenuItem grams = new JMenuItem(localisation(getResourceBundle(), Constants.GRAMS));
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

    public JMenuBar unitOfMeasureButton(JMenu menu) {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menu);
        return menuBar;
    }
}
