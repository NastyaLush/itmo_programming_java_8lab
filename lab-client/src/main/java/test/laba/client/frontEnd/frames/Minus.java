package test.laba.client.frontEnd.frames;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import test.laba.client.frontEnd.frames.changeProductFrames.ChangeProductDialog;
import test.laba.client.util.Command;
import test.laba.client.util.Constants;
import test.laba.client.util.VariableParsing;
import test.laba.common.dataClasses.Product;
import test.laba.common.dataClasses.UnitOfMeasure;
import test.laba.common.exception.VariableException;
import test.laba.common.responses.Response;

public class Minus implements ActionListener {
    private static final Font FONT = new Font("Safari", Font.BOLD, 17);
    private final JDialog delete;
    private final JPanel leftMinusPanel = new JPanel();
    private final HomeFrame homeFrame;
    private final JComponent minus;

    public Minus(HomeFrame homeFrame, JComponent minus) {
        this.homeFrame = homeFrame;
        this.minus = minus;
        delete = new JDialog(homeFrame.getFrame(), "", true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        delete.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        delete.setLocationRelativeTo(homeFrame.getMainPanel());
        delete.setLayout(new BorderLayout());
        homeFrame.revalidate(leftMinusPanel);
        leftMinusPanel.setLayout(new BoxLayout(leftMinusPanel, BoxLayout.Y_AXIS));
        JRadioButton removeKey = createRadioButton(homeFrame.localisation(Constants.REMOVE_KEY), new RemoveKeyOrRemoveLowerKey(Command.REMOVE_KEY.getString()));
        JRadioButton removeLowerKey = createRadioButton(homeFrame.localisation(Constants.REMOVE_LOWER_KEY), new RemoveKeyOrRemoveLowerKey(Command.REMOVE_LOWER_KEY.getString()));
        JRadioButton removeLower = createRadioButton(homeFrame.localisation(Constants.REMOVE_LOWER), new RemoveLower(homeFrame.getResourceBundle()));
        JRadioButton removeAnyByUnitOfMeasure = new JRadioButton(homeFrame.localisation(Constants.REMOVE_ANY_BY_UNIT_OF_MEASURE));
        removeAnyByUnitOfMeasure.setFont(FONT);
        removeAnyByUnitOfMeasure.addActionListener(removeUMDialog());
        createButtonGroup(removeKey, removeLower, removeLowerKey, removeAnyByUnitOfMeasure);
        delete.add(leftMinusPanel, BorderLayout.WEST);
        leftMinusPanel.add(removeKey);
        leftMinusPanel.add(removeLowerKey);
        leftMinusPanel.add(removeLower);
        leftMinusPanel.add(removeAnyByUnitOfMeasure);
        delete.pack();
        delete.setVisible(true);
    }
    private ActionListener removeUMDialog() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result = (String) JOptionPane.showInputDialog(
                        minus,
                        homeFrame.localisation(Constants.UNIT_OF_MEASURE),
                        "",
                        JOptionPane.QUESTION_MESSAGE,
                        null, homeFrame.getTableModule().unitOfMeasureContains(),
                        homeFrame.localisation(Constants.UNIT_OF_MEASURE));
                if (result != null) {
                    try {
                        homeFrame.treatmentResponseWithoutFrame(homeFrame::show, createResponse(Command.REMOVE_ANY_BY_UNIT_OF_MEASURE.getString(), result));
                    } catch (VariableException ex) {
                        homeFrame.exception(ex.getMessage());
                    }
                }
            }

            public Response createResponse(String command, String answer) throws VariableException {
                Response createdResponse = new Response(command);
                UnitOfMeasure unitOfMeasure1 = VariableParsing.toRightUnitOfMeasure(homeFrame.localisation(Constants.UNIT_OF_MEASURE), homeFrame.getTableModule().delocalizationUnitOfMeasure(answer), homeFrame.getResourceBundle());
                createdResponse.setUnitOfMeasure(unitOfMeasure1);
                return createdResponse;
            }
        };
    }

    private void createButtonGroup(JRadioButton removeKey, JRadioButton removeLower, JRadioButton removeLowerKey, JRadioButton removeAnyByUnitOfMeasure) {
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(removeKey);
        buttonGroup.add(removeLower);
        buttonGroup.add(removeLowerKey);
        buttonGroup.add(removeAnyByUnitOfMeasure);
    }

    private JRadioButton createRadioButton(String text, ActionListener actionListener) {
        JRadioButton removeKey = new JRadioButton(text);
        removeKey.setFont(FONT);
        removeKey.addActionListener(actionListener);
        return removeKey;
    }

    private class RemoveKeyOrRemoveLowerKey implements ActionListener {
        private final String name;

        RemoveKeyOrRemoveLowerKey(String name) {
            this.name = name;
        }


        public Response createResponse(String command, String answer) throws VariableException {
            Response createdResponse = new Response(command);
            Long key = VariableParsing.toLongNumber(homeFrame.localisation(Constants.KEY), answer, homeFrame.getResourceBundle());
            createdResponse.setKeyOrID(key);
            return createdResponse;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String answer = JOptionPane.showInputDialog(null,
                    homeFrame.localisation(Constants.KEY),
                    "",
                    JOptionPane.QUESTION_MESSAGE);
            if (answer != null) {
                try {
                    homeFrame.treatmentResponseWithoutFrame(homeFrame::show, createResponse(name, answer));
                } catch (VariableException ex) {
                    homeFrame.exception(ex.getMessage());
                }
            }
        }
    }

    private final class RemoveLower extends ChangeProductDialog {
        private RemoveLower(ResourceBundle resourceBundle) {
            super(resourceBundle, homeFrame);
        }

        @Override
        protected void addKey() {
        }

        @Override
        protected void addOkListener() {
            getOk().addActionListener(new OkListener() {
                @Override
                protected Response createResponse(Product product, Long key) {
                    return null;
                }

                @Override
                protected void sentProduct(Long key, Product product) {
                }

                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        Product product = addProduct();
                        sentProduct(createResponse(product));
                    } catch (VariableException ex) {
                        homeFrame.exception(ex.getMessage());
                    }
                }

                private Response createResponse(Product product) {
                    Response response = new Response(Command.REMOVE_LOWER.getString());
                    response.setProduct(product);
                    return response;
                }

                private void sentProduct(Response response) {
                    delete.dispatchEvent(new WindowEvent(getDialog(), WindowEvent.WINDOW_CLOSING));
                    homeFrame.treatmentResponseWithoutFrame(homeFrame::show, response);
                }
            });
        }

    }

}
