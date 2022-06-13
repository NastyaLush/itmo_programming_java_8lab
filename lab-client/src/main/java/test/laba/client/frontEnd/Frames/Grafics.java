package test.laba.client.frontEnd.Frames;

import test.laba.client.frontEnd.HomeFrame;
import test.laba.common.dataClasses.Coordinates;
import test.laba.common.dataClasses.Product;
import test.laba.common.dataClasses.UnitOfMeasure;
import test.laba.common.exception.CreateError;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Grafics extends JComponent implements ActionListener {
    private static final double MULTI_FOR_UMBRELLA_HANDLE = 0.6;
    private static final int START_UMBRELLA_ROUND = 0;
    private static final int FINISH_UMBRELLA_ROUND = 180;
    private static final int MAX_RGB = 256;
    private static final int MAX_UMBRELLA = Toolkit.getDefaultToolkit().getScreenSize().height / 4;
    private static final int MAX_X = Toolkit.getDefaultToolkit().getScreenSize().width;
    private static final int OFFSET_X = 233;
    private static final int MAX_Y = Toolkit.getDefaultToolkit().getScreenSize().height;
    private static final int MIN_SIZE_PRICE = 40;
    private static final int MIN_SIZE_X = -233;
    private static final int MIN_SIZE_Y = 0;
    private final HomeFrame homeFrame;
    private HashMap<Long, Umbrella> newCollectionInsert = new HashMap<>();
    private HashMap<Long, Umbrella> collection = new HashMap<>();
    javax.swing.Timer timer = new Timer(5,this);

    public Grafics(HomeFrame homeFrame) {
        this.homeFrame = homeFrame;
    }

    public void paint(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;

        graphics.drawLine(homeFrame.screenSize.width / 2, 0, homeFrame.screenSize.width / 2, homeFrame.screenSize.height);
        graphics.drawLine(0, homeFrame.screenSize.height / 2, homeFrame.screenSize.width, homeFrame.screenSize.height / 2);


        if(collection != null){
            sort(collection).stream().forEach(e ->{
                e.draw(graphics);
                //System.out.println(e.getValue().getCondition()+ " " + e.getKey());
            });
            Iterator<Map.Entry<Long, Umbrella>> iterator = collection.entrySet().iterator();
            while (iterator.hasNext()){
                if(iterator.next().getValue().getCondition() == Condition.DEAD){
                    iterator.remove();
                }
            }
        }

        timer.start();
        paintCollection();
        update();
        //changeAll(graphics);

    }
    private void paintCollection(){
        collection.entrySet().stream().forEach(e ->{
            //e.getValue().draw(g);
        });
    }


    private List<Umbrella> sort(HashMap<Long,Umbrella> map) {
        return map.entrySet()
                .stream()
                .map(e -> e.getValue())
                .sorted(Collections.reverseOrder())
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


    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
    private class Umbrella extends JComponent implements Comparable<Umbrella>{
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

        public Umbrella(Product product) {
            this.product = product;
            inisialization(product);
            function.put(Condition.SHOW,this::paint);
            function.put(Condition.INSERT,this::insert);
            function.put(Condition.REMOVE,this::remove);
            function.put(Condition.UPDATE,this::update);
            function.put(Condition.DEAD,this::dead);
        }
        private void inisialization(Product product){
            this.productX =check(product.getCoordinates().getX(), MIN_SIZE_X, MAX_X, OFFSET_X);
            this.y= check(Math.round(product.getCoordinates().getY()), MIN_SIZE_Y, MAX_Y);
            this.widthOrHeight = check(Math.toIntExact(product.getPrice()), MIN_SIZE_PRICE, MAX_UMBRELLA);
            System.out.println(product);
            this.color = createColor(product.getOwnerID().hashCode());
        }

        public void paint(Graphics2D graphics) {
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
            graphics.fillArc(moveX, y, widthOrHeight, widthOrHeight, START_UMBRELLA_ROUND, FINISH_UMBRELLA_ROUND);
            graphics.drawLine(startUmbrellaHandleX, startUmbrellaHandleY, finishUmbrellaHandleX, finishUmbrellaHandleY);
            graphics.drawArc(startUmbrellaHandleRoundX, startUmbrellaHandleRoundY, widthOrHeightUmbrellaHandle, widthOrHeightUmbrellaHandle, START_UMBRELLA_ROUND, finishUmbrellaHandleRound);

            graphics.setColor(Color.WHITE);
            graphics.setStroke(new BasicStroke(thicknessForUmbrellaRound));

            graphics.drawArc(moveX, y, widthOrHeight, widthOrHeight, START_UMBRELLA_ROUND, FINISH_UMBRELLA_ROUND);
            graphics.drawLine(lineRoundUmbrellaHandleX, startUmbrellaHandleY, lineRoundUmbrellaHandleX, finishUmbrellaHandleY);

        }
        public void draw(Graphics2D graphics){
           function.get(condition).drawing(graphics);
        }
        private void update(Graphics2D graphics){
            if(trembling!=0) {
                changeColor();
                moveX += (int) Math.pow(-1, trembling--);
                y += (int) Math.pow(-1, trembling);
                widthOrHeight += (int) Math.pow(-1, trembling);
                paint(graphics);
            } else {
                condition = Condition.SHOW;
                trembling =100;
                inisialization(product);
            }


        }
        public void insert(Graphics2D graphics){
            if(productX > moveX){
                moveX++;
                paint(graphics);
                color = createColor(moveX.hashCode());
            } else {
                condition = Condition.SHOW;
                color= createColor(product.getOwnerID().hashCode());
            }
        }
        public void remove(Graphics2D graphics2D){
            if(!changeColor()){
                widthOrHeight += color.getGreen()%2;
                paint(graphics2D);

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
                    ", product=" + product +
                    '}';
        }


        public int compareTo(Umbrella o) {
            return Comparator.comparing(Umbrella::getWidthOrHeight).compare(this, o);
        }
    }
    private interface IFunction{
        public void drawing(Graphics2D graphics2D);
    }
}
