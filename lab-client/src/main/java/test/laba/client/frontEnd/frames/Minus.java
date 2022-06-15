package test.laba.client.frontEnd.frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import test.laba.client.frontEnd.frames.changeProductFrames.ChangeProductFrame;
import test.laba.client.util.Command;
import test.laba.client.util.Constants;
import test.laba.client.util.VariableParsing;
import test.laba.common.dataClasses.Product;
import test.laba.common.dataClasses.UnitOfMeasure;
import test.laba.common.exception.VariableException;
import test.laba.common.responses.Response;

public class Minus implements ActionListener {
    private final JFrame delete = new JFrame();
    private final JPanel leftMinusPanel = new JPanel();
    private final HomeFrame homeFrame;
    private final int fourthSmallerPanelSize = 4;

    public Minus(HomeFrame homeFrame) {
        this.homeFrame = homeFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        delete.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        delete.setSize(homeFrame.getScreenSize().width / 2, homeFrame.getScreenSize().height / fourthSmallerPanelSize);
        delete.setLocationRelativeTo(homeFrame.getMainPanel());
        delete.setMinimumSize(new Dimension(homeFrame.screenSize.width / 2, homeFrame.screenSize.height / 2));
        delete.setLayout(new BorderLayout());
        homeFrame.revalidate(leftMinusPanel);
        leftMinusPanel.setLayout(new BoxLayout(leftMinusPanel, BoxLayout.Y_AXIS));
        JRadioButton removeKey = createRadioButton(homeFrame.localisation(homeFrame.getResourceBundle(), Constants.REMOVE_KEY), new RemoveKeyOrRemoveLowerKey(Command.REMOVE_KEY.getString()));
        JRadioButton removeLowerKey = createRadioButton(homeFrame.localisation(homeFrame.getResourceBundle(), Constants.REMOVE_LOWER_KEY), new RemoveKeyOrRemoveLowerKey(Command.REMOVE_LOWER_KEY.getString()));
        JRadioButton removeLower = createRadioButton(homeFrame.localisation(homeFrame.getResourceBundle(), Constants.REMOVE_LOWER), new RemoveLower(homeFrame.getResourceBundle()));
        JRadioButton removeAnyByUnitOfMeasure = new JRadioButton(homeFrame.localisation(homeFrame.getResourceBundle(), Constants.REMOVE_ANY_BY_UNIT_OF_MEASURE));
        removeAnyByUnitOfMeasure.addActionListener(new RemoveKeyOrRemoveLowerKey(Command.REMOVE_ANY_BY_UNIT_OF_MEASURE.getString()) {
            @Override
            protected JComponent createButtons(JPanel panel) {
                JLabel unitOfMeasure = new JLabel(homeFrame.localisation(homeFrame.getResourceBundle(), Constants.UNIT_OF_MEASURE));
                JMenu menu = homeFrame.createUMMenu(homeFrame.localisation(homeFrame.getResourceBundle(), Constants.UNIT_OF_MEASURE));
                JMenuBar menuBar = homeFrame.unitOfMeasureButton(menu);
                panel.add(unitOfMeasure);
                panel.add(Box.createRigidArea(new Dimension(0, getEnterBetweenLabelAndText())));
                panel.add(menuBar);
                return menu;
            }

            @Override
            public Response createResponse(String command, JComponent component) throws VariableException {
                Response createdResponse = new Response(command);
                System.out.println(homeFrame.getTableModule().delocalizationUnitOfMeasure(((JMenu) component).getText()));
                UnitOfMeasure unitOfMeasure1 = VariableParsing.toRightUnitOfMeasure(homeFrame.localisation(homeFrame.getResourceBundle(), Constants.UNIT_OF_MEASURE), homeFrame.getTableModule().delocalizationUnitOfMeasure(((JMenu) component).getText()));
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
        private final int location = 50;
        private final Dimension textKeySize = new Dimension(100, 20);
        private final int seventhFrameSmaller = 7;
        private final int enterBetweenOk = 20;
        private final int enterBetweenLabelAndText = 5;
        private JButton ok;

        RemoveKeyOrRemoveLowerKey(String name) {
            this.name = name;
        }

        protected JComponent createButtons(JPanel panel) {
            JLabel key = new JLabel(homeFrame.localisation(homeFrame.getResourceBundle(), Constants.KEY));
            JTextField textKey = new JTextField();
            textKey.setPreferredSize(textKeySize);
            panel.add(key);
            panel.add(Box.createRigidArea(new Dimension(0, enterBetweenLabelAndText)));
            panel.add(textKey);
            return textKey;
        }

        public Response createResponse(String command, JComponent component) throws VariableException {
            Response createdResponse = new Response(command);
            Long key = VariableParsing.toLongNumber(homeFrame.localisation(homeFrame.getResourceBundle(), Constants.KEY), ((JTextField) component).getText());
            createdResponse.setKeyOrID(key);
            return createdResponse;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame removeFrame = new JFrame();
            removeFrame.setPreferredSize(new Dimension(homeFrame.screenSize.width / seventhFrameSmaller, homeFrame.screenSize.height / seventhFrameSmaller));
            removeFrame.setLocation(location, location);
            removeFrame.setLocationRelativeTo(delete);
            removeFrame.setLayout(new BorderLayout());

            JPanel removePanel = new JPanel();
            JComponent component = createButtons(removePanel);
            ok = new JButton(homeFrame.localisation(homeFrame.getResourceBundle(), Constants.OK));
            ok.addActionListener(new OkListener(name, removeFrame, homeFrame.getResourceBundle()) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        homeFrame.getLock().lock();
                        homeFrame.setResponse(createResponse());
                        homeFrame.treatmentResponseWithFrame(homeFrame::show, getFrame());
                        homeFrame.getLock().unlock();
                    } catch (VariableException ex) {
                        exception(ex.getMessage());
                    }
                }

                @Override
                public Response createResponse() throws VariableException {
                    return RemoveKeyOrRemoveLowerKey.this.createResponse(getCommand(), component);
                }
            });

            removePanel.setLayout(new BoxLayout(removePanel, BoxLayout.Y_AXIS));
            removePanel.add(Box.createRigidArea(new Dimension(0, enterBetweenOk)));
            removePanel.add(ok);
            removePanel.revalidate();
            removePanel.repaint();

            removeFrame.pack();

            removeFrame.add(removePanel);
            removeFrame.setVisible(true);
        }

        public int getEnterBetweenLabelAndText() {
            return enterBetweenLabelAndText;
        }
    }
    private final class RemoveLower extends ChangeProductFrame {
        private RemoveLower(ResourceBundle resourceBundle) {
            super(resourceBundle);
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
