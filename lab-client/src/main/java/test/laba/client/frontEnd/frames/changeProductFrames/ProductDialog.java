package test.laba.client.frontEnd.frames.changeProductFrames;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import test.laba.client.frontEnd.frames.local.Localized;
import test.laba.client.util.Constants;

import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

public abstract class ProductDialog implements Localized {
    private final Font umFont = new Font("Safari", Font.ITALIC, 13);
    private final Dimension umSize = new Dimension(400, 50);
    private JDialog dialog;
    private ResourceBundle resourceBundle;

    public ProductDialog(JDialog dialog, ResourceBundle resourceBundle) {
        this.dialog = dialog;
        this.resourceBundle = resourceBundle;
    }

    @Override
    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public JDialog getDialog() {
        return dialog;
    }

    public void setDialog(JDialog dialog) {
        this.dialog = dialog;
    }
    public JMenu createUMMenu(String name) {
        JMenu menu = new JMenu(name);
        menu.setPreferredSize(umSize);
        menu.setFont(umFont);
        JMenuItem pcs = new JMenuItem(localisation(Constants.PCS));
        changeMenuName(pcs, menu);
        JMenuItem millilitres = new JMenuItem(localisation(Constants.MILLILITERS));
        changeMenuName(millilitres, menu);
        JMenuItem grams = new JMenuItem(localisation(Constants.GRAMS));
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
