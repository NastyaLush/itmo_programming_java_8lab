package test.laba.client.frontEnd.Frames;

import test.laba.client.util.Constants;
import test.laba.client.util.VariableParsing;
import test.laba.common.dataClasses.Coordinates;
import test.laba.common.dataClasses.Location;
import test.laba.common.dataClasses.Person;
import test.laba.common.dataClasses.Product;
import test.laba.common.exception.VariableException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;


public abstract class ChangeProductFrame extends FrameProduct implements ActionListener {
    protected JPanel mainPlusPanel;
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    protected JButton ok;
    private JTextField textKey;
    private JTextField textProductName;
    private JTextField textCoordinateX;
    private JTextField textCoordinateY;
    private JTextField textPrice;
    private JTextField textManufactoreCost;
    private JCheckBox addOwner;
    private JMenu textUM;
    protected final Font labelFont = new Font("Safari", Font.CENTER_BASELINE, 13);
    protected final Set<Component> ownersLabels = new HashSet<>();
    private NeedOwner needOwner;

    public ChangeProductFrame(ResourceBundle resourceBundle) {
        super(new JFrame(), resourceBundle);
    }

    public void revalidate() {
        jFrame = new JFrame();
        ok = new JButton(localisation(resourceBundle, Constants.OK));
        mainPlusPanel = new JPanel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        revalidate();
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.setSize(screenSize.width / 2, screenSize.height / 4);
        jFrame.setLocation(30, 30);
        jFrame.setMinimumSize(new Dimension(screenSize.width / 2, screenSize.height / 10 * 5));
        jFrame.setLayout(new BorderLayout());

        ok.setFont(labelFont);
        ok.setBackground(Color.gray);
        addOkListener();
        System.out.println("hh");

        addKey();
        textProductName = createButtonGroupe(localisation(resourceBundle, Constants.PRODUCT_NAME), localisation(resourceBundle, Constants.CAN_NOT_BE_NULL), false);
        textCoordinateX = createButtonGroupe(localisation(resourceBundle, Constants.COORDINATE_X), localisation(resourceBundle, Constants.MUST_BE_BIGGER) + "-233" + localisation(resourceBundle, Constants.CAN_NOT_BE_NULL), false);
        textCoordinateY = createButtonGroupe(localisation(resourceBundle, Constants.COORDINATE_Y), localisation(resourceBundle, Constants.CAN_NOT_BE_NULL), false);
        textPrice = createButtonGroupe(localisation(resourceBundle, Constants.PRICE), localisation(resourceBundle, Constants.MUST_BE_BIGGER) + "0" + localisation(resourceBundle, Constants.CAN_NOT_BE_NULL), false);
        textManufactoreCost = createButtonGroupe(localisation(resourceBundle, Constants.MANUFACTURE_COST), localisation(resourceBundle, Constants.CAN_NOT_BE_NULL), false);
        textUM = unitOfMeas(localisation(resourceBundle, Constants.UNIT_OF_MEASURE), localisation(resourceBundle, Constants.CAN_NOT_BE_NULL));

        addOwner = new JCheckBox(localisation(resourceBundle, Constants.ADD_OWNER));
        addOwner.setFont(labelFont);
        addOwner.setPreferredSize(new Dimension(23, 24));
        needOwner = new NeedOwner();
        addOwner.addActionListener(needOwner);

        mainPlusPanel.add(addOwner);
        addActionButtons();
        mainPlusPanel.setLayout(new BoxLayout(mainPlusPanel, BoxLayout.Y_AXIS));
        jFrame.add(mainPlusPanel, BorderLayout.CENTER);
        jFrame.setVisible(true);
    }
    protected void addActionButtons(){
        mainPlusPanel.add(ok);
    }
    protected void removeActionButtons(){
        mainPlusPanel.remove(ok);
    }

    protected abstract void addOkListener();

    protected String getID(){
        return "0";
    }
    protected abstract class OkListener implements ActionListener {
        protected abstract void createResponse(Product product, Long key);

        protected Product addProduct() throws VariableException {
            Product product = new Product();
            product.setId(VariableParsing.toLongNumber(local(Constants.ID), getID()));
            product.setName(VariableParsing.toRightName(local(Constants.PRODUCT_NAME), textProductName.getText()));
            Coordinates coordinates = new Coordinates();
            coordinates.setX(VariableParsing.toRightX(local(Constants.COORDINATE_X), textCoordinateX.getText()));
            coordinates.setY(VariableParsing.toRightY(local(Constants.COORDINATE_Y), textCoordinateY.getText()));
            product.setCoordinates(coordinates);
            product.setPrice(VariableParsing.toRightPrice(local(Constants.PRICE), textPrice.getText()));
            product.setManufactureCost(VariableParsing.toRightNumberInt(local(Constants.MANUFACTURE_COST), textManufactoreCost.getText()));
            product.setUnitOfMeasure(VariableParsing.toRightUnitOfMeasure(local(Constants.UNIT_OF_MEASURE), textUM.getName()));
            if (addOwner.isSelected()) {
                product.setOwner(needOwner.parsingDate());
            }
            return product;
        }

        protected abstract void sentProduct(Long key, Product product);

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Long key = VariableParsing.toRightNumberLong(local(Constants.KEY), textKey.getText());
                Product product = addProduct();

                createResponse(product, key);

                sentProduct(key, product);
            } catch (VariableException ex) {
                exception(ex.getMessage());
            }

        }
    }

    protected JTextField createButtonGroupe(String name, String description, boolean saveToDelete) {
        JLabel label = new JLabel(name + "(" + description + ")");
        label.setForeground(Color.gray);
        label.setFont(labelFont);
        label.setPreferredSize(new Dimension(23, 24));
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(15, 24));
        Component enter = Box.createRigidArea(new Dimension(0, 13));


        if (saveToDelete) {
            ownersLabels.add(label);
            ownersLabels.add(textField);
            ownersLabels.add(enter);
        }

        mainPlusPanel.add(label);
        mainPlusPanel.add(textField);
        mainPlusPanel.add(enter);
        return textField;
    }

    protected JMenu createUMMenu(String name) {
        JMenu menu = new JMenu(name);
        menu.setPreferredSize(new Dimension(400, 50));
        menu.setFont(new Font("Safari", Font.CENTER_BASELINE, 13));
        JMenuItem pcs = new JMenuItem(localisation(resourceBundle, Constants.PCS));
        pcs.setName(Constants.PCS.getString());
        changeMenuName(pcs, menu);
        JMenuItem millilitres = new JMenuItem(localisation(resourceBundle, Constants.MILLILITERS));
        millilitres.setName(Constants.MILLILITERS.getString());
        changeMenuName(millilitres, menu);
        JMenuItem grams = new JMenuItem(localisation(resourceBundle, Constants.GRAMS));
        grams.setName(Constants.GRAMS.getString());
        changeMenuName(grams, menu);

        menu.add(pcs);
        menu.add(millilitres);
        menu.add(grams);
        return menu;

    }

    protected JMenuBar unitOfMeasureButton(JMenu menu) {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menu);
        return menuBar;
    }

    protected JMenu unitOfMeas(String name, String description) {
        JLabel label = new JLabel(name + "(" + description + ")");
        label.setForeground(Color.gray);
        label.setFont(labelFont);
        label.setPreferredSize(new Dimension(23, 24));


        JMenu menu = createUMMenu(name);
        JMenuBar menuBar = unitOfMeasureButton(menu);

        mainPlusPanel.add(label);
        mainPlusPanel.add(menuBar);
        mainPlusPanel.add(Box.createRigidArea(new Dimension(0, 13)));
        return menu;
    }

    protected void addKey() {
        textKey = createButtonGroupe(localisation(resourceBundle, Constants.KEY), localisation(resourceBundle, Constants.CAN_NOT_BE_NULL), false);
    }

    private class NeedOwner implements ActionListener {
        private JTextField textPersonName;
        private JTextField textPersonBirthday;
        private JTextField textPersonHeight;
        private JTextField textLocationX;
        private JTextField textLocationY;
        private JTextField textLocationName;

        @Override
        public void actionPerformed(ActionEvent e) {
            jFrame.setMinimumSize(new Dimension(screenSize.width / 2, screenSize.height / 10 * 8));
            if (addOwner.isSelected()) {
                removeActionButtons();
                textPersonName = createButtonGroupe(local(Constants.PERSON_NAME), local(Constants.CAN_NOT_BE_NULL), true);
                textPersonBirthday = createButtonGroupe(local(Constants.BIRTHDAY), local(Constants.CAN_NOT_BE_NULL) + local(Constants.FORMAT), true);
                textPersonHeight = createButtonGroupe(local(Constants.HEIGHT), local(Constants.MUST_BE_BIGGER) + "0" + local(Constants.CAN_NOT_BE_NULL), true);
                textLocationX = createButtonGroupe(local(Constants.LOCATION_X), "", true);
                textLocationY = createButtonGroupe(local(Constants.LOCATION_Y), "", true);
                textLocationName = createButtonGroupe(local(Constants.LOCATION_NAME), local(Constants.CAN_NOT_BE_NULL), true);
                addActionButtons();
                mainPlusPanel.validate();
                jFrame.validate();
            } else {
                ownersLabels.stream().forEach(i -> mainPlusPanel.remove(i));
                ownersLabels.clear();
                jFrame.repaint();
            }

        }


        public Person parsingDate() throws VariableException {
            Person person = new Person();
            person.setName(VariableParsing.toRightName(local(Constants.PERSON_NAME), textPersonName.getText()));
            person.setBirthday(VariableParsing.toRightBirthday(local(Constants.BIRTHDAY), textPersonBirthday.getText()));
            person.setHeight(VariableParsing.toRightNumberInt(local(Constants.HEIGHT), textPersonHeight.getText()));
            Location location = new Location();
            location.setX(VariableParsing.toRightNumberLong(local(Constants.LOCATION_X), textLocationX.getText()));
            location.setY(VariableParsing.toRightNumberInt(local(Constants.LOCATION_Y), textLocationY.getText()));
            location.setName(VariableParsing.toRightName(local(Constants.LOCATION_NAME), textLocationName.getText()));
            person.setLocation(location);
            return person;
        }
    }

}
