package test.laba.client.frontEnd.frames.changeProductFrames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import test.laba.client.frontEnd.frames.HomeFrame;
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
import test.laba.common.responses.Response;


public abstract class ChangeProductDialog extends ProductDialog implements ActionListener {
    private static final int START_LOCATION = 30;
    private static final int HOW_SMALLER_SIZE_HEIGHT = 2;
    private static final double HOW_SMALLER_SIZE_HEIGHT_MAIN = 0.56;
    private static final Dimension STANDARD_DIMENSION = new Dimension(23, 24);
    private static final int HEIGHT_BOX_AREA = 13;
    private static final int WIDTH_UM = 400;
    private static final int HEIGHT_UM = 50;
    private static final int WIDTH_BUTTON = 15;
    private static final int HEIGHT_BUTTON = 24;
    private static final int HEIGHT_STANDARD_BUTTON = 13;
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
    private final Font labelFont = new Font("Safari", Font.BOLD, HEIGHT_STANDARD_BUTTON);
    private final HomeFrame homeFrame;
    private NeedOwner needOwner;

    public ChangeProductDialog(ResourceBundle resourceBundle, HomeFrame homeFrame) {
        super(new JDialog((Frame) null, "", true), resourceBundle);
        this.homeFrame = homeFrame;
    }

    public void revalidate() {
        ok = new JButton(localisation(Constants.OK));
        mainPlusPanel = new JPanel();
        setResourceBundle(homeFrame.getResourceBundle());
        setDialog(new JDialog((Frame) null, "", true));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        revalidate();
        getDialog().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getDialog().setSize(screenSize.width / 2, screenSize.height / HOW_SMALLER_SIZE_HEIGHT);
        getDialog().setLocation(START_LOCATION, START_LOCATION);
        getDialog().setMinimumSize(new Dimension(screenSize.width / 2, (int) (screenSize.height * HOW_SMALLER_SIZE_HEIGHT_MAIN)));
        getDialog().setLayout(new BorderLayout());

        ok.setFont(labelFont);
        ok.setBackground(Color.gray);
        addOkListener();

        addKey();
        textProductName = createButtonGroup(localisation(Constants.PRODUCT_NAME), localisation(Constants.CAN_NOT_BE_NULL), false);
        textCoordinateX = createButtonGroup(localisation(Constants.COORDINATE_X), localisation(Constants.MUST_BE_BIGGER) + " -233 " + localisation(Constants.CAN_NOT_BE_NULL), false);
        textCoordinateY = createButtonGroup(localisation(Constants.COORDINATE_Y), localisation(Constants.CAN_NOT_BE_NULL), false);
        textPrice = createButtonGroup(localisation(Constants.PRICE), localisation(Constants.MUST_BE_BIGGER) + " 0 " + localisation(Constants.CAN_NOT_BE_NULL), false);
        textManufactureCost = createButtonGroup(localisation(Constants.MANUFACTURE_COST), localisation(Constants.CAN_NOT_BE_NULL), false);
        textUM = unitOfMeas(localisation(Constants.UNIT_OF_MEASURE), localisation(Constants.CAN_NOT_BE_NULL));

        addOwner = new JCheckBox(localisation(Constants.ADD_OWNER));
        addOwner.setFont(labelFont);
        addOwner.setPreferredSize(STANDARD_DIMENSION);
        needOwner = new NeedOwner();
        addOwner.addActionListener(needOwner);

        mainPlusPanel.add(addOwner);
        addActionButtons();
        mainPlusPanel.setLayout(new BoxLayout(mainPlusPanel, BoxLayout.Y_AXIS));
        JPanel helpMainPanel = new JPanel();
        helpMainPanel.add(mainPlusPanel);
        getDialog().add(helpMainPanel, BorderLayout.WEST);
        getDialog().repaint();
        getDialog().setVisible(true);
        getDialog().pack();
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
        label.setPreferredSize(STANDARD_DIMENSION);
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(WIDTH_BUTTON, HEIGHT_BUTTON));
        Component enter = Box.createRigidArea(new Dimension(0, HEIGHT_BOX_AREA));


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
    protected JTextField createBirthady(String name, String description, boolean saveToDelete) {
      return  createButtonGroup(name, description, saveToDelete);
    }
    @Override
    public JMenu createUMMenu(String name) {
        JMenu menu = new JMenu(name);
        menu.setPreferredSize(new Dimension(WIDTH_UM, HEIGHT_UM));
        menu.setFont(getLabelFont());
        JMenuItem pcs = new JMenuItem(localisation(Constants.PCS));
        pcs.setName(Constants.PCS.getString());
        changeMenuName(pcs, menu);
        JMenuItem millilitres = new JMenuItem(localisation(Constants.MILLILITERS));
        millilitres.setName(Constants.MILLILITERS.getString());
        changeMenuName(millilitres, menu);
        JMenuItem grams = new JMenuItem(localisation(Constants.GRAMS));
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
        label.setPreferredSize(STANDARD_DIMENSION);


        JMenu menu = createUMMenu(name);
        JMenuBar menuBar = unitOfMeasureButton(menu);

        mainPlusPanel.add(label);
        mainPlusPanel.add(menuBar);
        mainPlusPanel.add(Box.createRigidArea(new Dimension(0, HEIGHT_BOX_AREA)));
        return menu;
    }

    protected void addKey() {
        textKey = createButtonGroup(localisation(Constants.KEY), localisation(Constants.CAN_NOT_BE_NULL), false);
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

    protected Product addProduct() throws VariableException {
        Product product = new Product();
        product.setId(VariableParsing.toLongNumber(localisation(Constants.ID), getID(), getResourceBundle()));
        product.setName(VariableParsing.toRightName(localisation(Constants.PRODUCT_NAME), textProductName.getText(), getResourceBundle()));
        Coordinates coordinates = new Coordinates();
        coordinates.setX(VariableParsing.toRightX(localisation(Constants.COORDINATE_X), textCoordinateX.getText(), getResourceBundle()));
        coordinates.setY(VariableParsing.toRightY(localisation(Constants.COORDINATE_Y), textCoordinateY.getText(), getResourceBundle()));
        product.setCoordinates(coordinates);
        product.setPrice(VariableParsing.toRightPrice(localisation(Constants.PRICE), textPrice.getText(), getResourceBundle()));
        product.setManufactureCost(VariableParsing.toRightNumberInt(localisation(Constants.MANUFACTURE_COST), textManufactureCost.getText(), getResourceBundle()));
        product.setUnitOfMeasure(VariableParsing.toRightUnitOfMeasure(localisation(Constants.UNIT_OF_MEASURE), textUM.getName(), getResourceBundle()));
        if (addOwner.isSelected()) {
            product.setOwner(needOwner.parsingDate());
        }
        return product;
    }
    public HomeFrame getHomeFrame() {
        return homeFrame;
    }
    protected void close() {
        getDialog().setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        getDialog().dispatchEvent(new WindowEvent(getDialog(), WindowEvent.WINDOW_CLOSING));

    }

    protected abstract class OkListener implements ActionListener {
        protected abstract Response createResponse(Product product, Long key);

        protected abstract void sentProduct(Long key, Product product);

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Long key = VariableParsing.toRightNumberLong(localisation(Constants.KEY), textKey.getText(), getResourceBundle());
                Product product = addProduct();
                createResponse(product, key);
                sentProduct(key, product);
                close();
            } catch (VariableException ex) {
                homeFrame.exception(ex.getMessage());
            }

        }
    }

    private class NeedOwner implements ActionListener {
        private static final double HOW_SMALLER_THIS_FRAME_SIZE = 0.65;
        private JTextField textPersonName;
        private JTextField textPersonBirthday;
        private JTextField textPersonHeight;
        private JTextField textLocationX;
        private JTextField textLocationY;
        private JTextField textLocationName;


        @Override
        public void actionPerformed(ActionEvent e) {
            getDialog().setMinimumSize(new Dimension(screenSize.width / 2, (int) (screenSize.height / HOW_SMALLER_THIS_FRAME_SIZE)));
            if (addOwner.isSelected()) {
                removeActionButtons();
                textPersonName = createButtonGroup(localisation(Constants.PERSON_NAME), localisation(Constants.CAN_NOT_BE_NULL), true);
                textPersonBirthday = createBirthady(localisation(Constants.BIRTHDAY), localisation(Constants.CAN_NOT_BE_NULL) + localisation(Constants.FORMAT), true);
                textPersonHeight = createButtonGroup(localisation(Constants.HEIGHT), localisation(Constants.MUST_BE_BIGGER) + "0" + localisation(Constants.CAN_NOT_BE_NULL), true);
                textLocationX = createButtonGroup(localisation(Constants.LOCATION_X), "", true);
                textLocationY = createButtonGroup(localisation(Constants.LOCATION_Y), "", true);
                textLocationName = createButtonGroup(localisation(Constants.LOCATION_NAME), localisation(Constants.CAN_NOT_BE_NULL), true);
                addActionButtons();
                mainPlusPanel.validate();
                getDialog().setMinimumSize(new Dimension(screenSize.width / 2, (int) (screenSize.height / HOW_SMALLER_THIS_FRAME_SIZE)));
                getDialog().validate();
            } else {
                ownersLabels.forEach(i -> mainPlusPanel.remove(i));
                ownersLabels.clear();
                getDialog().setMinimumSize(new Dimension(screenSize.width / 2, screenSize.height / HOW_SMALLER_SIZE_HEIGHT));
                getDialog().repaint();
                getDialog().pack();
            }

        }


        public Person parsingDate() throws VariableException {
            Person person = new Person();
            person.setName(VariableParsing.toRightName(localisation(Constants.PERSON_NAME), textPersonName.getText(), getResourceBundle()));
            person.setBirthday(VariableParsing.toRightBirthday(localisation(Constants.BIRTHDAY), textPersonBirthday.getText(), getResourceBundle()));
            person.setHeight(VariableParsing.toRightNumberInt(localisation(Constants.HEIGHT), textPersonHeight.getText(), getResourceBundle()));
            Location location = new Location();
            location.setX(VariableParsing.toRightNumberLong(localisation(Constants.LOCATION_X), textLocationX.getText(), getResourceBundle()));
            location.setY(VariableParsing.toRightNumberInt(localisation(Constants.LOCATION_Y), textLocationY.getText(), getResourceBundle()));
            location.setName(VariableParsing.toRightName(localisation(Constants.LOCATION_NAME), textLocationName.getText(), getResourceBundle()));
            person.setLocation(location);
            return person;
        }
    }


}
