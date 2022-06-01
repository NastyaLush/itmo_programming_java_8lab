package test.laba.client.frontEnd;

import test.laba.common.responses.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class HomeFrame extends Frame{
    private final JPanel mainPanel = new JPanel();
    private final String login;
    private final JLabel userPicture = new JLabel();
    private JLabel userName = new JLabel();
    private final Color green = Color.getHSBColor((float) 0.40034366, (float) 0.8362069, (float) 0.9098039);

    private Response response;
    public HomeFrame(Condition condition, Lock lock, String login) {
        super(condition, lock);
        this.login = login;
    }

    @Override
    public void run() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(screenSize.width - 250, screenSize.height-150);
        jFrame.setLocationRelativeTo(null);
        jFrame.setMinimumSize(new Dimension(screenSize.width/2, screenSize.height/2));



        jFrame.setLayout(new BorderLayout());

        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));

        ImageIcon imageIcon = new ImageIcon("user1.png");
        userPicture.setIcon(imageIcon);

        userName.setText(login);
        userName.setForeground(Color.WHITE);
        userName.setFont(underLine(new Font("Safari", Font.CENTER_BASELINE, 38)));

        JButton manCost = new JButton("average of manufacture cost");
        manCost.setFont(new Font("Safari", Font.CENTER_BASELINE, 20));
        manCost.setBackground(Color.BLACK);
        manCost.setForeground(Color.WHITE);

        JButton groupCountingByPrice = new JButton("group counting by price");
        groupCountingByPrice.setFont(new Font("Safari", Font.CENTER_BASELINE, 20));
        groupCountingByPrice.setBackground(Color.BLACK);
        groupCountingByPrice.setForeground(Color.WHITE);

        JMenu lang = new JMenu("lang");
        lang.setPreferredSize(new Dimension(jFrame.getWidth()/16,250));
        lang.setFont(new Font("Safari", Font.CENTER_BASELINE, 20));
        JMenuItem rus = new JMenuItem("Russian");
        JMenuItem nor = new JMenuItem("Norwegian");
        JMenuItem fr = new JMenuItem("French");
        JMenuItem sp = new JMenuItem("Spanish");

        lang.add(rus);
        lang.add(nor);
        lang.add(fr);
        lang.add(sp);

        JMenuBar language = new JMenuBar();
        language.add(lang);
        language.setBackground(green);
        language.setForeground(Color.BLACK);


        JMenu sort = new JMenu("sort by");
        sort.setFont(new Font("Safari", Font.CENTER_BASELINE, 15));
        sort.setPreferredSize(new Dimension(100,250));
        sort.setForeground(Color.WHITE);
        JMenuItem aToZ = new JMenuItem("sort a to z");
        JMenuItem zToA = new JMenuItem("sort z to a");
        JMenuItem random = new JMenuItem("random");

        sort.add(aToZ);
        sort.add(zToA);
        sort.add(random);

        JMenuBar sortBy = new JMenuBar();
        sortBy.add(sort);
        sortBy.setBackground(Color.BLACK);
        sortBy.setForeground(Color.WHITE);

        JPanel leftPanel = new JPanel();
        JPanel hightPanel = new JPanel();
        JPanel downPAnel = new JPanel();

        hightPanel.setLayout( new BoxLayout(hightPanel,BoxLayout.X_AXIS));
        hightPanel.add(language,BorderLayout.WEST );
        hightPanel.add(Box.createRigidArea(new Dimension(5,0)));
        hightPanel.add(sortBy, BorderLayout.WEST);
        hightPanel.add(Box.createRigidArea(new Dimension(5,0)));
        hightPanel.add(groupCountingByPrice, BorderLayout.WEST);
        hightPanel.add(Box.createRigidArea(new Dimension(5,0)));
        hightPanel.add(manCost, BorderLayout.WEST);
        hightPanel.add(Box.createRigidArea(new Dimension(screenSize.width/3,0)));
        hightPanel.add(userName, BorderLayout.EAST);
        hightPanel.add(Box.createRigidArea(new Dimension(5,0)));
        hightPanel.add(userPicture, BorderLayout.NORTH);
/////////////////////////////////////
        JButton picture = new JButton();
        picture.setBackground(green);
        ImageIcon pictureIcon = new ImageIcon("picture.png");
        picture.setIcon(pictureIcon);
        picture.addActionListener(new Picture());

        JButton restart = new JButton();
        restart.setBackground(green);
        ImageIcon restartIcon = new ImageIcon("restart.png");
        restart.setIcon(restartIcon);

        JButton help = new JButton();
        help.setBackground(green);
        ImageIcon helpIcon = new ImageIcon("question.png");
        help.setIcon(helpIcon);

        JButton history = new JButton();
        history.setBackground(green);
        ImageIcon historyIcon = new ImageIcon("history.png");
        history.setIcon(historyIcon);

        JButton script = new JButton();
        script.setBackground(green);
        ImageIcon scriptIcon = new ImageIcon("script.png");
        script.setIcon(scriptIcon);

        JButton info = new JButton();
        info.setBackground(green);
        ImageIcon infoIcon = new ImageIcon("info.png");
        info.setIcon(infoIcon);

        leftPanel.setLayout( new BoxLayout(leftPanel,BoxLayout.Y_AXIS));
        leftPanel.add(picture);
        leftPanel.add(restart);
        leftPanel.add(help);
        leftPanel.add(info);
        leftPanel.add(history);
        leftPanel.add(script);

/////////////////

        JButton trash = new JButton();
        trash.setBackground(green);
        ImageIcon trashIcon = new ImageIcon("trash.png");
        trash.setIcon(trashIcon);
        trash.setPreferredSize(new Dimension(jFrame.getWidth()/16,0));

        JButton minus = new JButton();
        minus.setBackground(Color.white);
        ImageIcon minusIcon = new ImageIcon("minus.png");
        minus.setIcon(minusIcon);

        JButton plus = new JButton();
        plus.setBackground(Color.white);
        ImageIcon plusIcon = new ImageIcon("plus.png");
        plus.setIcon(plusIcon);


        downPAnel.setLayout( new BoxLayout(downPAnel,BoxLayout.X_AXIS));
        downPAnel.add(trash, BorderLayout.EAST);
        downPAnel.add(Box.createRigidArea(new Dimension(screenSize.width-700,0)));
        downPAnel.add(minus, BorderLayout.WEST);
        downPAnel.add(plus, BorderLayout.WEST);

        leftPanel.setBackground(green);
        hightPanel.setBackground(Color.BLACK);

        mainPanel.setPreferredSize(new Dimension(jFrame.getWidth()/2,jFrame.getHeight()/2));
        leftPanel.setPreferredSize(new Dimension(jFrame.getWidth()/16,jFrame.getHeight()));
        hightPanel.setPreferredSize(new Dimension(jFrame.getWidth(),jFrame.getHeight()/10));
        downPAnel.setPreferredSize(new Dimension(jFrame.getWidth(),jFrame.getHeight()/8));

        jFrame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        jFrame.getContentPane().add(BorderLayout.WEST, leftPanel);
        jFrame.getContentPane().add(BorderLayout.NORTH, hightPanel);
        jFrame.getContentPane().add(BorderLayout.SOUTH, downPAnel);
        //createTable();
        jFrame.repaint();
        jFrame.setVisible(true);
        createTable();
        jFrame.repaint();
        //jFrame.addWindowStateListener();
    }

    public void createTable(){

        TableModule tableModule = new TableModule();
        JTable table = new JTable(tableModule);
        JScrollPane tableScrollPane = new JScrollPane(table);
        //tableScrollPane.setPreferredSize();

        mainPanel.add(tableScrollPane);
    }

    private class Picture implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame pictureFrame = new JFrame();
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            pictureFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            pictureFrame.setSize(screenSize.width - 250, screenSize.height);
            pictureFrame.setLocationRelativeTo(null);
            pictureFrame.setMinimumSize(new Dimension(screenSize.width/2, screenSize.height/2));

            JPanel hightPanel = new JPanel();
            pictureFrame.getContentPane().add(BorderLayout.NORTH, hightPanel);
            hightPanel.setPreferredSize(new Dimension(jFrame.getWidth(),jFrame.getHeight()/10));
            hightPanel.setLayout(new BoxLayout(hightPanel, BoxLayout.X_AXIS));
            hightPanel.setBackground(Color.BLACK);

            hightPanel.add(Box.createRigidArea(new Dimension(screenSize.width-500,0)));
            hightPanel.add(userName);
            hightPanel.add(Box.createRigidArea(new Dimension(5,0)));
            hightPanel.add(userPicture);


            pictureFrame.setLayout(new BorderLayout());
            pictureFrame.getContentPane().add(hightPanel,BorderLayout.NORTH );
            pictureFrame.getContentPane().add(mainPanel,BorderLayout.CENTER );



            pictureFrame.setVisible(true);
        }
    }
    private class Help implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            response = new Response("help");
            lock.lock();
            condition.signal();
            try {
                condition.await();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            lock.unlock();

        }
    }
}
