package test.laba.client.frontEnd.frames.animation;

import test.laba.client.frontEnd.frames.HomeFrame;
import test.laba.client.frontEnd.frames.changeProductFrames.ChangeProductFrameAnimation;
import test.laba.common.dataClasses.Product;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Grafics extends JComponent implements ActionListener, MouseListener {
    private static final double MULTI_FOR_UMBRELLA_HANDLE = 0.6;
    private static final int START_UMBRELLA_ROUND = 0;
    private static final int FINISH_UMBRELLA_ROUND = 180;
    private static final int MAX_RGB = 256;
    private static final int MAX_UMBRELLA = Toolkit.getDefaultToolkit().getScreenSize().height / 4;
    private int maxX = this.getSize().width;
    private static final int OFFSET_X = 233;
    private int maxY = this.getSize().height;
    private static final int MIN_SIZE_PRICE = 40;
    private static final int MIN_SIZE_X = -233;
    private static final int MIN_SIZE_Y = 0;
    private final HomeFrame homeFrame;
    private HashMap<Long, Umbrella> newCollectionInsert = new HashMap<>();
    private HashMap<Long, Umbrella> collection = new HashMap<>();
    javax.swing.Timer timer = new Timer(5,this);


    public Grafics(HomeFrame homeFrame) {
        this.homeFrame = homeFrame;
        addMouseListener(this);
    }

    public void paint(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        
        graphics.drawLine(this.getSize().width/2, 0, this.getSize().width/2, this.getSize().height);
        graphics.drawLine(0, this.getSize().height/2, this.getSize().width, this.getSize().height/2);


        if(collection != null){
            sortOrdered(collection).stream().forEach(e ->{
                e.paint(graphics);
            });
            Iterator<Map.Entry<Long, Umbrella>> iterator = collection.entrySet().iterator();
            while (iterator.hasNext()){
                if(iterator.next().getValue().getCondition() == Condition.DEAD){
                    iterator.remove();
                }
            }
        }

        timer.start();
        update();

    }


    private List<Umbrella> sortOrdered(HashMap<Long,Umbrella> map) {
        return map.entrySet()
                .stream()
                .map(e -> e.getValue())
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toList());
    }
    private List<Umbrella> sort(HashMap<Long,Umbrella> map) {
        return map.entrySet()
                .stream()
                .map(e -> e.getValue())
                .collect(Collectors.toList());
    }

    private void update(){
        newCollectionInsert.clear();
        if(homeFrame.getGraphicMap() != null) {
            homeFrame.getGraphicMap().entrySet().stream().forEach(e -> {
                newCollectionInsert.put(e.getKey(), new Umbrella(e.getValue()));
            });
        }

        newCollectionInsert.entrySet().stream().forEach(
                e->{
                    if(!collection.containsKey(e.getKey())){
                        e.getValue().setCondition(Condition.INSERT);
                        collection.put(e.getKey(), e.getValue());
                    } else {
                        if (e.getValue().getProduct().compareTo(collection.get(e.getKey()).getProduct())!=0) {
                            collection.get(e.getKey()).setCondition(Condition.UPDATE);
                            collection.get(e.getKey()).setProduct(e.getValue().getProduct());
                        }
                    }
                }
        );
        collection.entrySet().stream().forEach(e ->{
            if(!newCollectionInsert.containsKey(e.getKey())){
                e.getValue().setCondition(Condition.REMOVE);
            }
        });
    }

    private void updateSize(){
        maxX = this.getSize().width/2;
        maxY = this.getSize().height/2;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        sort(collection).stream().filter(e ->e.contains(mouseEvent.getX(),mouseEvent.getY())).limit(1).forEach(e ->
        {
            long key = collection.entrySet().stream().filter(e2 -> e2.getValue().equals(e)).findFirst().get().getKey();
                new ChangeProductFrameAnimation(homeFrame.getResourceBundle(), e.getProduct(), key) {
                    @Override
                    protected void addRemoveListener() {
                        homeFrame.treatmentAnimation(this::createResponse, this.jFrame);
                    }
                    private void treatmentResponse(){
                        homeFrame.treatmentAnimation(this::createUpdateResponse, this.jFrame);
                    }
                    @Override
                    protected void addOkListener() {
                        ok.addActionListener(e1 -> treatmentResponse());
                    }
                }.actionPerformed(new ActionEvent(this,5,"tr"));
        });
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

    private class Umbrella extends JComponent implements Comparable<Umbrella> {
        public final int colorWhite = 255;
        private Integer moveX = 0;
        private int productX;
        private Condition condition = Condition.SHOW;
        private int y;
        private Color color;
        private int widthOrHeight;
        private int trembling =100;
        private Product product;
        private HashMap<Condition, IFunction> function = new HashMap<>();

        private Arc2D umbrellaRound;
        private Arc2D umbrellaRoundSmall;
        private Line2D umbrellaHandle;
        private Arc2D umbrellaRoundContur;
        private Line2D umbrellaHandleContur;

        public Umbrella(Product product) {
            this.product = product;
            inisialization(product);
            function.put(Condition.SHOW,this::draw);
            function.put(Condition.INSERT,this::insert);
            function.put(Condition.REMOVE,this::remove);
            function.put(Condition.UPDATE,this::update);
            function.put(Condition.DEAD,this::dead);

           // addMouseListener(this);
        }
        private void inisialization(Product product){
            updateSize();
            this.productX =check(product.getCoordinates().getX(), MIN_SIZE_X, maxX, OFFSET_X);
            this.y= check(Math.round(product.getCoordinates().getY()), MIN_SIZE_Y, maxY);
            this.widthOrHeight = check(Math.toIntExact(product.getPrice()), MIN_SIZE_PRICE, MAX_UMBRELLA);
            this.color = createColor(product.getOwnerID().hashCode());
        }

        @Override
        protected void paintComponent(Graphics g) {
            function.get(condition).drawing((Graphics2D) g);
        }


        public void draw(Graphics2D graphics) {
            final int startUmbrellaHandleX = moveX + (widthOrHeight / 2);
            final int startUmbrellaHandleY = y + (widthOrHeight / 2);
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
            umbrellaRound = new Arc2D.Float(moveX, y,widthOrHeight,widthOrHeight, START_UMBRELLA_ROUND, FINISH_UMBRELLA_ROUND, Arc2D.PIE);
            graphics.fill(umbrellaRound);
            umbrellaHandle = new Line2D.Float(startUmbrellaHandleX, startUmbrellaHandleY, finishUmbrellaHandleX, finishUmbrellaHandleY);
            graphics.draw(umbrellaHandle);
            umbrellaRoundSmall = new Arc2D.Float(startUmbrellaHandleRoundX, startUmbrellaHandleRoundY, widthOrHeightUmbrellaHandle, widthOrHeightUmbrellaHandle, START_UMBRELLA_ROUND, finishUmbrellaHandleRound, Arc2D.OPEN);
            graphics.draw(umbrellaRoundSmall);
            graphics.setColor(Color.WHITE);
            graphics.setStroke(new BasicStroke(thicknessForUmbrellaRound));

            umbrellaRoundContur = new Arc2D.Float(moveX, y, widthOrHeight, widthOrHeight, START_UMBRELLA_ROUND, FINISH_UMBRELLA_ROUND, Arc2D.PIE);
            umbrellaHandleContur = new Line2D.Float(lineRoundUmbrellaHandleX, startUmbrellaHandleY, lineRoundUmbrellaHandleX, finishUmbrellaHandleY);
            graphics.draw(umbrellaHandleContur);
            graphics.draw(umbrellaRoundContur);
        }

        @Override
        public boolean contains(int x, int y) {
            return umbrellaRound.contains(x,y) ||
                    umbrellaRoundSmall.contains(x,y) ||
                    umbrellaHandle.contains(x,y) ||
                    umbrellaRoundContur.contains(x,y) ||
                    umbrellaHandleContur.contains(x,y);
        }

        public void paint(Graphics graphics){
            super.paint(graphics);
            function.get(condition).drawing((Graphics2D) graphics);
        }
        private void update(Graphics2D graphics){
            if(trembling!=0) {
                changeColor();
                moveX += (int) Math.pow(-1, trembling--);
                y += (int) Math.pow(-1, trembling);
                widthOrHeight += (int) Math.pow(-1, trembling);
                draw(graphics);
            } else {
                condition = Condition.SHOW;
                trembling =100;
                inisialization(product);
            }


        }
        public void insert(Graphics2D graphics){
            if(productX > moveX){
                moveX++;
                draw(graphics);
                color = createColor(moveX.hashCode());
            } else {
                condition = Condition.SHOW;
                color= createColor(product.getOwnerID().hashCode());
            }
        }
        public void remove(Graphics2D graphics2D){
            if(!changeColor()){
                widthOrHeight += color.getGreen()%2;
                draw(graphics2D);

            } else {
                condition = Condition.DEAD;
            }

        }
        public void dead(Graphics2D graphics2D){}
        private boolean changeColor(){
            color = new Color(ChangeColor(color.getRed()), ChangeColor(color.getGreen()), ChangeColor(color.getBlue()));
            return color.getRed()==color.getBlue() && colorWhite==color.getGreen();
        }
        private int ChangeColor(int color){
            return (color< colorWhite)? ++color: color;
        }
        private Color createColor(int hash) {
            Random random = new Random(hash);
            return new Color(
                    random.nextInt(MAX_RGB),
                    random.nextInt(MAX_RGB),
                    random.nextInt(MAX_RGB)
            );
        }

        private int check(int price, int min, int max) {
            return (price < min) ? (price<0)?0:price + min : ((price>max) ? max:price);
        }
        private int check(int price, int min, int max, int offset) {
            price+=offset;
            return check(price,min,max);
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
            return "Umbrella{" +
                    "moveX=" + moveX +
                    ", y=" + y +
                    ", widthOrHeight=" + widthOrHeight +
                    ", product=" + product.getId() +
                    '}';
        }

        public int getProductX() {
            return productX;
        }

        @Override
        public int getY() {
            return y;
        }

        public int compareTo(Umbrella o) {
            return Comparator.comparing(Umbrella::getWidthOrHeight).thenComparing(Umbrella::getY).thenComparing(Umbrella::getProductX).compare(this, o);
        }

    }
    private interface IFunction{
        void drawing(Graphics2D graphics2D);
    }
}
