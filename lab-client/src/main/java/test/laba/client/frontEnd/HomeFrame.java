package test.laba.client.frontEnd;

import test.laba.client.frontEnd.Frames.*;
import test.laba.client.util.VariableParsing;
import test.laba.common.dataClasses.*;
import test.laba.common.exception.VariableException;
import test.laba.common.responses.Response;
import test.laba.common.responses.ResponseWithError;
import test.laba.client.util.Command;
import test.laba.client.util.Constants;

import javax.swing.*;
import javax.swing.plaf.TableHeaderUI;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

public class HomeFrame extends FrameProduct implements Runnable {
    private TablePanel mainPanel;
    private String login = "test";
    private TableModule tableModule;
    private JLabel nameUser;
    private Response response;
    private final Condition condition;
    private HashMap<Long, Product> graphicCollection;
    private final Lock lock;
    private final Color green = Color.getHSBColor((float) 0.44, (float) 0.60, (float) 0.80);
    private final Color blue = Color.getHSBColor(0.67F, 0.30F, 0.84F);
    private int lastClick =-1;


    ///////////

    public HomeFrame(Condition condition, Lock lock, String login, Response response, ResourceBundle resourceBundle) {
        super(new JFrame(),resourceBundle);
        this.login = login;
        this.response = response;
        this.lock = lock;
        this.condition = condition;
        this.nameUser = createUserName();
    }

    public void run() {
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        jFrame.setName(localisation(resourceBundle, Constants.TITLE));
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setPreferredSize(new Dimension(screenSize.width - 150, screenSize.height - 50));
        jFrame.setMinimumSize(new Dimension((int)(screenSize.width / 1.2), (int)(screenSize.height / 1.2)));
        jFrame.setLocation(100, 100);
        jFrame.setLayout(new BorderLayout());
        tableModule = new TableModule(resourceBundle);

        mainPanel = createTable();
        mainPanel.setOpaque(true);
        jFrame.getContentPane().add(BorderLayout.CENTER, mainPanel);

        JButton manCost = createButtonCommand(Command.AVERAGE_OF_MANUFACTURE_COST.getString(), Constants.AVERAGE_OF_MANUFACTURE_COST);
        JButton groupCountingByPrice = createButtonCommand(Command.GROUP_COUNTING_BY_PRICE.getString(), Constants.GROUP_COUNTING_BY_PRICE);
        JButton filter = createFilter(Command.FILTER.getString(), Constants.FILTER);





        JPanel leftPanel = new JPanel();
        JPanel upPanel = new JPanel();
        JPanel downPAnel = new JPanel();

        upPanel.setLayout(new BoxLayout(upPanel, BoxLayout.X_AXIS));
        upPanel.add(createLanguage(Color.white), BorderLayout.WEST);
        upPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        upPanel.add(filter, BorderLayout.WEST);
        upPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        upPanel.add(groupCountingByPrice, BorderLayout.WEST);
        upPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        upPanel.add(manCost, BorderLayout.WEST);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        jPanel.setBackground(Color.BLACK);

        jPanel.add(nameUser, BorderLayout.EAST);
        upPanel.add(jPanel, BorderLayout.AFTER_LINE_ENDS);
        upPanel.add(Box.createRigidArea(new Dimension(5, 0)));


        //repaintAll();
        JButton picture = createPictureButton("graphics", green, "picture.png", new Picture(this,createUserName()));
        JButton restart = createPictureButton("show", green, "restart.png", new CommandWithoutAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                treatmentResponseWithCommandName(((JButton) e.getSource()).getName(), HomeFrame.this::showNothing);
            }
        });
        JButton help = createPictureButton("help", green, "question.png", new CommandWithoutAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                treatmentResponseWithCommandName(((JButton) e.getSource()).getName(), HomeFrame.this::showHelp);
            }
        });
        JButton history = createPictureButton("history", green, "history.png", new CommandWithoutAction());
        JButton script = createPictureButton("script", green, "script.png", new Script());
        JButton info = createPictureButton("info", green, "info.png", new CommandWithoutAction());

        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(picture);
        leftPanel.add(restart);
        leftPanel.add(help);
        leftPanel.add(info);
        leftPanel.add(history);
        leftPanel.add(script);


/////////////////

        JButton trash = createPictureButton("trash", green, "trash.png", e -> treatmentResponseWithCommandName("clear", this::show));
        trash.setPreferredSize(new Dimension(95, 95));
        JButton minus = createPictureButton("minus", Color.white, "minus.png", new Minus());
        JButton plus = createPictureButton("plus", Color.white, "plus.png", new Plus(resourceBundle));


        downPAnel.setLayout(new BorderLayout());
        downPAnel.setBackground(Color.WHITE);
        downPAnel.add(trash, BorderLayout.WEST);
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.add(minus);
        panel.add(plus);
        downPAnel.add(panel, BorderLayout.EAST);

        leftPanel.setBackground(green);
        upPanel.setBackground(Color.BLACK);

        mainPanel.setPreferredSize(new Dimension(jFrame.getWidth() / 2, jFrame.getHeight() / 2));
        upPanel.setPreferredSize(new Dimension(jFrame.getWidth(), 85));
        downPAnel.setPreferredSize(new Dimension(jFrame.getWidth(), 115));
        leftPanel.setPreferredSize(new Dimension(95, jFrame.getHeight()));

        jFrame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        jFrame.getContentPane().add(BorderLayout.WEST, leftPanel);
        jFrame.getContentPane().add(BorderLayout.NORTH, upPanel);
        jFrame.getContentPane().add(BorderLayout.SOUTH, downPAnel);

        jFrame.repaint();
      //  repaintAll();
        jFrame.setVisible(true);
    }


    private TablePanel createTable(){
        JTable table = new JTable(tableModule);
        table.setFont(new Font("Safari", Font.BOLD, 13));
        JTableHeader tableHeader = table.getTableHeader();
        table.setAutoCreateRowSorter(true);
        table.setUpdateSelectionOnSort(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableHeader.setBackground(blue);
        tableHeader.setFont(new Font("Safari", Font.PLAIN, 13));
        tableHeader.setFocusable(false);
        return new TablePanel(table) {
            @Override
            protected void outputSelection() {
                new ChangeProductFrameTable(this, tableModule, resourceBundle) {

                    @Override
                    protected void addOkListener() {
                        ok.addActionListener(new OkListener() {
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
                                    createResponse(product, Long.valueOf(getDescription(localisation(resourceBundle, Constants.ID))));
                                    sentProduct();
                                } catch (VariableException ex) {
                                    exception(ex.getMessage());
                                }
                            }

                            private void sentProduct() {
                                jFrame.dispatchEvent(new WindowEvent(jFrame, WindowEvent.WINDOW_CLOSING));
                                treatmentResponseWithoutFrame(HomeFrame.this::show);
                            }
                        });
                    }
                }.actionPerformed(new ActionEvent(this, 1, Command.UPDATE_ID.getString()));
            }
        };
    }

    private JLabel createUserName(){
        JLabel userName = new JLabel(login);
        userName.setForeground(Color.WHITE);
        userName.setFont(underLine(new Font("Safari", Font.BOLD, 38)));
        return userName;
    }


    private void repaint() {
        response = new Response("show");
        mainPanel.repaint();
    }

    private void repaintAll() {
        response = new Response("show");
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
    private void repaintFilter(){
        repaint();
    }


    private class CommandWithoutAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            treatmentResponseWithCommandName(((JButton) e.getSource()).getName(), HomeFrame.this::show);
        }

    }

    private class Plus extends ChangeProductFrame {
        public Plus(ResourceBundle resourceBundle) {
            super(resourceBundle);
        }

        @Override
        protected void addOkListener() {
            ok.addActionListener(new OkListener() {
                @Override
                protected void createResponse(Product product, Long key) {
                    response = new Response("insert_null");
                    response.setProduct(product);
                    response.setKeyOrID(key);
                }

                @Override
                protected void sentProduct(Long key, Product product) {
                    lock.lock();
                    jFrame.dispatchEvent(new WindowEvent(jFrame, WindowEvent.WINDOW_CLOSING));
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

    private class Minus implements ActionListener {
        private final JFrame delete = new JFrame();
        private final JPanel leftMinusPanel = new JPanel();

        @Override
        public void actionPerformed(ActionEvent e) {
            delete.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            delete.setSize(screenSize.width / 2, screenSize.height / 4);
            delete.setLocation(30, 30);
            delete.setMinimumSize(new Dimension(screenSize.width / 2, screenSize.height / 10 * 5));
            delete.setLayout(new BorderLayout());
            revalidate(leftMinusPanel);

            leftMinusPanel.setLayout(new BoxLayout(leftMinusPanel, BoxLayout.Y_AXIS));

            JRadioButton removeKey = new JRadioButton(localisation(resourceBundle, Constants.REMOVE_KEY));
            removeKey.addActionListener(new RemoveKeyOrRemoveLowerKey(Command.REMOVE_KEY.getString()));
            JRadioButton removeLowerKey = new JRadioButton(localisation(resourceBundle, Constants.REMOVE_LOWER_KEY));
            removeLowerKey.addActionListener(new RemoveKeyOrRemoveLowerKey(Command.REMOVE_LOWER_KEY.getString()));
            JRadioButton removeLower = new JRadioButton(localisation(resourceBundle, Constants.REMOVE_LOWER));
            removeLower.addActionListener(new RemoveLower(resourceBundle));
            JRadioButton removeAnyByUnitOfMeasure = new JRadioButton(localisation(resourceBundle, Constants.REMOVE_ANY_BY_UNIT_OF_MEASURE));
            removeAnyByUnitOfMeasure.addActionListener(new RemoveAnyByUnitOfMeasure(Command.REMOVE_ANY_BY_UNIT_OF_MEASURE.getString()));

            ButtonGroup buttonGroup = new ButtonGroup();
            buttonGroup.add(removeKey);
            buttonGroup.add(removeLower);
            buttonGroup.add(removeLowerKey);
            buttonGroup.add(removeAnyByUnitOfMeasure);

            delete.add(leftMinusPanel, BorderLayout.WEST);

            leftMinusPanel.add(removeKey);
            leftMinusPanel.add(removeLowerKey);
            leftMinusPanel.add(removeLower);
            leftMinusPanel.add(removeAnyByUnitOfMeasure);

            delete.setVisible(true);
        }

        private class RemoveKeyOrRemoveLowerKey implements ActionListener {
            private final String name;

            public RemoveKeyOrRemoveLowerKey(String name) {
                this.name = name;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame removeFrame = new JFrame();
                removeFrame.setSize(screenSize.width / 8, screenSize.height / 8);
                removeFrame.setLocation(50, 50);
                removeFrame.setMinimumSize(new Dimension(screenSize.width / 6, screenSize.height / 6));
                removeFrame.setLayout(new BorderLayout());

                JLabel key = new JLabel(localisation(resourceBundle, Constants.KEY));
                JTextField textKey = new JTextField();
                textKey.setPreferredSize(new Dimension(100, 20));

                JButton ok = new JButton(localisation(resourceBundle, Constants.OK));
                ok.addActionListener(new OkListener(name, textKey, removeFrame, resourceBundle) {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            lock.lock();
                            response = createResponse();
                            treatmentResponseWithFrame(HomeFrame.this::show, jFrame);
                            lock.unlock();
                        } catch (VariableException ex) {
                            exception(ex.getMessage());
                        }
                    }
                    @Override
                    public Response createResponse() throws VariableException {
                        Response createdResponse = new Response(command);
                        Long key = VariableParsing.toLongNumber(localisation(resourceBundle, Constants.KEY), ((JTextField) textKey).getText());
                        createdResponse.setKeyOrID(key);
                        return createdResponse;
                    }


                });

                JPanel removePanel = new JPanel();
                removePanel.setLayout(new BoxLayout(removePanel, BoxLayout.Y_AXIS));

                removePanel.add(Box.createRigidArea(new Dimension(0, 10)));
                removePanel.add(key);
                removePanel.add(textKey);
                removePanel.add(Box.createRigidArea(new Dimension(0, 50)));
                removePanel.add(ok);
                removePanel.revalidate();
                removePanel.repaint();

                removeFrame.add(removePanel);
                removeFrame.setVisible(true);
            }
        }

        private class RemoveAnyByUnitOfMeasure implements ActionListener {
            private final String name;

            public RemoveAnyByUnitOfMeasure(String name) {
                this.name = name;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame removeFrame = new JFrame();
                removeFrame.setSize(screenSize.width / 8, screenSize.height / 8);
                removeFrame.setLocation(50, 50);
                removeFrame.setMinimumSize(new Dimension(screenSize.width / 6, screenSize.height / 6));
                removeFrame.setLayout(new BorderLayout());

                JLabel unitOfMeasure = new JLabel(localisation(resourceBundle, Constants.UNIT_OF_MEASURE));
                JMenu menu = createUMMenu(localisation(resourceBundle, Constants.UNIT_OF_MEASURE));
                JMenuBar menuBar = unitOfMeasureButton(menu);

                JButton ok = new JButton(localisation(resourceBundle, Constants.OK));
                ok.addActionListener(new OkListener(name, menu, removeFrame, resourceBundle) {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            response = createResponse();
                            treatmentResponseWithFrame(HomeFrame.this::show, jFrame);
                        } catch (VariableException ex) {
                            exception(ex.getMessage());
                        }
                    }

                    @Override
                    public Response createResponse() throws VariableException {
                        Response createdResponse = new Response(command);
                        UnitOfMeasure unitOfMeasure1 = VariableParsing.toRightUnitOfMeasure(((JMenu) textKey).getText(), localisation(resourceBundle, Constants.UNIT_OF_MEASURE));
                        createdResponse.setUnitOfMeasure(unitOfMeasure1);
                        return createdResponse;
                    }
                });

                JPanel removePanel = new JPanel();
                removePanel.setLayout(new BoxLayout(removePanel, BoxLayout.Y_AXIS));

                removePanel.add(Box.createRigidArea(new Dimension(0, 10)));
                removePanel.add(unitOfMeasure);
                removePanel.add(menuBar);
                removePanel.add(ok);
                removePanel.add(Box.createRigidArea(new Dimension(0, 50)));
                removePanel.revalidate();
                removePanel.repaint();

                removeFrame.add(removePanel);
                removeFrame.setVisible(true);
            }

        }

        private class RemoveLower extends ChangeProductFrame {
            public RemoveLower(ResourceBundle resourceBundle) {
                super(resourceBundle);
            }

            @Override
            protected void addKey() {
            }

            @Override
            protected void addOkListener() {
                ok.addActionListener(new OkListener() {
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
                            sentProduct(product);
                        } catch (VariableException ex) {
                            exception(ex.getMessage());
                        }
                    }

                    private void createResponse(Product product) {
                        response = new Response(Command.REMOVE_LOWER.getString());
                        response.setProduct(product);
                    }

                    private void sentProduct(Product product) {
                        delete.dispatchEvent(new WindowEvent(jFrame, WindowEvent.WINDOW_CLOSING));
                        treatmentResponseWithoutFrame(HomeFrame.this::show);
                    }
                });
            }

        }

    }


    private class Script implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle(localisation(resourceBundle, Constants.CHOOSE_DIRECTORY));
            // Определение режима - только каталог
            int result = fileChooser.showOpenDialog(null);
            // Если директория выбрана, покажем ее в сообщении
            if (result == JFileChooser.APPROVE_OPTION) {
                response = new Response(Command.EXECUTE_SCRIPT.getString());
                response.setMessage(fileChooser.getSelectedFile().getAbsolutePath());

                treatmentResponseWithoutFrame(HomeFrame.this::showScript);
            }
        }
    }

    private void treatmentResponseWithFrame(IFunction show, JFrame jFrame) {
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
    public void treatmentAnimation(String name, JFrame jFrame){
        lock.lock();
        response = new Response(name);
        treatmentResponseWithFrame(this::show, jFrame);
        lock.unlock();
    }
    public void treatmentAnimation(IFunctionResponse newResponse, JFrame jFrame){
        lock.lock();
        response = newResponse.createResponse();
        treatmentResponseWithFrame(this::show, jFrame);
        lock.unlock();
    }

    private void treatmentResponseWithoutFrame(IFunction showResponse) {
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

    private JButton createFilter(String name, Constants command) {
        JButton jButton;
        jButton = new JButton(localisation(resourceBundle, command));
        jButton.setFont(new Font("Safari", Font.ITALIC, 20));
        jButton.setBackground(Color.BLACK);
        jButton.setForeground(Color.WHITE);
        jButton.setFocusPainted(false);
        jButton.setBorderPainted(false);
        jButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new Filter(tableModule, resourceBundle) {
                            @Override
                            public void repaint() {
                                repaintFilter();
                            }
                        }.actionPerformed(e,jFrame);
                    }
                });
        return jButton;
    }

    private JButton createButtonCommand(String name, Constants command) {
        JButton jButton = new JButton(localisation(resourceBundle, command));
        jButton.setFont(new Font("Safari", Font.ITALIC, 20));
        jButton.setBackground(Color.BLACK);
        jButton.setForeground(Color.WHITE);
        jButton.setFocusPainted(false);
        jButton.setBorderPainted(false);
        jButton.addActionListener((ActionEvent actionEvent) -> {
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
                show(localisation(resourceBundle, command) + '\n' + response.getCommand());
            }

        });
        return jButton;
    }

    private void showNothing(String message) {
    }

    public Response getResponse() {
        return response;
    }

    public void prepeareAnswer(Response response) {
        this.response = response;
    }



    public void setGraphicCollection(HashMap<Long, Product> graphicCollection) {
        this.graphicCollection = graphicCollection;
    }

    public HashMap<Long, Product> getGraphicMap() {
        return graphicCollection;
    }

    private void showHelp(String s) {
        show(localisation(resourceBundle, Constants.HELP));
    }

    private interface IFunction {
        void function(String oldField);
    }
    public interface IFunctionResponse {
        Response createResponse();
    }

}
