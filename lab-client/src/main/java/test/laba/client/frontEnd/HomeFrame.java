package test.laba.client.frontEnd;

import test.laba.client.frontEnd.Frames.ChangeProductFrame;
import test.laba.client.frontEnd.Frames.ChangeProductFrameTable;
import test.laba.client.frontEnd.Frames.FrameProduct;
import test.laba.client.util.VariableParsing;
import test.laba.common.dataClasses.*;
import test.laba.common.exception.VariableException;
import test.laba.common.responses.Response;
import test.laba.common.responses.ResponseWithError;
import test.laba.client.util.Command;
import test.laba.client.util.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ResourceBundle;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class HomeFrame extends FrameProduct implements Runnable {
    private JPanel mainPanel = new JPanel();
    private final String login;
    private final JLabel userPicture = new JLabel();
    private TableModule tableModule;
    private final JLabel userName = new JLabel();
    private Response response;
    private final Condition condition;
    private final Lock lock;
    private final Color green = Color.getHSBColor((float) 0.40034366, (float) 0.8362069, (float) 0.9098039);


    ///////////

    public HomeFrame(Condition condition, Lock lock, String login, Response response) {
        super(new JFrame(), Local.getResourceBundleDeafult());
        this.login = login;
        this.response = response;
        this.lock = lock;
        this.condition = condition;
    }

    public void run() {
        System.out.println(resourceBundle.getLocale());
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        jFrame.setName(localisation(resourceBundle, Constants.TITLE));
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(screenSize.width - 250, screenSize.height - 150);
        jFrame.setMinimumSize(new Dimension(screenSize.width / 2, screenSize.height / 2));
        jFrame.setLayout(new BorderLayout());
        tableModule = new TableModule(resourceBundle);

        mainPanel = new TablePanel(new JTable(tableModule)) {
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
        mainPanel.setOpaque(true);
        jFrame.getContentPane().add(BorderLayout.CENTER, mainPanel);


        ImageIcon imageIcon = new ImageIcon("user1.png");
        userPicture.setIcon(imageIcon);

        userName.setText(login);
        userName.setForeground(Color.WHITE);
        userName.setFont(underLine(new Font("Safari", Font.BOLD, 38)));

        JButton manCost = createButtonCommand(Command.AVERAGE_OF_MANUFACTURE_COST.getString(), Constants.AVERAGE_OF_MANUFACTURE_COST);
        JButton groupCountingByPrice = createButtonCommand(Command.GROUP_COUNTING_BY_PRICE.getString(), Constants.GROUP_COUNTING_BY_PRICE);


        JMenu lang = new JMenu(localisation(resourceBundle, Constants.LANGUAGE));
        lang.setPreferredSize(new Dimension(jFrame.getWidth() / 15, 250));
        lang.setFont(new Font("Safari", Font.PLAIN, 20));

        JMenuItem rus = new JMenuItem(localisation(resourceBundle, Constants.RUSSIAN));
        rus.setName(Local.russian);
        JMenuItem nor = new JMenuItem(localisation(resourceBundle, Constants.NORWEGIAN));
        nor.setName(Local.norwegian);
        JMenuItem fr = new JMenuItem(localisation(resourceBundle, Constants.FRENCH));
        fr.setName(Local.france);
        JMenuItem sp = new JMenuItem(localisation(resourceBundle, Constants.SPANISH));
        sp.setName(Local.spanish);


        changeMenuAndLAg(rus, lang);
        changeMenuAndLAg(nor, lang);
        changeMenuAndLAg(fr, lang);
        changeMenuAndLAg(sp, lang);

        lang.add(rus);
        lang.add(nor);
        lang.add(fr);
        lang.add(sp);

        JMenuBar language = new JMenuBar();
        language.add(lang);
        language.setBackground(green);
        language.setForeground(Color.BLACK);

        JMenuBar sortBy = createSortBy();

        JPanel leftPanel = new JPanel();
        JPanel upPanel = new JPanel();
        JPanel downPAnel = new JPanel();

        upPanel.setLayout(new BoxLayout(upPanel, BoxLayout.X_AXIS));
        upPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        upPanel.add(language, BorderLayout.WEST);
        upPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        upPanel.add(sortBy, BorderLayout.WEST);
        upPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        upPanel.add(groupCountingByPrice, BorderLayout.WEST);
        upPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        upPanel.add(manCost, BorderLayout.WEST);
        upPanel.add(Box.createRigidArea(new Dimension(screenSize.width / 3, 0)));
        upPanel.add(userName, BorderLayout.EAST);
        upPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        upPanel.add(userPicture, BorderLayout.NORTH);

        JButton picture = createPictureButton("graphics", green, "picture.png", new Picture());
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
        JButton minus = createPictureButton("minus", Color.white, "minus.png", new Minus());
        JButton plus = createPictureButton("plus", Color.white, "plus.png", new Plus(resourceBundle));


        downPAnel.setLayout(new BoxLayout(downPAnel, BoxLayout.X_AXIS));
        downPAnel.add(trash, BorderLayout.EAST);
        downPAnel.add(Box.createRigidArea(new Dimension(screenSize.width - 700, 0)));
        downPAnel.add(minus, BorderLayout.WEST);
        downPAnel.add(plus, BorderLayout.WEST);

        leftPanel.setBackground(green);
        upPanel.setBackground(Color.BLACK);

        mainPanel.setPreferredSize(new Dimension(jFrame.getWidth() / 2, jFrame.getHeight() / 2));
        leftPanel.setPreferredSize(new Dimension(jFrame.getWidth() / 16, jFrame.getHeight()));
        upPanel.setPreferredSize(new Dimension(jFrame.getWidth(), jFrame.getHeight() / 10));
        downPAnel.setPreferredSize(new Dimension(jFrame.getWidth(), jFrame.getHeight() / 8));

        jFrame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        jFrame.getContentPane().add(BorderLayout.WEST, leftPanel);
        jFrame.getContentPane().add(BorderLayout.NORTH, upPanel);
        jFrame.getContentPane().add(BorderLayout.SOUTH, downPAnel);

        jFrame.repaint();
        repaintAll();
        jFrame.pack();
        jFrame.setVisible(true);
    }

    private JMenuBar createSortBy() {
        JMenu sort = new JMenu(localisation(resourceBundle, Constants.SORT_BY));
        sort.setFont(new Font("Safari", Font.BOLD, 15));
        sort.setPreferredSize(new Dimension(100, 250));
        sort.setForeground(Color.WHITE);
        JMenuItem aToZ = new JMenuItem(localisation(resourceBundle, Constants.SORT_A_TO_Z));
        JMenuItem zToA = new JMenuItem(localisation(resourceBundle, Constants.SORT_Z_TO_A));
        JMenuItem random = new JMenuItem(localisation(resourceBundle, Constants.RANDOM));

        sort.add(aToZ);
        sort.add(zToA);
        sort.add(random);

        JMenuBar sortBy = new JMenuBar();
        sortBy.add(sort);
        sortBy.setBackground(Color.BLACK);
        sortBy.setForeground(Color.WHITE);

        return sortBy;
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
        repaint();
    }

    private class Picture implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame pictureFrame = new JFrame();
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            pictureFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            pictureFrame.setSize(screenSize.width - 250, screenSize.height);
            pictureFrame.setLocationRelativeTo(null);
            pictureFrame.setMinimumSize(new Dimension(screenSize.width / 2, screenSize.height / 2));

            JPanel upPanel = new JPanel();
            pictureFrame.getContentPane().add(BorderLayout.NORTH, upPanel);
            upPanel.setPreferredSize(new Dimension(jFrame.getWidth(), jFrame.getHeight() / 10));
            upPanel.setLayout(new BoxLayout(upPanel, BoxLayout.X_AXIS));
            upPanel.setBackground(Color.BLACK);

            upPanel.add(Box.createRigidArea(new Dimension(screenSize.width - 500, 0)));
            upPanel.add(userName);
            upPanel.add(Box.createRigidArea(new Dimension(5, 0)));
            upPanel.add(userPicture);
            JPanel activePanel = new JPanel();


            pictureFrame.setLayout(new BorderLayout());
            pictureFrame.getContentPane().add(upPanel, BorderLayout.NORTH);
            pictureFrame.getContentPane().add(activePanel, BorderLayout.CENTER);


            pictureFrame.setVisible(true);
        }
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
                            response = createResponse();
                            treatmentResponseWithFrame(HomeFrame.this::show);
                        } catch (VariableException ex) {
                            exception(ex.getMessage());
                        }
                    }

                    @Override
                    public Response createResponse() throws VariableException {
                        Response createdResponse = new Response(command);
                        Long key = VariableParsing.toLongNumber(((JTextField) textKey).getText(), localisation(resourceBundle, Constants.KEY));
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
                            treatmentResponseWithFrame(HomeFrame.this::show);
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
                System.out.println(fileChooser.getSelectedFile().getAbsolutePath());
                response = new Response(Command.EXECUTE_SCRIPT.getString());
                response.setMessage(fileChooser.getSelectedFile().getAbsolutePath());

                treatmentResponseWithoutFrame(HomeFrame.this::showScript);
            }
        }
    }

    private void treatmentResponseWithFrame(IFunction show) {
        if (jFrame != null) {
            close(jFrame);
        }
        treatmentResponseWithoutFrame(show);
    }

    private void treatmentResponseWithCommandName(String name, IFunction show) {
        response = new Response(name);
        treatmentResponseWithoutFrame(show);

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
        button.setBackground(colorBackground);
        button.addActionListener(actionListener);
        return button;
    }

    private JButton createButtonCommand(String name, Constants command) {
        JButton jButton;
        if (name.equals(Command.AVERAGE_OF_MANUFACTURE_COST.getString())) {
            jButton = new JButton(localisation(resourceBundle, Constants.AVERAGE_OF_MANUFACTURE_COST));
        } else {
            jButton = new JButton(localisation(resourceBundle, Constants.GROUP_COUNTING_BY_PRICE));
        }
        jButton.setFont(new Font("Safari", Font.ITALIC, 20));
        jButton.setBackground(Color.BLACK);
        jButton.setForeground(Color.WHITE);
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

    private void repaintFrame() {
        close();
        jFrame = new JFrame();
        run();
    }

    private void changeMenuAndLAg(JMenuItem jMenuItem, JMenu menu) {
        jMenuItem.addActionListener((ActionEvent e) -> {
            menu.setText(jMenuItem.getName());
            resourceBundle = Local.locals.get(jMenuItem.getName());
            repaintFrame();
        });
    }

    private void showHelp(String s) {
        show(localisation(resourceBundle, Constants.HELP));
        System.out.println(localisation(resourceBundle, Constants.HELP));
    }

    private interface IFunction {
        void function(String oldField);
    }

}
