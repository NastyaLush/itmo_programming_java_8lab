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


public class Frame extends AbstractFrame implements Runnable{
    private JLabel login;
    private final JTextField textLogin = new JTextField(10);
    private JLabel password;
    private final JPasswordField textPassword = new JPasswordField(10);
    private JLabel host;
    private final JTextField textHost = new JTextField("localhost",10);
    private JLabel port;
    private final JTextField textPort = new JTextField(10);
    private JLabel authorisation;
    private JButton register;
    private final JPanel mainPanel = new JPanel();
    private String userHost = null;
    private String userPort = null;
    protected Response response = null;
    protected final Condition condition;
    protected final Lock lock;
    private boolean isNewUser = false;
    protected boolean isException = false;



    public Frame(Condition condition, Lock lock/*, String name*/) {
        super(new JFrame(/*name*/), Local.getResourceBundleDeafult());
        this.lock = lock;
        this.condition = condition;
    }

    private void start() {
        inisialization();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(screenSize.width - 250, screenSize.height - 250);
        jFrame.setLocationRelativeTo(null);
        jFrame.setMinimumSize(new Dimension(screenSize.width/2, screenSize.height/2));


        authorisation.setFont(underLine(new Font("Safari", Font.ITALIC, 50)));
        authorisation.setHorizontalAlignment(JLabel.LEFT);


        Font labelFont = new Font("Safari", Font.CENTER_BASELINE, 18);
        login.setForeground(Color.gray);
        login.setFont(labelFont);
        login.setSize(23, 45);
        textLogin.setPreferredSize(new Dimension(23,24));


        password.setForeground(Color.gray);
        password.setFont(labelFont);


        host.setForeground(Color.gray);
        host.setFont(labelFont);
        textHost.setBounds(2,3,4,5);

        port.setForeground(Color.gray);
        port.setFont(labelFont);


        JButton signUp = new JButton(localisation(resourceBundle, Constants.SING_UP));
        signUp.setFont(new Font("Safari", Font.CENTER_BASELINE, 15));
        signUp.setBackground(Color.gray);
        signUp.setPreferredSize(new Dimension(10000,40));
        signUp.addActionListener(new SignUp());

        register.setBackground(Color.lightGray);
        register.setFont(underLine(new Font("Arial", Font.ITALIC, 13)));
        register.addActionListener(new Registration());

        jFrame.setLayout(new BorderLayout());

        BoxLayout boxLayout = new BoxLayout(mainPanel,BoxLayout.Y_AXIS);
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

        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        JPanel panel5 = new JPanel();

        panel2.setBackground(Color.BLACK);
        panel5.setBackground(Color.PINK);
        panel3.setBackground(Color.BLUE);
        panel4.setBackground(Color.GREEN);

        mainPanel.setPreferredSize(new Dimension(jFrame.getWidth()/2,jFrame.getHeight()/2));
        panel2.setPreferredSize(new Dimension(jFrame.getWidth()/4,jFrame.getHeight()));
        panel3.setPreferredSize(new Dimension(jFrame.getWidth(),jFrame.getHeight()/8));
        panel4.setPreferredSize(new Dimension(jFrame.getWidth()/2,jFrame.getHeight()));
        panel5.setPreferredSize(new Dimension(jFrame.getWidth()/4,jFrame.getHeight()/8));

        jFrame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        jFrame.getContentPane().add(BorderLayout.WEST, panel2);
        jFrame.getContentPane().add(BorderLayout.NORTH, panel3);
        jFrame.getContentPane().add(BorderLayout.EAST, panel4);
        jFrame.getContentPane().add(BorderLayout.SOUTH, panel5);
        jFrame.setVisible(true);
        //jFrame.addWindowStateListener();
    }

    private void inisialization(){
        System.out.println(resourceBundle.getString(Constants.AUTHORISATION.getString()));
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
            System.out.println(textLogin.getText());
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

    @Override
    public void run() {
        start();
    }
    public void prepeareAnswer(Response response, boolean isException){
        this.response = response;
        this.isException = isException;
    }
}
