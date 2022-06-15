package test.laba.client.frontEnd.frames.animation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;
import javax.swing.JComponent;
import test.laba.client.frontEnd.frames.HomeFrame;
import test.laba.client.frontEnd.frames.changeProductFrames.ChangeProductFrameAnimation;
import test.laba.common.dataClasses.Product;

import javax.swing.Timer;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.util.List;
import java.util.stream.Collectors;

public class MyGraphics extends JComponent implements ActionListener, MouseListener {
    private static final double MULTI_FOR_UMBRELLA_HANDLE = 0.6;
    private static final int START_UMBRELLA_ROUND = 0;
    private static final int FINISH_UMBRELLA_ROUND = 180;
    private static final int MAX_RGB = 256;
    private static final int MAX_UMBRELLA = Toolkit.getDefaultToolkit().getScreenSize().height / 4;
    private int maxX = this.getSize().width;
    private final int offsetX = 233;
    private int maxY = this.getSize().height;
    private final int minSizePrice = 40;
    private final int minSizeX = -233;
    private final int minSizeY = 0;
    private final int updateTime = 5;
    private final HomeFrame homeFrame;
    private final HashMap<Long, Umbrella> newCollectionInsert = new HashMap<>();
    private final HashMap<Long, Umbrella> collection = new HashMap<>();
    private final javax.swing.Timer timer = new Timer(updateTime, this);


    public MyGraphics(HomeFrame homeFrame) {
        this.homeFrame = homeFrame;
        addMouseListener(this);
    }

    @Override
    public void paint(java.awt.Graphics g) {
        Graphics2D graphics = (Graphics2D) g;

        graphics.drawLine(this.getSize().width / 2, 0, this.getSize().width / 2, this.getSize().height);
        graphics.drawLine(0, this.getSize().height / 2, this.getSize().width, this.getSize().height / 2);


        if (collection != null) {
            sortOrdered(collection).forEach(e -> e.paint(graphics));
            collection.entrySet().removeIf(longUmbrellaEntry -> longUmbrellaEntry.getValue().getCondition() == Condition.DEAD);
        }

        timer.start();
        update();

    }


    private List<Umbrella> sortOrdered(HashMap<Long, Umbrella> map) {
        return map.values()
                .stream()
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toList());
    }

    private List<Umbrella> sort(HashMap<Long, Umbrella> map) {
        return new ArrayList<>(map.values());
    }

    private void update() {
        newCollectionInsert.clear();
        if (homeFrame.getGraphicMap() != null) {
            homeFrame.getGraphicMap().forEach((key, value) -> newCollectionInsert.put(key, new Umbrella(value)));
        }

        newCollectionInsert.forEach((key, value) -> {
            if (! collection.containsKey(key)) {
                value.setCondition(Condition.INSERT);
                collection.put(key, value);
            } else {
                if (value.getProduct().compareTo(collection.get(key).getProduct()) != 0) {
                    collection.get(key).setCondition(Condition.UPDATE);
                    collection.get(key).setProduct(value.getProduct());
                }
            }
        });
        collection.forEach((key, value) -> {
            if (! newCollectionInsert.containsKey(key)) {
                value.setCondition(Condition.REMOVE);
            }
        });
    }

    private void updateSize() {
        maxX = this.getSize().width / 2;
        maxY = this.getSize().height / 2;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        sort(collection).stream().filter(e -> e.contains(mouseEvent.getX(), mouseEvent.getY())).limit(1).forEach(e -> {
            long key = collection.entrySet().stream().filter(e2 -> e2.getValue().equals(e)).findFirst().get().getKey();
            createUpdateFrame(e, key);
        });
    }
    public void createUpdateFrame(Umbrella umbrella, Long key) {
        new ChangeProductFrameAnimation(homeFrame.getResourceBundle(), umbrella.getProduct(), key) {
            @Override
            protected void addRemoveListener() {
                homeFrame.treatmentAnimation(this::createResponse, this.getFrame());
            }
            private void treatmentResponse() {
                homeFrame.treatmentAnimation(this::createUpdateResponse, this.getFrame());
            }
            @Override
            protected void addOkListener() {
                getOk().addActionListener(e1 -> treatmentResponse());
            }
        }.actionPerformed(new ActionEvent(this, updateTime, "showUpdate"));
    }
    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private final class Umbrella extends JComponent implements Comparable<Umbrella> {
        public final int colorWhite = 255;
        private Integer moveX = 0;
        private int productX;
        private Condition condition = Condition.SHOW;
        private int moveY;
        private Color color;
        private int widthOrHeight;
        private int trembling;
        private final int tremblingConstantInteger = 100;
        private Product product;
        private final HashMap<Condition, IFunction> function = new HashMap<>();
        private Arc2D umbrellaRound;
        private Arc2D umbrellaRoundSmall;
        private Line2D umbrellaHandle;
        private Arc2D umbrellaRoundContour;
        private Line2D umbrellaHandleContour;

        private Umbrella(Product product) {
            initialization(product);
            this.product = product;
            function.put(Condition.SHOW, this::draw);
            function.put(Condition.INSERT, this::insert);
            function.put(Condition.REMOVE, this::remove);
            function.put(Condition.UPDATE, this::update);
            function.put(Condition.DEAD, this::dead);
        }

        private void initialization(Product newProduct) {
            updateSize();
            this.productX = check(newProduct.getCoordinates().getX(), minSizeX, maxX, offsetX);
            this.moveY = check(Math.round(newProduct.getCoordinates().getY()), minSizeY, maxY);
            this.widthOrHeight = check(Math.toIntExact(newProduct.getPrice()), minSizePrice, MAX_UMBRELLA);
            this.color = createColor(newProduct.getOwnerID().hashCode());
        }

        @Override
        protected void paintComponent(java.awt.Graphics g) {
            function.get(condition).drawing((Graphics2D) g);
        }


        public void draw(Graphics2D graphics) {
            final int startUmbrellaHandleX = moveX + (widthOrHeight / 2);
            final int startUmbrellaHandleY = moveY + (widthOrHeight / 2);
            final int finishUmbrellaHandleX = startUmbrellaHandleX;
            final int finishUmbrellaHandleY = (int) ((widthOrHeight * MULTI_FOR_UMBRELLA_HANDLE) + startUmbrellaHandleY);

            final int widthOrHeightUmbrellaHandle = widthOrHeight / 8;
            final int thicknessForUmbrellaHandle = widthOrHeight / 20;
            final int thicknessForUmbrellaRound = thicknessForUmbrellaHandle / 4;

            final int startUmbrellaHandleRoundX = startUmbrellaHandleX - widthOrHeight / 8;
            final int startUmbrellaHandleRoundY = finishUmbrellaHandleY - thicknessForUmbrellaHandle;

            final int finishUmbrellaHandleRound = -FINISH_UMBRELLA_ROUND;

            final int lineRoundUmbrellaHandleX = startUmbrellaHandleX + thicknessForUmbrellaHandle / 2;


            graphics.setColor(color);
            graphics.setStroke(new BasicStroke(thicknessForUmbrellaHandle));
            umbrellaRound = new Arc2D.Float(moveX, moveY, widthOrHeight, widthOrHeight, START_UMBRELLA_ROUND, FINISH_UMBRELLA_ROUND, Arc2D.PIE);
            graphics.fill(umbrellaRound);
            umbrellaHandle = new Line2D.Float(startUmbrellaHandleX, startUmbrellaHandleY, finishUmbrellaHandleX, finishUmbrellaHandleY);
            graphics.draw(umbrellaHandle);
            umbrellaRoundSmall = new Arc2D.Float(startUmbrellaHandleRoundX, startUmbrellaHandleRoundY, widthOrHeightUmbrellaHandle, widthOrHeightUmbrellaHandle, START_UMBRELLA_ROUND, finishUmbrellaHandleRound, Arc2D.OPEN);
            graphics.draw(umbrellaRoundSmall);
            graphics.setColor(Color.WHITE);
            graphics.setStroke(new BasicStroke(thicknessForUmbrellaRound));

            umbrellaRoundContour = new Arc2D.Float(moveX, moveY, widthOrHeight, widthOrHeight, START_UMBRELLA_ROUND, FINISH_UMBRELLA_ROUND, Arc2D.PIE);
            umbrellaHandleContour = new Line2D.Float(lineRoundUmbrellaHandleX, startUmbrellaHandleY, lineRoundUmbrellaHandleX, finishUmbrellaHandleY);
            graphics.draw(umbrellaHandleContour);
            graphics.draw(umbrellaRoundContour);
        }

        @Override
        public boolean contains(int x, int y) {
            return umbrellaRound.contains(x, y) || umbrellaRoundSmall.contains(x, y) || umbrellaHandle.contains(x, y) || umbrellaRoundContour.contains(x, y) || umbrellaHandleContour.contains(x, y);
        }

        @Override
        public void paint(java.awt.Graphics graphics) {
            super.paint(graphics);
            function.get(condition).drawing((Graphics2D) graphics);
        }

        private void update(Graphics2D graphics) {
            if (trembling != 0) {
                changeColor();
                moveX += (int) Math.pow(-1, trembling--);
                moveY += (int) Math.pow(-1, trembling);
                widthOrHeight += (int) Math.pow(-1, trembling);
                draw(graphics);
            } else {
                condition = Condition.SHOW;
                trembling = tremblingConstantInteger;
                initialization(product);
            }


        }

        public void insert(Graphics2D graphics) {
            if (productX > moveX) {
                moveX++;
                draw(graphics);
                color = createColor(moveX.hashCode());
            } else {
                condition = Condition.SHOW;
                color = createColor(product.getOwnerID().hashCode());
            }
        }

        public void remove(Graphics2D graphics2D) {
            if (!changeColor()) {
                widthOrHeight += color.getGreen() % 2;
                draw(graphics2D);

            } else {
                condition = Condition.DEAD;
            }

        }

        public void dead(Graphics2D graphics2D) {
        }

        private boolean changeColor() {
            color = new Color(colorPaler(color.getRed()), colorPaler(color.getGreen()), colorPaler(color.getBlue()));
            return color.getRed() == color.getBlue() && colorWhite == color.getGreen();
        }

        private int colorPaler(int newColor) {
            int createNewColor = newColor;
            return (createNewColor < colorWhite) ? ++createNewColor : createNewColor;
        }

        private Color createColor(int hash) {
            Random random = new Random(hash);
            return new Color(
                    random.nextInt(MAX_RGB),
                    random.nextInt(MAX_RGB),
                    random.nextInt(MAX_RGB)
            );
        }

        private int check(int checkVariable, int min, int max) {
            return (checkVariable < min) ? (checkVariable < 0) ? 0 : checkVariable + min : (Math.min(checkVariable, max));
        }

        private int check(int checkVariable, int min, int max, int offset) {
            return check(checkVariable + offset, min, max);
        }

        public Product getProduct() {
            return product;
        }


        public void setCondition(Condition condition) {
            this.condition = condition;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public Condition getCondition() {
            return condition;
        }

        public int getWidthOrHeight() {
            return widthOrHeight;
        }

        @Override
        public String toString() {
            return "Umbrella{"
                    + "moveX=" + moveX
                    + ", y=" + moveY
                    + ", widthOrHeight=" + widthOrHeight
                    + ", product=" + product.getId()
                    + '}';
        }

        public int getProductX() {
            return productX;
        }

        @Override
        public int getY() {
            return moveY;
        }

        @Override
        public int compareTo(Umbrella o) {
            return Comparator.comparing(Umbrella::getWidthOrHeight).thenComparing(Umbrella::getY).thenComparing(Umbrella::getProductX).compare(this, o);
        }

    }

    private interface IFunction {
        void drawing(Graphics2D graphics2D);
    }
}
