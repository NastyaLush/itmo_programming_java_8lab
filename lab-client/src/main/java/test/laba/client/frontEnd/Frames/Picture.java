package test.laba.client.frontEnd.Frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Picture implements ActionListener {
    private final JFrame jFrame;
    private final JLabel userName;
    private final JLabel userPicture;

    public Picture(JFrame jFrame, JLabel userName, JLabel userPicture) {
        this.jFrame = jFrame;
        this.userName = userName;
        this.userPicture = userPicture;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame pictureFrame = new JFrame();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        pictureFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        pictureFrame.setSize(screenSize.width - 250, screenSize.height);
        pictureFrame.setLocationRelativeTo(null);
        pictureFrame.setMinimumSize(new Dimension(screenSize.width / 2, screenSize.height / 2));

        JPanel upPanel = new JPanel();
        pictureFrame.getContentPane().add(BorderLayout.NORTH, upPanel);
        upPanel.setPreferredSize(new Dimension(jFrame.getWidth(), jFrame.getHeight() / 10));
        upPanel.setLayout(new BoxLayout(upPanel, BoxLayout.X_AXIS));
        upPanel.setBackground(Color.BLACK);

        upPanel.add(Box.createRigidArea(new Dimension(screenSize.width - 500, 0)));
        upPanel.add(userName);
        upPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        upPanel.add(userPicture);
        JPanel activePanel = new JPanel();
        activePanel.add(new Grafics());


        pictureFrame.setLayout(new BorderLayout());
        pictureFrame.getContentPane().add(upPanel, BorderLayout.NORTH);
        pictureFrame.getContentPane().add(activePanel, BorderLayout.CENTER);


        pictureFrame.setVisible(true);
    }
}
