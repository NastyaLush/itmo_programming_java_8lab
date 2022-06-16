package test.laba.client.frontEnd.frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import test.laba.client.frontEnd.frames.changeProductFrames.ChangeProductFrame;
import test.laba.client.util.Command;
import test.laba.client.util.Constants;
import test.laba.client.util.VariableParsing;
import test.laba.common.dataClasses.Product;
import test.laba.common.dataClasses.UnitOfMeasure;
import test.laba.common.exception.VariableException;
import test.laba.common.responses.Response;

public class Minus implements ActionListener {
    private static final int FOURTH_SMALLER_PANEL_SIZE = 4;
    private final JFrame delete = new JFrame();
    private final JPanel leftMinusPanel = new JPanel();
    private final HomeFrame homeFrame;

    public Minus(HomeFrame homeFrame) {
        this.homeFrame = homeFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        delete.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        delete.setSize(homeFrame.getScreenSize().width / 2, homeFrame.getScreenSize().height / FOURTH_SMALLER_PANEL_SIZE);
        delete.setLocationRelativeTo(homeFrame.getMainPanel());
        delete.setMinimumSize(new Dimension(homeFrame.screenSize.width / 2, homeFrame.screenSize.height / 2));
        delete.setLayout(new BorderLayout());
        homeFrame.revalidate(leftMinusPanel);
        leftMinusPanel.setLayout(new BoxLayout(leftMinusPanel, BoxLayout.Y_AXIS));
        JRadioButton removeKey = createRadioButton(homeFrame.localisation(homeFrame.getResourceBundle(), Constants.REMOVE_KEY), new RemoveKeyOrRemoveLowerKey(Command.REMOVE_KEY.getString()));
        JRadioButton removeLowerKey = createRadioButton(homeFrame.localisation(homeFrame.getResourceBundle(), Constants.REMOVE_LOWER_KEY), new RemoveKeyOrRemoveLowerKey(Command.REMOVE_LOWER_KEY.getString()));
        JRadioButton removeLower = createRadioButton(homeFrame.localisation(homeFrame.getResourceBundle(), Constants.REMOVE_LOWER), new RemoveLower(homeFrame.getResourceBundle()));
        JRadioButton removeAnyByUnitOfMeasure = new JRadioButton(homeFrame.localisation(homeFrame.getResourceBundle(), Constants.REMOVE_ANY_BY_UNIT_OF_MEASURE));
        removeAnyByUnitOfMeasure.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result = (String) JOptionPane.showInputDialog(
                        null,
                        homeFrame.localisation(homeFrame.getResourceBundle(), Constants.UNIT_OF_MEASURE),
                        "Выбор напитка",
                        JOptionPane.QUESTION_MESSAGE,
                        null, homeFrame.getTableModule().unitOfMeasureContains(homeFrame.getResourceBundle()),
                        homeFrame.localisation(homeFrame.getResourceBundle(), Constants.UNIT_OF_MEASURE));
                try {
                    homeFrame.getLock().lock();
                    homeFrame.setResponse(createResponse(Command.REMOVE_ANY_BY_UNIT_OF_MEASURE.getString(), result));
                    homeFrame.treatmentResponseWithoutFrame(homeFrame::show);
                    homeFrame.getLock().unlock();
                } catch (VariableException ex) {
                    homeFrame.exception(ex.getMessage());
                }
            }

            public Response createResponse(String command, String answer) throws VariableException {
                Response createdResponse = new Response(command);
                UnitOfMeasure unitOfMeasure1 = VariableParsing.toRightUnitOfMeasure(homeFrame.localisation(homeFrame.getResourceBundle(), Constants.UNIT_OF_MEASURE), homeFrame.getTableModule().delocalizationUnitOfMeasure(answer), homeFrame.getResourceBundle());
                createdResponse.setUnitOfMeasure(unitOfMeasure1);
                return createdResponse;
            }
        });
        createButtonGroup(removeKey, removeLower, removeLowerKey, removeAnyByUnitOfMeasure);
        delete.add(leftMinusPanel, BorderLayout.WEST);
        leftMinusPanel.add(removeKey);
        leftMinusPanel.add(removeLowerKey);
        leftMinusPanel.add(removeLower);
        leftMinusPanel.add(removeAnyByUnitOfMeasure);
        delete.setVisible(true);
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
            Long key = VariableParsing.toLongNumber(homeFrame.localisation(homeFrame.getResourceBundle(), Constants.KEY), answer, homeFrame.getResourceBundle());
            createdResponse.setKeyOrID(key);
            return createdResponse;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String answer = JOptionPane.showInputDialog(null,
                    homeFrame.localisation(homeFrame.getResourceBundle(), Constants.KEY),
                    "",
                    JOptionPane.QUESTION_MESSAGE);

            try {
                homeFrame.getLock().lock();
                homeFrame.setResponse(createResponse(name, answer));
                homeFrame.treatmentResponseWithoutFrame(homeFrame::show);
                homeFrame.getLock().unlock();
            } catch (VariableException ex) {
                homeFrame.exception(ex.getMessage());
            }
        }
    }

    private final class RemoveLower extends ChangeProductFrame {
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
                protected void createResponse(Product product, Long key) {
                }

                @Override
                protected void sentProduct(Long key, Product product) {
                }

                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        Product product = addProduct();
                        createResponse(product);
                        sentProduct();
                    } catch (VariableException ex) {
                        exception(ex.getMessage());
                    }
                }

                private void createResponse(Product product) {
                    homeFrame.setResponse(new Response(Command.REMOVE_LOWER.getString()));
                    homeFrame.getResponse().setProduct(product);
                }

                private void sentProduct() {
                    delete.dispatchEvent(new WindowEvent(getFrame(), WindowEvent.WINDOW_CLOSING));
                    homeFrame.treatmentResponseWithoutFrame(homeFrame::show);
                }
            });
        }

    }

}
