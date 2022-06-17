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
    private static final int TENS_LESSER = 10;
    private static final int OFFSET_FRAME = 250;
    private final HomeFrame homeFrame;
    private final JLabel userName;
    private final JFrame pictureFrame;
    private final Dimension screenSize;
    private final JPanel upPanel;
    private MyGraphics graphics;

    public Picture(HomeFrame jFrame, JLabel userName) {
        this.homeFrame = jFrame;
        this.userName = userName;
        this.pictureFrame = new JFrame();
        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.upPanel = new JPanel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        createFrame();
        pictureFrame.setVisible(true);
    }

    public void createFrame() {
        if(graphics != null) {
            pictureFrame.getContentPane().remove(graphics);
            pictureFrame.revalidate();
        }
        pictureFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        pictureFrame.setSize(screenSize.width - OFFSET_FRAME, screenSize.height - OFFSET_FRAME);
        pictureFrame.setLocationRelativeTo(null);
        pictureFrame.setMinimumSize(new Dimension(screenSize.width / 2, screenSize.height / 2));


        pictureFrame.getContentPane().add(BorderLayout.NORTH, upPanel);
        upPanel.setPreferredSize(new Dimension((int) homeFrame.getScreenSize().getWidth(), (int) homeFrame.getScreenSize().getHeight() / TENS_LESSER));
        upPanel.setLayout(new BorderLayout());
        upPanel.setBackground(Color.BLACK);

        upPanel.add(userName, BorderLayout.AFTER_LINE_ENDS);


        pictureFrame.setLayout(new BorderLayout());
        pictureFrame.getContentPane().add(upPanel, BorderLayout.NORTH);
        graphics = new MyGraphics(homeFrame);
        pictureFrame.getContentPane().add(graphics);
    }

}
