package test.laba.client.frontEnd.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.ResourceBundle;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;
import test.laba.client.frontEnd.frames.animation.Picture;
import test.laba.client.frontEnd.frames.changeProductFrames.ChangeProductFrame;
import test.laba.client.frontEnd.frames.changeProductFrames.ChangeProductFrameTable;
import test.laba.client.frontEnd.frames.changeProductFrames.FrameProduct;
import test.laba.client.frontEnd.frames.table.TableModule;
import test.laba.client.frontEnd.frames.table.TablePanel;
import test.laba.client.util.Pictures;
import test.laba.common.dataClasses.Product;
import test.laba.common.exception.VariableException;
import test.laba.common.responses.Response;
import test.laba.common.responses.ResponseWithError;
import test.laba.client.util.Command;
import test.laba.client.util.Constants;

import javax.swing.table.JTableHeader;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class HomeFrame extends FrameProduct implements Runnable {
    private TablePanel mainPanel;
    private final String login;
    private TableModule tableModule;
    private final JLabel nameUser;
    private Response response;
    private final Condition condition;
    private HashMap<Long, Product> graphicCollection;
    private final Lock lock;
    private final Color green = Color.getHSBColor((float) 0.44, (float) 0.60, (float) 0.80);
    private final Color blue = Color.getHSBColor(0.67F, 0.30F, 0.84F);
    private Plus plusListener;
    private final JPanel upPanel = new JPanel();
    private final Dimension preferredSize = new Dimension(screenSize.width - 150, screenSize.height - 50);
    private final Dimension minimumSize = new Dimension((int) (screenSize.width / 1.2), (int) (screenSize.height / 1.2));
    private final Dimension trashSize = new Dimension(95, 95);
    private final int location = 100;
    private final int upPanelHeight = 85;
    private final int downPanelHeight = 115;
    private final int leftPanelWeight = 95;
    private final int enterBetweenUpLabels = 5;
    private final int standardSizeTextLabelsOnTable = 13;
    private final int userNameSize = 38;
    private JButton minus;
    private final int standardSizeText = 20;

    public HomeFrame(Condition condition, Lock lock, String login, Response response, ResourceBundle resourceBundle) {
        super(new JFrame(), resourceBundle);
        this.login = login;
        this.response = response;
        this.lock = lock;
        this.condition = condition;
        this.nameUser = createUserName();
    }

    @Override
    public void run() {
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        getFrame().setName(localisation(getResourceBundle(), Constants.TITLE));
        getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getFrame().setPreferredSize(preferredSize);
        getFrame().setMinimumSize(minimumSize);
        getFrame().setLocation(location, location);
        getFrame().setLayout(new BorderLayout());
        JButton trash = createPictureButton("trash", green, Pictures.TRASH.getPath(), e -> treatmentResponseWithCommandName(Command.CLEAR.getString(), this::show));
        trash.setPreferredSize(trashSize);
        plusListener = new Plus(getResourceBundle());
        paintTextLabels();
        JPanel downPanel = new JPanel();
        downPanel.setLayout(new BorderLayout());
        downPanel.setBackground(Color.WHITE);
        downPanel.add(trash, BorderLayout.WEST);
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        minus = createPictureButton("minus", Color.white, Pictures.MINUS.getPath(), new Minus(this));
        panel.add(minus);
        JButton plus = createPictureButton("plus", Color.white, Pictures.PLUS.getPath(), plusListener);
        panel.add(plus);
        downPanel.add(panel, BorderLayout.EAST);
        downPanel.setPreferredSize(new Dimension(getFrame().getWidth(), downPanelHeight));
        createTable();
        mainPanel.setOpaque(true);
        mainPanel.setPreferredSize(new Dimension(getFrame().getWidth() / 2, getFrame().getHeight() / 2));
        upPanel.setBackground(Color.BLACK);
        upPanel.setPreferredSize(new Dimension(getFrame().getWidth(), upPanelHeight));
        upPanel.setLayout(new BoxLayout(upPanel, BoxLayout.X_AXIS));
        getFrame().getContentPane().add(BorderLayout.CENTER, mainPanel);
        getFrame().getContentPane().add(BorderLayout.CENTER, mainPanel);
        getFrame().getContentPane().add(BorderLayout.WEST, createLeftPanel());
        getFrame().getContentPane().add(BorderLayout.NORTH, upPanel);
        getFrame().getContentPane().add(BorderLayout.SOUTH, downPanel);
        getFrame().repaint();
        repaintAll();
        getFrame().setVisible(true);
    }

    private JPanel createLeftPanel() {
        minus = createPictureButton("minus", Color.white, Pictures.MINUS.getPath(), new Minus(this));

        JButton picture = createPictureButton("graphics", green, Pictures.ANIMATION.getPath(), new Picture(this, createUserName()));
        JButton restart = createPictureButton("show", green, Pictures.SHOW.getPath(), new CommandWithoutAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                treatmentResponseWithCommandName(((JButton) e.getSource()).getName(), HomeFrame.this::showNothing);
            }
        });
        JButton help = createPictureButton("help", green, Pictures.HELP.getPath(), new CommandWithoutAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                treatmentResponseWithCommandName(((JButton) e.getSource()).getName(), HomeFrame.this::showHelp);
            }
        });
        JButton history = createPictureButton("history", green, Pictures.HISTORY.getPath(), new CommandWithoutAction());
        JButton script = createPictureButton("script", green, Pictures.SCRIPT.getPath(), new Script());
        JButton info = createPictureButton("info", green, Pictures.INFO.getPath(), new CommandWithoutAction());

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(green);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(leftPanelWeight, getFrame().getHeight()));
        leftPanel.add(picture);
        leftPanel.add(restart);
        leftPanel.add(help);
        leftPanel.add(info);
        leftPanel.add(history);
        leftPanel.add(script);
        return leftPanel;
    }

    @Override
    public void repaintForLanguage() {
        upPanel.removeAll();
        upPanel.revalidate();
        paintTextLabels();
        getFrame().remove(mainPanel);
        createTable();
        upPanel.repaint();
        getFrame().repaint();
        repaintAll();

    }

    private void paintTextLabels() {
        JButton manCost = createButtonCommand(Command.AVERAGE_OF_MANUFACTURE_COST.getString(), Constants.AVERAGE_OF_MANUFACTURE_COST);
        JButton groupCountingByPrice = createButtonCommand(Command.GROUP_COUNTING_BY_PRICE.getString(), Constants.GROUP_COUNTING_BY_PRICE);
        JButton filter = createFilter();

        upPanel.add(createLanguage(Color.white), BorderLayout.WEST);
        upPanel.add(Box.createRigidArea(new Dimension(enterBetweenUpLabels, 0)));
        upPanel.add(filter, BorderLayout.WEST);
        upPanel.add(Box.createRigidArea(new Dimension(enterBetweenUpLabels, 0)));
        upPanel.add(groupCountingByPrice, BorderLayout.WEST);
        upPanel.add(Box.createRigidArea(new Dimension(enterBetweenUpLabels, 0)));
        upPanel.add(manCost, BorderLayout.WEST);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        jPanel.setBackground(Color.BLACK);

        jPanel.add(nameUser, BorderLayout.EAST);
        upPanel.add(jPanel, BorderLayout.AFTER_LINE_ENDS);
        upPanel.add(Box.createRigidArea(new Dimension(enterBetweenUpLabels, 0)));
        plusListener.setResourceBundle(getResourceBundle());

    }


    private void createTable() {
        tableModule = new TableModule(getResourceBundle());
        JTable table = new JTable(tableModule);
        table.setFont(new Font("Safari", Font.BOLD, standardSizeTextLabelsOnTable));
        JTableHeader tableHeader = table.getTableHeader();
        table.setAutoCreateRowSorter(true);
        table.setUpdateSelectionOnSort(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableHeader.setBackground(blue);
        tableHeader.setFont(new Font("Safari", Font.PLAIN, standardSizeTextLabelsOnTable));
        tableHeader.setFocusable(false);
        mainPanel = createTablePanel(table);
        mainPanel.repaint();
        getFrame().getContentPane().add(BorderLayout.CENTER, mainPanel);
        getFrame().repaint();
    }

    private TablePanel createTablePanel(JTable table) {
        return new TablePanel(table) {
            @Override
            protected void outputSelection() {
                new ChangeProductFrameTable(this, tableModule, getResourceBundle()) {
                    @Override
                    protected void addOkListener() {
                        getOk().addActionListener(new OkListener() {
                            @Override
                            protected void createResponse(Product product, Long key) {
                                response = new Response(Command.UPDATE_ID.getString());
                                response.setProduct(product);
                                response.setFlagUdateID(true);
                                response.setKeyOrID(key);
                            }
                            @Override
                            protected void sentProduct(Long key, Product product) {
                            }

                            @Override
                            public void actionPerformed(ActionEvent e) {
                                try {
                                    Product product = addProduct();
                                    createResponse(product, Long.valueOf(getDescription(localisation(getResourceBundle(), Constants.ID))));
                                    sentProduct();
                                } catch (VariableException ex) {
                                    exception(ex.getMessage());
                                }
                            }

                            private void sentProduct() {
                                getFrame().dispatchEvent(new WindowEvent(getFrame(), WindowEvent.WINDOW_CLOSING));
                                treatmentResponseWithoutFrame(HomeFrame.this::show);
                            }
                        });
                    }
                }.actionPerformed(new ActionEvent(this, 1, Command.UPDATE_ID.getString()));
            }
        };
    }

    private JLabel createUserName() {
        JLabel userName = new JLabel(login);
        userName.setForeground(Color.WHITE);
        userName.setFont(underLine(new Font("Safari", Font.BOLD, userNameSize)));
        return userName;
    }

    private void repaint() {
        response = new Response(Command.SHOW.getString());
        mainPanel.repaint();
    }

    private void repaintAll() {
        response = new Response(Command.SHOW.getString());
        response.setAddToHistory(false);
        lock.lock();
        condition.signal();
        try {
            condition.await();
        } catch (InterruptedException ex) {
            exception(ex.getMessage());
        }
        lock.unlock();
        tableModule.addProducts(response.getProductHashMap());
        graphicCollection = response.getProductHashMap();
        repaint();
    }

    private void repaintFilter() {
        repaint();
    }

    public void treatmentResponseWithFrame(IFunction show, JFrame jFrame) {
        if (jFrame != null) {
            close(jFrame);
        }
        treatmentResponseWithoutFrame(show);
    }

    public void treatmentResponseWithCommandName(String name, IFunction show) {
        lock.lock();
        response = new Response(name);
        treatmentResponseWithoutFrame(show);
        lock.unlock();

    }

    public void treatmentAnimation(IFunctionResponse newResponse, JFrame jFrame) throws VariableException {
        lock.lock();
        response = newResponse.createResponse();
        treatmentResponseWithFrame(this::show, jFrame);
        lock.unlock();
    }

    void treatmentResponseWithoutFrame(IFunction showResponse) {
        lock.lock();
        condition.signal();
        try {
            condition.await();
            if (response instanceof ResponseWithError) {
                exception(response.getCommand());
            } else {
                showResponse.function(response.getCommand());
                repaintAll();
            }
        } catch (InterruptedException ex) {
            exception(ex.getMessage());
        }
        lock.unlock();
    }

    private JButton createPictureButton(String name, java.awt.Color colorBackground, String picturePath, ActionListener actionListener) {
        JButton button = new JButton(new ImageIcon(picturePath));
        button.setName(name);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setBackground(colorBackground);
        button.addActionListener(actionListener);
        return button;
    }

    private JButton createFilter() {
        JButton jButton;
        jButton = new JButton(localisation(getResourceBundle(), Constants.FILTER));
        jButton.setFont(new Font("Safari", Font.ITALIC, standardSizeText));
        jButton.setBackground(Color.BLACK);
        jButton.setForeground(Color.WHITE);
        jButton.setFocusPainted(false);
        jButton.setBorderPainted(false);
        jButton.addActionListener(
                e -> new Filter(tableModule, getResourceBundle()) {
                    @Override
                    public void repaint() {
                        repaintFilter();
                    }
                }.actionPerformed(getFrame()));
        return jButton;
    }

    private JButton createButtonCommand(String name, Constants command) {
        JButton jButton = new JButton(localisation(getResourceBundle(), command));
        jButton.setFont(new Font("Safari", Font.ITALIC, standardSizeText));
        jButton.setBackground(Color.BLACK);
        jButton.setForeground(Color.WHITE);
        jButton.setFocusPainted(false);
        jButton.setBorderPainted(false);
        jButton.addActionListener((ActionEvent actionEvent) -> createButtonCommandAction(name, command));
        return jButton;
    }

    private void createButtonCommandAction(String name, Constants command) {
        response = new Response(name);
        lock.lock();
        condition.signal();

        try {
            condition.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        lock.unlock();
        if (response instanceof ResponseWithError) {
            exception(response.getCommand());
        } else {
            show(localisation(getResourceBundle(), command) + '\n' + response.getCommand());
        }
    }

    private void showNothing(String message) {
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        lock.lock();
        this.response = response;
        lock.unlock();
    }

    public TableModule getTableModule() {
        return tableModule;
    }

    public void prepareAnswer(Response newResponse) {
        this.response = newResponse;
    }

    public void setGraphicCollection(HashMap<Long, Product> graphicCollection) {
        this.graphicCollection = graphicCollection;
    }

    public HashMap<Long, Product> getGraphicMap() {
        return graphicCollection;
    }

    private void showHelp(String s) {
        show(localisation(getResourceBundle(), Constants.HELP));
    }

    public TablePanel getMainPanel() {
        return mainPanel;
    }

    public Lock getLock() {
        return lock;
    }
    private class CommandWithoutAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            treatmentResponseWithCommandName(((JButton) e.getSource()).getName(), HomeFrame.this::show);
        }

    }


    private final class Plus extends ChangeProductFrame {
        private Plus(ResourceBundle resourceBundle) {
            super(resourceBundle);
        }

        @Override
        protected void addOkListener() {
            getOk().addActionListener(new OkListener() {
                @Override
                protected void createResponse(Product product, Long key) {
                    response = new Response(Command.INSERT_NULL.getString());
                    response.setProduct(product);
                    response.setKeyOrID(key);
                }

                @Override
                protected void sentProduct(Long key, Product product) {
                    lock.lock();
                    getFrame().dispatchEvent(new WindowEvent(getFrame(), WindowEvent.WINDOW_CLOSING));
                    condition.signal();

                    try {
                        condition.await();
                        if (response instanceof ResponseWithError) {
                            exception(response.getCommand());
                        } else {
                            show(response.getCommand());
                            tableModule.addProduct(key, product);
                            repaintAll();
                        }
                    } catch (InterruptedException ex) {
                        exception(ex.getMessage());
                    }

                    lock.unlock();
                }
            });
        }

    }
    private class Script implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle(localisation(getResourceBundle(), Constants.CHOOSE_DIRECTORY));
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                response = new Response(Command.EXECUTE_SCRIPT.getString());
                response.setMessage(fileChooser.getSelectedFile().getAbsolutePath());
                treatmentResponseWithoutFrame(HomeFrame.this::showScript);
            }
        }
    }

    public interface IFunction {
        void function(String oldField);
    }

    public interface IFunctionResponse {
        Response createResponse() throws VariableException;
    }

}
