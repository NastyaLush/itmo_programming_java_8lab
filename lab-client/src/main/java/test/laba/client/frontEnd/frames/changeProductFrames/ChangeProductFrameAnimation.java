package test.laba.client.frontEnd.frames.changeProductFrames;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTextField;
import test.laba.client.frontEnd.frames.HomeFrame;
import test.laba.client.frontEnd.frames.IFunctionString;
import test.laba.client.util.Command;
import test.laba.client.util.Constants;
import test.laba.common.dataClasses.Product;
import test.laba.common.dataClasses.UnitOfMeasure;
import test.laba.common.exception.VariableException;
import test.laba.common.responses.Response;


import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.ResourceBundle;

public abstract class ChangeProductFrameAnimation extends ChangeProductFrame {
    private final Product product;
    private final Long key;
    private JButton remove;
    private final HashMap<String, IFunctionString> functions;
    private final Dimension standardDimension = new Dimension(23, 24);
    private final int widthStandardTextField = 15;
    private final int heightStandardArea = 5;
    private final int heightStandardButton = 24;
    private final int heightStandardAreaSmall = 5;

    public ChangeProductFrameAnimation(ResourceBundle resourceBundle, Product product, Long key, HomeFrame homeFrame) {
        super(resourceBundle, homeFrame);
        this.product = product;
        functions = new HashMap<>();
        functions.put(local(Constants.PRODUCT_NAME), product::getName);
        functions.put(local(Constants.COORDINATE_X), product.getCoordinates()::getX);
        functions.put(local(Constants.COORDINATE_Y), product.getCoordinates()::getY);
        functions.put(local(Constants.PRICE), product::getPrice);
        functions.put(local(Constants.MANUFACTURE_COST), product::getManufactureCost);
        functions.put(local(Constants.UNIT_OF_MEASURE), product::getUnitOfMeasure);
        if (product.getOwner() != null) {
            functions.put(local(Constants.PERSON_NAME), product.getOwner()::getName);
            functions.put(local(Constants.BIRTHDAY), product.getOwner()::getBirthday);
            functions.put(local(Constants.HEIGHT), product.getOwner()::getHeight);
            functions.put(local(Constants.LOCATION_NAME), product.getOwner().getLocation()::getName);
            functions.put(local(Constants.LOCATION_X), product.getOwner().getLocation()::getX);
            functions.put(local(Constants.LOCATION_Y), product.getOwner().getLocation()::getY);
        } else {
            functions.put(local(Constants.PERSON_NAME), null);
            functions.put(local(Constants.BIRTHDAY), null);
            functions.put(local(Constants.HEIGHT), null);
            functions.put(local(Constants.LOCATION_NAME), null);
            functions.put(local(Constants.LOCATION_X), null);
            functions.put(local(Constants.LOCATION_Y), null);
        }

        this.key = key;
    }


    @Override
    protected JTextField createButtonGroup(String name, String description, boolean saveToDelete) {
        JLabel label = new JLabel(name + "(" + description + ")");
        label.setForeground(Color.gray);
        label.setFont(getLabelFont());
        label.setPreferredSize(standardDimension);

        JTextField textField = new JTextField(getDescription(name));
        textField.setPreferredSize(new Dimension(widthStandardTextField, heightStandardButton));
        Component enter = Box.createRigidArea(new Dimension(0, heightStandardArea));


        addLabels(saveToDelete, label, textField, enter);
        return textField;
    }

    @Override
    protected void addKey() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
    }

    @Override
    protected void addActionButtons() {
        super.addActionButtons();
        remove = new JButton(localisation(getResourceBundle(), Constants.REMOVE_KEY));
        remove.setBackground(Color.GRAY);
        remove.setFont(getLabelFont());
        remove.addActionListener(e -> {
            try {
                addRemoveListener();
            } catch (VariableException ex) {
                show(ex.getMessage());
            }
        });
        getMainPlusPanel().add(Box.createRigidArea(new Dimension(0, heightStandardAreaSmall)));
        getMainPlusPanel().add(remove);
    }

    protected Response createResponse() {
        Response response = new Response(Command.REMOVE_KEY.getString());
        response.setKeyOrID(key);
        return response;
    }

    protected abstract void addRemoveListener() throws VariableException;

    @Override
    protected void removeActionButtons() {
        super.removeActionButtons();
        getMainPlusPanel().remove(remove);

    }

    protected Response createUpdateResponse() throws VariableException {
        Response response = new Response(Command.UPDATE_ID.getString());
        Product newProduct = addProduct();
        response.setProduct(newProduct);
        response.setFlagUdateID(true);
        response.setKeyOrID(product.getId());
        return response;
    }


    @Override
    protected JMenu unitOfMeas(String name, String description) {
        JLabel label = new JLabel(name + "(" + description + ")");
        label.setForeground(Color.gray);
        label.setFont(getLabelFont());
        label.setPreferredSize(standardDimension);


        JMenu menu = createUMMenu(localisation(getResourceBundle(), localUM(product.getUnitOfMeasure())));
        menu.setName(getDescription(name));
        JMenuBar menuBar = unitOfMeasureButton(menu);

        getMainPlusPanel().add(label);
        getMainPlusPanel().add(menuBar);
        getMainPlusPanel().add(Box.createRigidArea(new Dimension(0, heightStandardArea)));
        return menu;
    }

    protected String getDescription(String name) {
        if (functions.get(name) != null) {
            return String.valueOf(functions.get(name).getText());
        } else {
            return "";
        }
    }

    private Constants localUM(UnitOfMeasure unitOfMeasure) {
        switch (unitOfMeasure) {
            case PCS:
                return Constants.PCS;
            case MILLILITERS:
                return Constants.MILLILITERS;
            case GRAMS:
                return Constants.GRAMS;
            default:
                return null;

        }
    }

}
