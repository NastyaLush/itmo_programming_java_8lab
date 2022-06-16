package test.laba.client.frontEnd.frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import test.laba.client.frontEnd.frames.local.Localized;
import test.laba.client.util.Constants;

import java.awt.event.WindowEvent;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public abstract class AbstractFrame implements FrameInterface, Localized {
    protected final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private JFrame jFrame;
    private ResourceBundle resourceBundle;

    public AbstractFrame(JFrame jFrame, ResourceBundle resourceBundle) {
        this.jFrame = jFrame;
        this.resourceBundle = resourceBundle;
    }

    @Override
    public void exception(String exception) {
        JOptionPane.showMessageDialog(jFrame, exception, localisation(resourceBundle, Constants.ERROR), JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void show(String message) {
        JOptionPane.showConfirmDialog(jFrame, message, localisation(resourceBundle, Constants.INFO), JOptionPane.OK_CANCEL_OPTION);
    }

    public void showScript(String message) {
        JPanel panel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(screenSize.width / 2, screenSize.height / 2);
            }
        };
        panel.setMaximumSize(new Dimension(screenSize.width / 2, screenSize.height / 2));

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        panel.setLayout(new BorderLayout());
        panel.add(new JScrollPane(textArea));
        textArea.setText(message);
        JOptionPane.showConfirmDialog(null, panel, message, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
    }

    protected Font underLine(Font font) {
        Map<TextAttribute, Object> map = new HashMap<>();
        map.put(TextAttribute.FONT, font);
        map.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        return Font.getFont(map);
    }

    public void close() {
        jFrame.removeAll();
        jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        close(jFrame);
    }

    public void close(JFrame frame) {
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.dispatchEvent(new WindowEvent(jFrame, WindowEvent.WINDOW_CLOSING));
    }

    protected void revalidate(JPanel panel) {
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public abstract void repaintForLanguage();

    public Dimension getScreenSize() {
        return screenSize;
    }

    protected String local(Constants constants) {
        return localisation(resourceBundle, constants);
    }

    public JFrame getFrame() {
        return jFrame;
    }

    public void setFrame(JFrame frame) {
        this.jFrame = frame;
    }
}
