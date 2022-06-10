package test.laba.client.frontEnd.Frames;

import test.laba.client.frontEnd.Constants;
import test.laba.client.frontEnd.Local;
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

public abstract class ChangeProductFrame extends FrameProduct implements ActionListener{
    protected final JPanel mainPlusPanel = new JPanel();
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    protected final JButton ok;
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
        ok = new JButton(localisation(resourceBundle, Constants.OK));
    }



    @Override
    public void actionPerformed(ActionEvent e) {

        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.setSize(screenSize.width / 2, screenSize.height / 4);
        jFrame.setLocation(30, 30);
        jFrame.setMinimumSize(new Dimension(screenSize.width / 2, screenSize.height / 10 * 5));
        jFrame.setLayout(new BorderLayout());

        ok.setFont(labelFont);
        ok.setBackground(Color.green);
        addOkListener();

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
        mainPlusPanel.add(ok);
        mainPlusPanel.setLayout(new BoxLayout(mainPlusPanel, BoxLayout.Y_AXIS));
        jFrame.add(mainPlusPanel, BorderLayout.CENTER);
        jFrame.setVisible(true);
    }
    protected abstract void addOkListener();
    protected abstract class OkListener implements ActionListener {
        protected abstract void createResponse(Product product, Long key);

        protected Product addProduct() throws VariableException {
            Product product = new Product();
            //product.setId();
            product.setName(VariableParsing.toRightName(textProductName.getText()));
            Coordinates coordinates = new Coordinates();
            coordinates.setX(VariableParsing.toRightX(textCoordinateX.getText()));
            coordinates.setY(VariableParsing.toRightY(textCoordinateY.getText()));
            product.setCoordinates(coordinates);
            product.setPrice(VariableParsing.toRightPrice(textPrice.getText()));
            product.setManufactureCost(VariableParsing.toRightNumberInt(textManufactoreCost.getText()));
            product.setUnitOfMeasure(VariableParsing.toRightUnitOfMeasure(textUM.getText()));
            if (addOwner.isSelected()) {
                product.setOwner(needOwner.parsingDate());
            }
            return product;
        }

        protected abstract void sentProduct(Long key, Product product);
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Long key = VariableParsing.toRightNumberLong(textKey.getText());
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
        JMenuItem rus = new JMenuItem("PCS");
        changeMenuName(rus, menu);
        JMenuItem nor = new JMenuItem("MILLILITERS");
        changeMenuName(nor, menu);
        JMenuItem fr = new JMenuItem("GRAMS");
        changeMenuName(fr, menu);

        menu.add(rus);
        menu.add(nor);
        menu.add(fr);
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
                mainPlusPanel.remove(ok);
                textPersonName = createButtonGroupe(localisation(resourceBundle, Constants.PERSON_NAME), localisation(resourceBundle, Constants.CAN_NOT_BE_NULL), true);
                textPersonBirthday = createButtonGroupe(localisation(resourceBundle, Constants.BIRTHDAY), localisation(resourceBundle, Constants.CAN_NOT_BE_NULL), true);
                textPersonHeight = createButtonGroupe(localisation(resourceBundle, Constants.HEIGHT), localisation(resourceBundle, Constants.MUST_BE_BIGGER) + "0" + localisation(resourceBundle, Constants.CAN_NOT_BE_NULL), true);
                textLocationX = createButtonGroupe(localisation(resourceBundle, Constants.LOCATION_X), "", true);
                textLocationY = createButtonGroupe(localisation(resourceBundle, Constants.LOCATION_Y), "", true);
                textLocationName = createButtonGroupe(localisation(resourceBundle, Constants.LOCATION_NAME), localisation(resourceBundle, Constants.CAN_NOT_BE_NULL), true);
                mainPlusPanel.add(ok);
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
            person.setName(VariableParsing.toRightName(textPersonName.getText()));
            person.setBirthday(VariableParsing.toRightBirthday(textPersonBirthday.getText()));
            person.setHeight(VariableParsing.toRightNumberInt(textPersonHeight.getText()));
            Location location = new Location();
            location.setX(VariableParsing.toRightNumberLong(textLocationX.getText()));
            location.setY(VariableParsing.toRightNumberInt(textLocationY.getText()));
            location.setName(VariableParsing.toRightName(textLocationName.getText()));
            person.setLocation(location);
            return person;
        }
    }
}
