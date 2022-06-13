package test.laba.client;

import test.laba.common.responses.BasicResponse;

import javax.swing.*;
import java.awt.*;

public class Main extends JComponent {
    private static final double MULTI_FOR_UMBRELLA_HANDLE = 0.6;
    private static final int START_UMBRELLA_ROUND = 0;
    private static final int FINISH_UMBRELLA_ROUND = 180;
    private static final int THICKNESS_FOR_UMBRELLA_HANDLE = 10;
    private static final int THICKNESS_FOR_UMBRELLA_ROUND = 3;
    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        jFrame.setSize(new Dimension(800,800));
        jFrame.setLocation(500, 100);

        jFrame.getContentPane().add(new Main());
        jFrame.setVisible(true);
    }
    public void paint(Graphics g) {
        int x = 120;
        int y = 100;
        int widthOrHeight =303;
        Graphics2D graphics = (Graphics2D) g;
        Color color =Color.BLUE;

        final int startUmbrellaHandleX = x + (widthOrHeight / 2);
        final int startUmbrellaHandleY = y + (widthOrHeight / 2);
        final int finishUmbrellaHandleX = startUmbrellaHandleX;
        final int finishUmbrellaHandleY = (int) ((widthOrHeight * MULTI_FOR_UMBRELLA_HANDLE) + startUmbrellaHandleY);

        final int widthOrHeightUmbrellaHandle = widthOrHeight / 8;
        final int THICKNESS_FOR_UMBRELLA_HANDLE = widthOrHeight/20;
        final int THICKNESS_FOR_UMBRELLA_ROUND= THICKNESS_FOR_UMBRELLA_HANDLE/4;

        final int startUmbrellaHandleRoundX = startUmbrellaHandleX - widthOrHeight / 8;
        final int startUmbrellaHandleRoundY = finishUmbrellaHandleY - THICKNESS_FOR_UMBRELLA_HANDLE;

        final int finishUmbrellaHandleRound = -FINISH_UMBRELLA_ROUND;

        final int lineRoundUmbrellaHandleX = startUmbrellaHandleX + THICKNESS_FOR_UMBRELLA_HANDLE / 2;



        BasicStroke pen = new BasicStroke(THICKNESS_FOR_UMBRELLA_HANDLE);
        graphics.setColor(color);
        graphics.setStroke(pen);
        graphics.fillArc(x, y, widthOrHeight, widthOrHeight, START_UMBRELLA_ROUND, FINISH_UMBRELLA_ROUND);
        graphics.drawLine(startUmbrellaHandleX, startUmbrellaHandleY, finishUmbrellaHandleX, finishUmbrellaHandleY);
        graphics.drawArc(startUmbrellaHandleRoundX, startUmbrellaHandleRoundY, widthOrHeightUmbrellaHandle, widthOrHeightUmbrellaHandle, START_UMBRELLA_ROUND, finishUmbrellaHandleRound);

        graphics.setColor(Color.MAGENTA);
        graphics.setStroke(new BasicStroke(THICKNESS_FOR_UMBRELLA_ROUND));

        graphics.drawArc(x, y, widthOrHeight, widthOrHeight, START_UMBRELLA_ROUND, FINISH_UMBRELLA_ROUND);
        graphics.drawLine(lineRoundUmbrellaHandleX, startUmbrellaHandleY, lineRoundUmbrellaHandleX, finishUmbrellaHandleY);

    }

}
