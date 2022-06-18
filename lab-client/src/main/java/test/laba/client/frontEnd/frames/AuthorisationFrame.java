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
import test.laba.client.Client;
import test.laba.client.ClientApp;
import test.laba.client.util.Constants;
import test.laba.client.frontEnd.frames.local.Local;
import test.laba.client.util.VariableParsing;
import test.laba.common.exception.VariableException;
import test.laba.common.responses.Response;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import test.laba.common.responses.ResponseWithError;


public class AuthorisationFrame extends AbstractFrame implements Runnable {
    private static final int EIGHT_PANEL_SMALLER = 8;
    private static final int FOURTEEN_TWICE_PANEL_SMALLER = 14;
    private static final int AUTHORISATION_TEXT_SIZE = 50;
    private static final int LOGIN_TEXT_SIZE = 18;
    private static final int WIDTH_LOGIN = 23;
    private static final int HEIGHT_LOGIN = 45;
    private static final int REGISTER_SIZE = 13;
    private static final int ENTER_SIZE = 50;
    private static final int ENTER_SIZE_AFTER_PORT = 15;
    private static final int ENTER_SIZE_AFTER_REGISTER = 25;
    private static final int ENTER_SIZE_AFTER_SING_UP = 5;
    private static final int DEFAULT_LOCATION = 100;
    private static final Font STANDARD_FONT = new Font("Safari", Font.BOLD, 15);
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
    private final ClientApp clientApp;
    private Registration registration;
    private final Dimension minimumSizeFrame = new Dimension(screenSize.width / 2, screenSize.height - 300);
    private final Dimension mainPanelSizeFrame = new Dimension(415, 417);
    private boolean isNewUser = false;


    public AuthorisationFrame(ClientApp clientApp) {
        super(new JFrame(), Local.getResourceBundleDefault());
        this.clientApp = clientApp;
    }

    @Override
    public void run() {
        getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getFrame().setLocation(DEFAULT_LOCATION, DEFAULT_LOCATION);
        getFrame().setMinimumSize(minimumSizeFrame);

        paintMainPanels();

        JPanel leftPanel = new JPanel();
        JPanel downPanel = new JPanel();

        mainPanel.setSize(mainPanelSizeFrame);
        leftPanel.setPreferredSize(new Dimension(getFrame().getWidth() / EIGHT_PANEL_SMALLER, getFrame().getHeight()));
        upPanel.setPreferredSize(new Dimension(getFrame().getWidth() / EIGHT_PANEL_SMALLER, getFrame().getHeight() / FOURTEEN_TWICE_PANEL_SMALLER));
        downPanel.setPreferredSize(new Dimension(getFrame().getWidth() / EIGHT_PANEL_SMALLER, getFrame().getHeight() / EIGHT_PANEL_SMALLER));

        getFrame().getContentPane().add(BorderLayout.CENTER, panel);
        getFrame().getContentPane().add(BorderLayout.WEST, leftPanel);
        getFrame().getContentPane().add(BorderLayout.NORTH, upPanel);
        getFrame().getContentPane().add(BorderLayout.SOUTH, downPanel);


        getFrame().setVisible(true);
    }

    private void paintMainPanels() {
        revalidate();
        initialization();
        authorisation.setFont(underLine(new Font("Safari", Font.ITALIC, AUTHORISATION_TEXT_SIZE)));
        authorisation.setHorizontalAlignment(JLabel.LEFT);
        Font labelFont = new Font("Safari", Font.BOLD, LOGIN_TEXT_SIZE);
        login.setSize(WIDTH_LOGIN, HEIGHT_LOGIN);
        setFontAndColorToLabel(login, labelFont);
        setFontAndColorToLabel(password, labelFont);
        setFontAndColorToLabel(host, labelFont);
        setFontAndColorToLabel(port, labelFont);
        register.setBackground(Color.lightGray);
        register.setFont(underLine(new Font("Arial", Font.ITALIC, REGISTER_SIZE)));
        registration = new Registration();
        register.addActionListener(registration);

        getFrame().setLayout(new BorderLayout());
        BoxLayout boxLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
        panel.setLayout(new BorderLayout());
        JPanel panel2 = new JPanel();
        panel2.add(mainPanel);
        panel.add(panel2, BorderLayout.WEST);
        mainPanel.setLayout(boxLayout);

        addGroupButtonEnter(authorisation, createEnter(ENTER_SIZE));
        mainPanel.add(login);
        addGroupButtonEnter(textLogin, createEnter(ENTER_SIZE));
        mainPanel.add(password);
        addGroupButtonEnter(textPassword, createEnter(ENTER_SIZE));
        mainPanel.add(host);
        addGroupButtonEnter(textHost, createEnter(ENTER_SIZE));
        mainPanel.add(port);
        addGroupButtonEnter(textPort, createEnter(ENTER_SIZE_AFTER_PORT));
        addGroupButtonEnter(register, createEnter(ENTER_SIZE_AFTER_REGISTER));
        mainPanel.add(createButton(Constants.SING_UP, new SignUp()));
        upPanel.setLayout(new BorderLayout());
        JMenuBar lang = createLanguage(Color.BLACK);
        upPanel.add(lang, BorderLayout.WEST);
        getFrame().repaint();
    }

    private void revalidate() {
        panel.removeAll();
        panel.revalidate();
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
        JButton signUp = new JButton(localisation(constants));
        signUp.setFont(STANDARD_FONT);
        signUp.setBackground(Color.gray);
        signUp.addActionListener(actionListener);
        return signUp;
    }

    private void initialization() {
        login = new JLabel(localisation(Constants.LOGIN));
        password = new JLabel(localisation(Constants.PASSWORD));
        host = new JLabel(localisation(Constants.HOST));
        port = new JLabel(localisation(Constants.PORT));
        authorisation = new JLabel(localisation(Constants.AUTHORISATION));
        register = new JButton(localisation(Constants.NOT_LOGIN_YET));
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

    public ClientApp getClientApp() {
        return clientApp;
    }

    private class SignUp implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            userHost = textHost.getText();
            userPort = textPort.getText();
            try {
                response = new Response(VariableParsing.toRightName(login.getText(), textLogin.getText(), getResourceBundle()), new String(textPassword.getPassword()), "");
                Response answer  = Client.connection(AuthorisationFrame.this);
                if (!(answer instanceof ResponseWithError)) {
                    goToHomeFrame();
                } else {
                    exception(localisation(Constants.ERROR) + " " + answer.getCommand());
                }
            } catch (VariableException ex) {
                exception(ex.getMessage());
            }
        }
        private void goToHomeFrame() {
            close();
            new HomeFrame(textLogin.getText(), getResourceBundle(), clientApp).run();
        }
    }

    private class Registration implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            isNewUser = true;
            authorisation.setText(localisation(Constants.REGISTRATION));
            mainPanel.remove(register);
            mainPanel.add(createEnter(ENTER_SIZE_AFTER_SING_UP));
            mainPanel.add(createButton(Constants.BACK, e1 -> {
                isNewUser = false;
                run();
            }));
            getFrame().repaint();
        }
    }
}
