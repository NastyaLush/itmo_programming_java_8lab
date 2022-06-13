package test.laba.client.frontEnd.Frames;


import test.laba.client.util.Constants;
import test.laba.client.frontEnd.Local;
import test.laba.common.responses.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;


public class Frame extends LanguageInterface implements Runnable{
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
    private final JPanel upPanel = new JPanel();
    private String userHost = null;
    private String userPort = null;
    protected Response response = null;
    protected final Condition condition;
    protected final Lock lock;
    private JMenuBar lang;
    private boolean isNewUser = false;
    protected boolean isException = false;



    public Frame(Condition condition, Lock lock/*, String name*/) {
        super(new JFrame(/*name*/), Local.getResourceBundleDeafult());
        this.lock = lock;
        this.condition = condition;
        //start();
    }

    public void run() {
        upPanel.removeAll();
        mainPanel.removeAll();
        inisialization();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLocation(100,100);
        jFrame.setMinimumSize(new Dimension(screenSize.width /2, screenSize.height-300));
        jFrame.setMaximumSize(new Dimension(screenSize.width/2, screenSize.height/2));


        authorisation.setFont(underLine(new Font("Safari", Font.ITALIC, 50)));
        authorisation.setHorizontalAlignment(JLabel.LEFT);


        Font labelFont = new Font("Safari", Font.CENTER_BASELINE, 18);
        login.setForeground(Color.gray);
        login.setFont(labelFont);
        login.setSize(23, 45);

        password.setForeground(Color.gray);
        password.setFont(labelFont);


        host.setForeground(Color.gray);
        host.setFont(labelFont);
        textHost.setBounds(2,3,4,5);

        port.setForeground(Color.gray);
        port.setFont(labelFont);


        JButton signUp = createButton(Constants.SING_UP, new SignUp());

        register.setBackground(Color.lightGray);
        register.setFont(underLine(new Font("Arial", Font.ITALIC, 13)));
        register.addActionListener(new Registration());

        jFrame.setLayout(new BorderLayout());

        BoxLayout boxLayout = new BoxLayout(mainPanel,BoxLayout.Y_AXIS);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(mainPanel, BorderLayout.WEST);
        mainPanel.setLayout(boxLayout);



        mainPanel.add(authorisation);
        mainPanel.add(Box.createRigidArea(new Dimension(0,50)));
        mainPanel.add(login);
        mainPanel.add(textLogin);
        mainPanel.add(Box.createRigidArea(new Dimension(0,50)));
        mainPanel.add(password);
        mainPanel.add(textPassword);
        mainPanel.add(Box.createRigidArea(new Dimension(0,50)));
        mainPanel.add(host);
        mainPanel.add(textHost);
        mainPanel.add(Box.createRigidArea(new Dimension(0,50)));
        mainPanel.add(port);
        mainPanel.add(textPort);
        mainPanel.add(Box.createRigidArea(new Dimension(0,15)));
        mainPanel.add(register);
        mainPanel.add(Box.createRigidArea(new Dimension(0,25)));
        mainPanel.add(signUp);

        JPanel leftPanel = new JPanel();
        JPanel downPanel = new JPanel();

        upPanel.setLayout(new BorderLayout());
        lang = createLanguage(Color.BLACK);
        upPanel.add(lang, BorderLayout.WEST);

        mainPanel.setPreferredSize(new Dimension(415,417));
        leftPanel.setPreferredSize(new Dimension(jFrame.getWidth()/8,jFrame.getHeight()));
        upPanel.setPreferredSize(new Dimension(jFrame.getWidth()/8,jFrame.getHeight()/14));
        downPanel.setPreferredSize(new Dimension(jFrame.getWidth()/8,jFrame.getHeight()/8));

        jFrame.getContentPane().add(BorderLayout.CENTER, panel);
        jFrame.getContentPane().add(BorderLayout.WEST, leftPanel);
        jFrame.getContentPane().add(BorderLayout.NORTH, upPanel);
        jFrame.getContentPane().add(BorderLayout.SOUTH, downPanel);
        jFrame.setVisible(true);
    }
    private JButton createButton(Constants constants, ActionListener actionListener){
        JButton signUp = new JButton(localisation(resourceBundle, constants));
        signUp.setFont(new Font("Safari", Font.CENTER_BASELINE, 15));
        signUp.setBackground(Color.gray);
        signUp.addActionListener(actionListener);
        return signUp;
    }

    private void inisialization(){
        login = new JLabel(localisation(resourceBundle, Constants.LOGIN), 10);
        password = new JLabel(localisation(resourceBundle, Constants.PASSWORD));
        host = new JLabel(localisation(resourceBundle, Constants.HOST));
        port = new JLabel(localisation(resourceBundle, Constants.PORT));
        authorisation = new JLabel(localisation(resourceBundle, Constants.AUTHORISATION));
        register = new JButton(localisation(resourceBundle, Constants.NOT_LOGIN_YET));
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
            authorisation.setText(localisation(resourceBundle, Constants.REGISTRATION));
            mainPanel.remove(register);
            upPanel.remove(lang);
            mainPanel.add(Box.createRigidArea(new Dimension(0,5)));
            mainPanel.add(createButton(Constants.BACK, e1 -> run()));
            jFrame.repaint();
        }
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

    public JFrame getjFrame() {
        return jFrame;
    }

    public boolean isNewUser() {
        return isNewUser;
    }

    public void prepeareAnswer(Response response, boolean isException){
        this.response = response;
        this.isException = isException;
    }
}
