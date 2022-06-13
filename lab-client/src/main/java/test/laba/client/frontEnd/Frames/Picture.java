package test.laba.client.frontEnd.Frames;

import test.laba.client.frontEnd.HomeFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Picture extends JFrame implements ActionListener {
    private final HomeFrame homeFrame;
    private final JLabel userName;
    private final JFrame pictureFrame;
    private final Dimension screenSize;
    private final JPanel upPanel;

    public Picture(HomeFrame jFrame, JLabel userName, JLabel userPicture) {
        this.homeFrame = jFrame;
        this.userName = userName;
        this.pictureFrame = new JFrame();
        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.upPanel = new JPanel();
        SwingUtilities.invokeLater(()->{
            toDo();
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        pictureFrame.setVisible(true);
    }

    public void toDo(){
        pictureFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        pictureFrame.setSize(screenSize.width - 250, screenSize.height);
        pictureFrame.setLocationRelativeTo(null);
        pictureFrame.setMinimumSize(new Dimension(screenSize.width / 2, screenSize.height / 2));


        pictureFrame.getContentPane().add(BorderLayout.NORTH, upPanel);
        upPanel.setPreferredSize(new Dimension((int) homeFrame.screenSize.getWidth(), (int)homeFrame.screenSize.getHeight() / 10));
        upPanel.setLayout(new BoxLayout(upPanel, BoxLayout.X_AXIS));
        upPanel.setBackground(Color.BLACK);

        upPanel.add(Box.createRigidArea(new Dimension(screenSize.width - 500, 0)));
        upPanel.add(userName);
        upPanel.add(Box.createRigidArea(new Dimension(5, 0)));


        pictureFrame.setLayout(new BorderLayout());
        pictureFrame.getContentPane().add(upPanel, BorderLayout.NORTH);

        pictureFrame.getContentPane().add(new Grafics(homeFrame));
    }

}
