package test.laba.client.frontEnd.frames.changeProductFrames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import test.laba.client.util.Constants;
import test.laba.client.util.VariableParsing;
import test.laba.common.dataClasses.Coordinates;
import test.laba.common.dataClasses.Location;
import test.laba.common.dataClasses.Person;
import test.laba.common.dataClasses.Product;
import test.laba.common.exception.VariableException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;


public abstract class ChangeProductFrame extends FrameProduct implements ActionListener {
    private JPanel mainPlusPanel;
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private JButton ok;
    private JTextField textKey;
    private JTextField textProductName;
    private JTextField textCoordinateX;
    private JTextField textCoordinateY;
    private JTextField textPrice;
    private JTextField textManufactureCost;
    private JCheckBox addOwner;
    private JMenu textUM;
    private final Set<Component> ownersLabels = new HashSet<>();
    private final int startLocation = 30;
    private final int howSmallerSizeHeight = 4;
    private final Dimension standardDimension = new Dimension(23, 24);
    private final int heightBoxArea = 13;
    private final int widthUM = 400;
    private final int heightUM = 50;
    private final int widthButton = 15;
    private final int heightButton = 24;
    private final int heightStandardButton = 13;
    private final Font labelFont = new Font("Safari", Font.BOLD, heightStandardButton);
    private NeedOwner needOwner;

    public ChangeProductFrame(ResourceBundle resourceBundle) {
        super(new JFrame(), resourceBundle);
    }

    public void revalidate() {
        setFrame(new JFrame());
        ok = new JButton(localisation(getResourceBundle(), Constants.OK));
        mainPlusPanel = new JPanel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        revalidate();
        getFrame().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getFrame().setSize(screenSize.width / 2, screenSize.height / howSmallerSizeHeight);
        getFrame().setLocation(startLocation, startLocation);
        getFrame().setMinimumSize(new Dimension(screenSize.width / 2, screenSize.height / 2));
        getFrame().setLayout(new BorderLayout());

        ok.setFont(labelFont);
        ok.setBackground(Color.gray);
        addOkListener();

        addKey();
        textProductName = createButtonGroup(localisation(getResourceBundle(), Constants.PRODUCT_NAME), localisation(getResourceBundle(), Constants.CAN_NOT_BE_NULL), false);
        textCoordinateX = createButtonGroup(localisation(getResourceBundle(), Constants.COORDINATE_X), localisation(getResourceBundle(), Constants.MUST_BE_BIGGER) + " -233 " + localisation(getResourceBundle(), Constants.CAN_NOT_BE_NULL), false);
        textCoordinateY = createButtonGroup(localisation(getResourceBundle(), Constants.COORDINATE_Y), localisation(getResourceBundle(), Constants.CAN_NOT_BE_NULL), false);
        textPrice = createButtonGroup(localisation(getResourceBundle(), Constants.PRICE), localisation(getResourceBundle(), Constants.MUST_BE_BIGGER) + " 0 " + localisation(getResourceBundle(), Constants.CAN_NOT_BE_NULL), false);
        textManufactureCost = createButtonGroup(localisation(getResourceBundle(), Constants.MANUFACTURE_COST), localisation(getResourceBundle(), Constants.CAN_NOT_BE_NULL), false);
        textUM = unitOfMeas(localisation(getResourceBundle(), Constants.UNIT_OF_MEASURE), localisation(getResourceBundle(), Constants.CAN_NOT_BE_NULL));

        addOwner = new JCheckBox(localisation(getResourceBundle(), Constants.ADD_OWNER));
        addOwner.setFont(labelFont);
        addOwner.setPreferredSize(standardDimension);
        needOwner = new NeedOwner();
        addOwner.addActionListener(needOwner);

        mainPlusPanel.add(addOwner);
        addActionButtons();
        mainPlusPanel.setLayout(new BoxLayout(mainPlusPanel, BoxLayout.Y_AXIS));
        getFrame().add(mainPlusPanel, BorderLayout.CENTER);
        getFrame().setVisible(true);
    }

    protected void addActionButtons() {
        mainPlusPanel.add(ok);
    }

    protected void removeActionButtons() {
        mainPlusPanel.remove(ok);
    }

    protected abstract void addOkListener();

    protected String getID() {
        return "0";
    }

    protected JTextField createButtonGroup(String name, String description, boolean saveToDelete) {
        JLabel label = new JLabel(name + "(" + description + ")");
        label.setForeground(Color.gray);
        label.setFont(labelFont);
        label.setPreferredSize(standardDimension);
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(widthButton, heightButton));
        Component enter = Box.createRigidArea(new Dimension(0, heightBoxArea));


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

    @Override
    public JMenu createUMMenu(String name) {
        JMenu menu = new JMenu(name);
        menu.setPreferredSize(new Dimension(widthUM, heightUM));
        menu.setFont(getLabelFont());
        JMenuItem pcs = new JMenuItem(localisation(getResourceBundle(), Constants.PCS));
        pcs.setName(Constants.PCS.getString());
        changeMenuName(pcs, menu);
        JMenuItem millilitres = new JMenuItem(localisation(getResourceBundle(), Constants.MILLILITERS));
        millilitres.setName(Constants.MILLILITERS.getString());
        changeMenuName(millilitres, menu);
        JMenuItem grams = new JMenuItem(localisation(getResourceBundle(), Constants.GRAMS));
        grams.setName(Constants.GRAMS.getString());
        changeMenuName(grams, menu);

        menu.add(pcs);
        menu.add(millilitres);
        menu.add(grams);
        return menu;

    }

    @Override
    public JMenuBar unitOfMeasureButton(JMenu menu) {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menu);
        return menuBar;
    }

    protected JMenu unitOfMeas(String name, String description) {
        JLabel label = new JLabel(name + "(" + description + ")");
        label.setForeground(Color.gray);
        label.setFont(labelFont);
        label.setPreferredSize(standardDimension);


        JMenu menu = createUMMenu(name);
        JMenuBar menuBar = unitOfMeasureButton(menu);

        mainPlusPanel.add(label);
        mainPlusPanel.add(menuBar);
        mainPlusPanel.add(Box.createRigidArea(new Dimension(0, heightBoxArea)));
        return menu;
    }

    protected void addKey() {
        textKey = createButtonGroup(localisation(getResourceBundle(), Constants.KEY), localisation(getResourceBundle(), Constants.CAN_NOT_BE_NULL), false);
    }

    protected void addLabels(boolean saveToDelete, JLabel label, JTextField textField, Component enter) {
        if (saveToDelete) {
            getOwnersLabels().add(label);
            getOwnersLabels().add(textField);
            getOwnersLabels().add(enter);
        }

        getMainPlusPanel().add(label);
        getMainPlusPanel().add(textField);
        getMainPlusPanel().add(enter);
    }

    public JPanel getMainPlusPanel() {
        return mainPlusPanel;
    }

    public Font getLabelFont() {
        return labelFont;
    }

    public JButton getOk() {
        return ok;
    }

    public Set<Component> getOwnersLabels() {
        return ownersLabels;
    }

    @Override
    public void repaintForLanguage() {
        setFrame(new JFrame());
        run();
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
            product.setManufactureCost(VariableParsing.toRightNumberInt(local(Constants.MANUFACTURE_COST), textManufactureCost.getText()));
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

    private class NeedOwner implements ActionListener {
        private JTextField textPersonName;
        private JTextField textPersonBirthday;
        private JTextField textPersonHeight;
        private JTextField textLocationX;
        private JTextField textLocationY;
        private JTextField textLocationName;
        private final double howSmallerThisFrameSize = 4 / 5;

        @Override
        public void actionPerformed(ActionEvent e) {
            getFrame().setMinimumSize(new Dimension(screenSize.width / 2, (int) (screenSize.height / howSmallerThisFrameSize)));
            if (addOwner.isSelected()) {
                removeActionButtons();
                textPersonName = createButtonGroup(local(Constants.PERSON_NAME), local(Constants.CAN_NOT_BE_NULL), true);
                textPersonBirthday = createButtonGroup(local(Constants.BIRTHDAY), local(Constants.CAN_NOT_BE_NULL) + local(Constants.FORMAT), true);
                textPersonHeight = createButtonGroup(local(Constants.HEIGHT), local(Constants.MUST_BE_BIGGER) + "0" + local(Constants.CAN_NOT_BE_NULL), true);
                textLocationX = createButtonGroup(local(Constants.LOCATION_X), "", true);
                textLocationY = createButtonGroup(local(Constants.LOCATION_Y), "", true);
                textLocationName = createButtonGroup(local(Constants.LOCATION_NAME), local(Constants.CAN_NOT_BE_NULL), true);
                addActionButtons();
                mainPlusPanel.validate();
                getFrame().validate();
            } else {
                ownersLabels.forEach(i -> mainPlusPanel.remove(i));
                ownersLabels.clear();
                getFrame().repaint();
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
