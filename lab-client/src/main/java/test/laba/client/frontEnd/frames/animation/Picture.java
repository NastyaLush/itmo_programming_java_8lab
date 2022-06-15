package test.laba.client.frontEnd.frames.animation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import test.laba.client.frontEnd.frames.HomeFrame;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Picture extends JFrame implements ActionListener {
    private final HomeFrame homeFrame;
    private final JLabel userName;
    private final JFrame pictureFrame;
    private final Dimension screenSize;
    private final JPanel upPanel;
    private final int tensLesser = 10;
    private final int offsetFrame = 250;

    public Picture(HomeFrame jFrame, JLabel userName) {
        this.homeFrame = jFrame;
        this.userName = userName;
        this.pictureFrame = new JFrame();
        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.upPanel = new JPanel();
        SwingUtilities.invokeLater(this::createFrame);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        pictureFrame.setVisible(true);
    }

    public void createFrame() {
        pictureFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        pictureFrame.setSize(screenSize.width - offsetFrame, screenSize.height - offsetFrame);
        pictureFrame.setLocationRelativeTo(null);
        pictureFrame.setMinimumSize(new Dimension(screenSize.width / 2, screenSize.height / 2));


        pictureFrame.getContentPane().add(BorderLayout.NORTH, upPanel);
        upPanel.setPreferredSize(new Dimension((int) homeFrame.getScreenSize().getWidth(), (int) homeFrame.getScreenSize().getHeight() / tensLesser));
        upPanel.setLayout(new BorderLayout());
        upPanel.setBackground(Color.BLACK);

        upPanel.add(userName, BorderLayout.AFTER_LINE_ENDS);


        pictureFrame.setLayout(new BorderLayout());
        pictureFrame.getContentPane().add(upPanel, BorderLayout.NORTH);

        pictureFrame.getContentPane().add(new MyGraphics(homeFrame));
    }

}
