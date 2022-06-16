package test.laba.client.frontEnd.frames;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import test.laba.client.frontEnd.frames.local.LanguageAbstractClass;
import test.laba.client.util.Constants;
import test.laba.client.frontEnd.frames.local.Local;
import test.laba.common.responses.Response;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;


public class Frame extends LanguageAbstractClass implements Runnable {
    private JLabel login;
    private final JTextField textLogin = new JTextField();
    private JLabel password;
    private final JPasswordField textPassword = new JPasswordField();
    private JLabel host;
    private final JTextField textHost = new JTextField("localhost");
    private JLabel port;
    private final JTextField textPort = new JTextField();
    private JLabel authorisation;
    private JButton register;
    private final JPanel mainPanel = new JPanel();
    private final JPanel panel = new JPanel();
    private final JPanel upPanel = new JPanel();
    private String userHost = null;
    private String userPort = null;
    private Response response = null;
    private final Condition condition;
    private final Lock lock;
    private Registration registration;
    private JMenuBar lang;
    private final int defaultLocation = 100;
    private final Dimension minimumSizeFrame = new Dimension(screenSize.width / 2, screenSize.height - 300);
    private final Dimension mainPanelSizeFrame = new Dimension(415, 417);
    private final int eightPanelSmaller = 8;
    private final int fourteenTwicePanelSmaller = 14;
    private final int authorisationTextSize = 50;
    private final int loginTextSize = 18;
    private final int widthLogin = 23;
    private final int heightLogin = 45;
    private final int registerSize = 13;
    private final int enterSize = 50;
    private final int enterSizeAfterPort = 15;
    private final int enterSizeAfterRegister = 25;
    private final int enterSizeAfterSingUp = 5;
    private final Font standardFont = new Font("Safari", Font.BOLD, 15);
    private boolean isNewUser = false;


    public Frame(Condition condition, Lock lock) {
        super(new JFrame(), Local.getResourceBundleDefault());
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getFrame().setLocation(defaultLocation, defaultLocation);
        getFrame().setMinimumSize(minimumSizeFrame);

        paintMainPanels();

        JPanel leftPanel = new JPanel();
        JPanel downPanel = new JPanel();

        mainPanel.setPreferredSize(mainPanelSizeFrame);
        leftPanel.setPreferredSize(new Dimension(getFrame().getWidth() / eightPanelSmaller, getFrame().getHeight()));
        upPanel.setPreferredSize(new Dimension(getFrame().getWidth() / eightPanelSmaller, getFrame().getHeight() / fourteenTwicePanelSmaller));
        downPanel.setPreferredSize(new Dimension(getFrame().getWidth() / eightPanelSmaller, getFrame().getHeight() / eightPanelSmaller));

        getFrame().getContentPane().add(BorderLayout.CENTER, panel);
        getFrame().getContentPane().add(BorderLayout.WEST, leftPanel);
        getFrame().getContentPane().add(BorderLayout.NORTH, upPanel);
        getFrame().getContentPane().add(BorderLayout.SOUTH, downPanel);


        getFrame().setVisible(true);
    }

    private void paintMainPanels() {
        revalidate();
        initialization();
        authorisation.setFont(underLine(new Font("Safari", Font.ITALIC, authorisationTextSize)));
        authorisation.setHorizontalAlignment(JLabel.LEFT);

        Font labelFont = new Font("Safari", Font.BOLD, loginTextSize);
        login.setSize(widthLogin, heightLogin);
        setFontAndColorToLabel(login, labelFont);
        setFontAndColorToLabel(password, labelFont);
        setFontAndColorToLabel(host, labelFont);
        setFontAndColorToLabel(port, labelFont);
        register.setBackground(Color.lightGray);
        register.setFont(underLine(new Font("Arial", Font.ITALIC, registerSize)));
        registration = new Registration();
        register.addActionListener(registration);

        getFrame().setLayout(new BorderLayout());
        BoxLayout boxLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
        panel.setLayout(new BorderLayout());
        panel.add(mainPanel, BorderLayout.WEST);
        mainPanel.setLayout(boxLayout);

        addGroupButtonEnter(authorisation, createEnter(enterSize));
        mainPanel.add(login);
        addGroupButtonEnter(textLogin, createEnter(enterSize));
        mainPanel.add(password);
        addGroupButtonEnter(textPassword, createEnter(enterSize));
        mainPanel.add(host);
        addGroupButtonEnter(textHost, createEnter(enterSize));
        mainPanel.add(port);
        addGroupButtonEnter(textPort, createEnter(enterSizeAfterPort));
        addGroupButtonEnter(register, createEnter(enterSizeAfterRegister));
        mainPanel.add(createButton(Constants.SING_UP, new SignUp()));
        upPanel.setLayout(new BorderLayout());
        lang = createLanguage(Color.BLACK);
        upPanel.add(lang, BorderLayout.WEST);
    }

    private void revalidate() {
        mainPanel.removeAll();
        upPanel.removeAll();
        mainPanel.revalidate();
        upPanel.revalidate();
    }

    private void setFontAndColorToLabel(JLabel label, Font font) {
        label.setFont(font);
        label.setForeground(Color.gray);
    }

    private Component createEnter(int height) {
        return Box.createRigidArea(new Dimension(0, height));
    }

    private void addGroupButtonEnter(Component label, Component enter) {
        mainPanel.add(label);
        mainPanel.add(enter);
    }

    @Override
    public void repaintForLanguage() {
        paintMainPanels();
        if (isNewUser) {
            registration.actionPerformed(new ActionEvent(this, 1, "repaint"));
        }


    }

    private JButton createButton(Constants constants, ActionListener actionListener) {
        JButton signUp = new JButton(localisation(getResourceBundle(), constants));
        signUp.setFont(standardFont);
        signUp.setBackground(Color.gray);
        signUp.addActionListener(actionListener);
        return signUp;
    }

    private void initialization() {
        login = new JLabel(localisation(getResourceBundle(), Constants.LOGIN));
        password = new JLabel(localisation(getResourceBundle(), Constants.PASSWORD));
        host = new JLabel(localisation(getResourceBundle(), Constants.HOST));
        port = new JLabel(localisation(getResourceBundle(), Constants.PORT));
        authorisation = new JLabel(localisation(getResourceBundle(), Constants.AUTHORISATION));
        register = new JButton(localisation(getResourceBundle(), Constants.NOT_LOGIN_YET));
    }

    public String getHost() {
        return userHost;
    }

    public String getPort() {
        return userPort;
    }

    public Response getResponse() {
        return response;
    }


    public boolean isNewUser() {
        return isNewUser;
    }

    public void revalidateFrame() {
        setFrame(new JFrame());
    }

    private class SignUp implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            userHost = textHost.getText();
            userPort = textPort.getText();
            response = new Response(textLogin.getText(), new String(textPassword.getPassword()), "");
            lock.lock();
            condition.signal();
            lock.unlock();
        }
    }

    private class Registration implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            isNewUser = true;
            authorisation.setText(localisation(getResourceBundle(), Constants.REGISTRATION));
            mainPanel.remove(register);
            mainPanel.add(createEnter(enterSizeAfterSingUp));
            mainPanel.add(createButton(Constants.BACK, e1 -> {
                isNewUser = false;
                run();
            }));
            getFrame().repaint();
        }
    }
}
