package test.laba.client.frontEnd.frames.changeProductFrames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
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


public abstract class ChangeProductFrame extends FrameProduct implements ActionListener {
    private static final int START_LOCATION = 30;
    private static final int HOW_SMALLER_SIZE_HEIGHT = 4;
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

    public ChangeProductFrame(ResourceBundle resourceBundle, HomeFrame homeFrame) {
        super(new JDialog((Frame) null, "", true/*false*/), resourceBundle);
        this.homeFrame = homeFrame;
    }

    public void revalidate() {
        //setDialog(new JDialog(homeFrame.getFrame(), "", false));
        ok = new JButton(localisation(getResourceBundle(), Constants.OK));
        mainPlusPanel = new JPanel();
        setResourceBundle(homeFrame.getResourceBundle());
        setDialog(new JDialog((Frame) null, "", true));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(getDialog());
        System.out.println(getResourceBundle());
        revalidate();
        //getDialog().setModalityType(Dialog.ModalityType.MODELESS);
        getDialog().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getDialog().setSize(screenSize.width / 2, screenSize.height / HOW_SMALLER_SIZE_HEIGHT);
        getDialog().setLocation(START_LOCATION, START_LOCATION);
        getDialog().setMinimumSize(new Dimension(screenSize.width / 2, screenSize.height / 2));
        getDialog().setLayout(new BorderLayout());

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
        addOwner.setPreferredSize(STANDARD_DIMENSION);
        needOwner = new NeedOwner();
        addOwner.addActionListener(needOwner);

        mainPlusPanel.add(addOwner);
        addActionButtons();
        mainPlusPanel.setLayout(new BoxLayout(mainPlusPanel, BoxLayout.Y_AXIS));
        getDialog().add(mainPlusPanel, BorderLayout.CENTER);
        getDialog().repaint();
        getDialog().setVisible(true);
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

    @Override
    public JMenu createUMMenu(String name) {
        JMenu menu = new JMenu(name);
        menu.setPreferredSize(new Dimension(WIDTH_UM, HEIGHT_UM));
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
        label.setPreferredSize(STANDARD_DIMENSION);


        JMenu menu = createUMMenu(name);
        JMenuBar menuBar = unitOfMeasureButton(menu);

        mainPlusPanel.add(label);
        mainPlusPanel.add(menuBar);
        mainPlusPanel.add(Box.createRigidArea(new Dimension(0, HEIGHT_BOX_AREA)));
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

    protected Product addProduct() throws VariableException {
        Product product = new Product();
        product.setId(VariableParsing.toLongNumber(local(Constants.ID), getID(), getResourceBundle()));
        product.setName(VariableParsing.toRightName(local(Constants.PRODUCT_NAME), textProductName.getText(), getResourceBundle()));
        Coordinates coordinates = new Coordinates();
        coordinates.setX(VariableParsing.toRightX(local(Constants.COORDINATE_X), textCoordinateX.getText(), getResourceBundle()));
        coordinates.setY(VariableParsing.toRightY(local(Constants.COORDINATE_Y), textCoordinateY.getText(), getResourceBundle()));
        product.setCoordinates(coordinates);
        product.setPrice(VariableParsing.toRightPrice(local(Constants.PRICE), textPrice.getText(), getResourceBundle()));
        product.setManufactureCost(VariableParsing.toRightNumberInt(local(Constants.MANUFACTURE_COST), textManufactureCost.getText(), getResourceBundle()));
        product.setUnitOfMeasure(VariableParsing.toRightUnitOfMeasure(local(Constants.UNIT_OF_MEASURE), textUM.getName(), getResourceBundle()));
        if (addOwner.isSelected()) {
            product.setOwner(needOwner.parsingDate());
        }
        return product;
    }
    public HomeFrame getHomeFrame() {
        return homeFrame;
    }

    protected abstract class OkListener implements ActionListener {
        protected abstract void createResponse(Product product, Long key);

        protected abstract void sentProduct(Long key, Product product);

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Long key = VariableParsing.toRightNumberLong(local(Constants.KEY), textKey.getText(), getResourceBundle());
                Product product = addProduct();

                homeFrame.getLock().lock();
                createResponse(product, key);
                sentProduct(key, product);
                homeFrame.getLock().unlock();
            } catch (VariableException ex) {
                homeFrame.exception(ex.getMessage());
            }

        }
    }

    private class NeedOwner implements ActionListener {
        private static final double HOW_SMALLER_THIS_FRAME_SIZE = 0.8;
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
                textPersonName = createButtonGroup(local(Constants.PERSON_NAME), local(Constants.CAN_NOT_BE_NULL), true);
                textPersonBirthday = createButtonGroup(local(Constants.BIRTHDAY), local(Constants.CAN_NOT_BE_NULL) + local(Constants.FORMAT), true);
                textPersonHeight = createButtonGroup(local(Constants.HEIGHT), local(Constants.MUST_BE_BIGGER) + "0" + local(Constants.CAN_NOT_BE_NULL), true);
                textLocationX = createButtonGroup(local(Constants.LOCATION_X), "", true);
                textLocationY = createButtonGroup(local(Constants.LOCATION_Y), "", true);
                textLocationName = createButtonGroup(local(Constants.LOCATION_NAME), local(Constants.CAN_NOT_BE_NULL), true);
                addActionButtons();
                mainPlusPanel.validate();
                getDialog().validate();
            } else {
                ownersLabels.forEach(i -> mainPlusPanel.remove(i));
                ownersLabels.clear();
                getDialog().repaint();
            }

        }


        public Person parsingDate() throws VariableException {
            Person person = new Person();
            person.setName(VariableParsing.toRightName(local(Constants.PERSON_NAME), textPersonName.getText(), getResourceBundle()));
            person.setBirthday(VariableParsing.toRightBirthday(local(Constants.BIRTHDAY), textPersonBirthday.getText(), getResourceBundle()));
            person.setHeight(VariableParsing.toRightNumberInt(local(Constants.HEIGHT), textPersonHeight.getText(), getResourceBundle()));
            Location location = new Location();
            location.setX(VariableParsing.toRightNumberLong(local(Constants.LOCATION_X), textLocationX.getText(), getResourceBundle()));
            location.setY(VariableParsing.toRightNumberInt(local(Constants.LOCATION_Y), textLocationY.getText(), getResourceBundle()));
            location.setName(VariableParsing.toRightName(local(Constants.LOCATION_NAME), textLocationName.getText(), getResourceBundle()));
            person.setLocation(location);
            return person;
        }
    }


}
