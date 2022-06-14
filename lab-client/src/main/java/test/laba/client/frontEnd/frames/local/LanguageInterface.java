package test.laba.client.frontEnd.frames.local;

import test.laba.client.frontEnd.frames.AbstractFrame;
import test.laba.client.util.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

public abstract class LanguageInterface extends AbstractFrame implements Localasiable {
    public LanguageInterface(JFrame jFrame, ResourceBundle resourceBundle) {
        super(jFrame, resourceBundle);
    }
    protected void run(){

    }
    protected JMenuBar createLanguage(Color textColor){
        JMenu lang = new JMenu(localisation(resourceBundle, Constants.LANGUAGE));
        lang.setFocusPainted(false);
        lang.setFocusable(false);
        lang.setContentAreaFilled(false);
        lang.setBorderPainted(false);
        lang.setPreferredSize(new Dimension(95, 250));
        lang.setFont(new Font("Safari", Font.PLAIN, 20));
        lang.setForeground(textColor);

        JMenuItem rus = new JMenuItem(localisation(resourceBundle, Constants.RUSSIAN));
        rus.setName(Local.russian);
        JMenuItem nor = new JMenuItem(localisation(resourceBundle, Constants.NORWEGIAN));
        nor.setName(Local.norwegian);
        JMenuItem fr = new JMenuItem(localisation(resourceBundle, Constants.FRENCH));
        fr.setName(Local.france);
        JMenuItem sp = new JMenuItem(localisation(resourceBundle, Constants.SPANISH));
        sp.setName(Local.spanish);


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
            resourceBundle = Local.locals.get(jMenuItem.getName());
            repaintFrame();
        });
    }
    private void repaintFrame() {
        repaintForLanguage();
    }
}