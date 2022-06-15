package test.laba.client.frontEnd.frames.local;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import test.laba.client.frontEnd.frames.AbstractFrame;
import test.laba.client.util.Constants;

import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

public abstract class LanguageAbstractClass extends AbstractFrame implements Localized {
    private final Dimension menuSize = new Dimension(95, 100);
    private final Font menuFont = new Font("Safari", Font.PLAIN, 20);
    public LanguageAbstractClass(JFrame jFrame, ResourceBundle resourceBundle) {
        super(jFrame, resourceBundle);
    }

    protected void run() {

    }

    protected JMenuBar createLanguage(Color textColor) {
        JMenu lang = new JMenu(localisation(getResourceBundle(), Constants.LANGUAGE));
        lang.setFocusPainted(false);
        lang.setFocusable(false);
        lang.setContentAreaFilled(false);
        lang.setBorderPainted(false);
        lang.setPreferredSize(menuSize);
        lang.setFont(menuFont);
        lang.setForeground(textColor);

        JMenuItem rus = new JMenuItem(localisation(getResourceBundle(), Constants.RUSSIAN));
        rus.setName(Local.RUSSIAN);
        JMenuItem nor = new JMenuItem(localisation(getResourceBundle(), Constants.NORWEGIAN));
        nor.setName(Local.NORWEGIAN);
        JMenuItem fr = new JMenuItem(localisation(getResourceBundle(), Constants.FRENCH));
        fr.setName(Local.FRANCE);
        JMenuItem sp = new JMenuItem(localisation(getResourceBundle(), Constants.SPANISH));
        sp.setName(Local.SPANISH);


        changeMenuAndLAg(rus);
        changeMenuAndLAg(nor);
        changeMenuAndLAg(fr);
        changeMenuAndLAg(sp);

        lang.add(rus);
        lang.add(nor);
        lang.add(fr);
        lang.add(sp);

        JMenuBar language = new JMenuBar();
        language.setFocusable(false);
        language.add(lang);
        language.setOpaque(false);
        language.setForeground(textColor);
        language.setBorderPainted(false);
        return language;
    }

    private void changeMenuAndLAg(JMenuItem jMenuItem) {
        jMenuItem.addActionListener((ActionEvent e) -> {
            setResourceBundle(Local.LOCALS.get(jMenuItem.getName()));
            repaintFrame();
        });
    }

    private void repaintFrame() {
        repaintForLanguage();
    }
}
