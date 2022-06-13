package test.laba.client.frontEnd.Frames;

import test.laba.client.frontEnd.TableModule;
import test.laba.client.frontEnd.TablePanel;
import test.laba.client.util.Command;
import test.laba.client.util.Constants;
import test.laba.common.dataClasses.Product;
import test.laba.common.responses.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.ResourceBundle;

public abstract class ChangeProductFrameAnimation extends  ChangeProductFrame{
    private final Product product;
    private final Long key;
    private JButton remove;
    private HashMap<String, IFunctionString> functions;
    public ChangeProductFrameAnimation(ResourceBundle resourceBundle, Product product, Long key) {
        super(resourceBundle);
        this.product = product;
        functions = new HashMap<String, IFunctionString>(){
            {
                put(local(Constants.PRODUCT_NAME), product::getName);
                put(local(Constants.COORDINATE_X), product.getCoordinates()::getX);
                put(local(Constants.COORDINATE_Y), product.getCoordinates()::getY);
                put(local(Constants.PRICE), product::getPrice);
                put(local(Constants.MANUFACTURE_COST), product::getManufactureCost);
                put(local(Constants.UNIT_OF_MEASURE), product::getUnitOfMeasure);
                if(product.getOwner()!= null) {
                    put(local(Constants.PERSON_NAME), product.getOwner()::getName);
                    put(local(Constants.BIRTHDAY), product.getOwner()::getBirthday);
                    put(local(Constants.HEIGHT), product.getOwner()::getHeight);
                    put(local(Constants.LOCATION_NAME), product.getOwner().getLocation()::getName);
                    put(local(Constants.LOCATION_X), product.getOwner().getLocation()::getX);
                    put(local(Constants.LOCATION_Y), product.getOwner().getLocation()::getY);
                } else {
                    put(local(Constants.PERSON_NAME), null);
                    put(local(Constants.BIRTHDAY), null);
                    put(local(Constants.HEIGHT), null);
                    put(local(Constants.LOCATION_NAME), null);
                    put(local(Constants.LOCATION_X), null);
                    put(local(Constants.LOCATION_Y), null);
                }

            }
        };
        this.key = key;
    }


    @Override
    protected JTextField createButtonGroupe(String name, String description, boolean saveToDelete) {
        JLabel label = new JLabel(name + "(" + description + ")");
        label.setForeground(Color.gray);
        label.setFont(labelFont);
        label.setPreferredSize(new Dimension(23, 24));

        JTextField textField = new JTextField(getDescription(name));
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
        remove = new JButton(localisation(resourceBundle, Constants.REMOVE_KEY));
        remove.setBackground(Color.GRAY);
        remove.setFont(labelFont);
        remove.addActionListener(e -> addRemoveListener());
        mainPlusPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPlusPanel.add(remove);
    }
    protected Response createResponse() {
        Response response = new Response(Command.REMOVE_KEY.getString());
        response.setKeyOrID(key);
        return response;
    }
    protected abstract void addRemoveListener();

    @Override
    protected void removeActionButtons() {
        super.removeActionButtons();
        mainPlusPanel.remove(remove);

    }
    protected Response createUpdateResponse(){
        Response response = new Response(Command.UPDATE_ID.getString());
        response.setProduct(product);
        response.setFlagUdateID(true);
        response.setKeyOrID(product.getId());
        return response;
    }



    protected JMenu unitOfMeas(String name, String description) {
        JLabel label = new JLabel(name + "(" + description + ")");
        label.setForeground(Color.gray);
        label.setFont(labelFont);
        label.setPreferredSize(new Dimension(23, 24));


        JMenu menu = createUMMenu(getDescription(name));
        JMenuBar menuBar = unitOfMeasureButton(menu);

        mainPlusPanel.add(label);
        mainPlusPanel.add(menuBar);
        mainPlusPanel.add(Box.createRigidArea(new Dimension(0, 13)));
        return menu;
    }

    protected String getDescription(String name) {
        if(functions.get(name) != null) {
            return String.valueOf(functions.get(name).getText());
        } else {
            return "";
        }
    }

}
