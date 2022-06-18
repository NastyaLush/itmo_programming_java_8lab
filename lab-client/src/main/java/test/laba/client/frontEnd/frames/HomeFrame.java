package test.laba.client.frontEnd.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import test.laba.client.ClientApp;
import test.laba.client.frontEnd.frames.animation.Picture;
import test.laba.client.frontEnd.frames.changeProductFrames.ChangeProductDialog;
import test.laba.client.frontEnd.frames.changeProductFrames.ChangeProductTableDialog;
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

public class HomeFrame extends AbstractFrame implements Runnable {
    private static final int LOCATION = 100;
    private static final int UP_PANEL_HEIGHT = 85;
    private static final int DOWN_PANEL_HEIGHT = 115;
    private static final int LEFT_PANEL_WEIGHT = 95;
    private static final int ENTER_BETWEEN_UP_LABELS = 5;
    private static final int STANDARD_SIZE_TEXT_LABELS_ON_TABLE = 13;
    private static final int USER_NAME_SIZE = 38;
    private TablePanel mainPanel;
    private String login;
    private TableModule tableModule;
    private final JLabel nameUser;
    private final Color green = Color.getHSBColor((float) 0.44, (float) 0.60, (float) 0.80);
    private final Color blue = Color.getHSBColor(0.67F, 0.30F, 0.84F);
    private Plus plusListener;
    private final JPanel upPanel = new JPanel();
    private final Dimension preferredSize = new Dimension(screenSize.width - 150, screenSize.height - 50);
    private final Dimension minimumSize = new Dimension((int) (screenSize.width / 1.2), (int) (screenSize.height / 1.2));
    private final Dimension trashSize = new Dimension(95, 95);
    private final ClientApp clientApp;
    private HashMap<Long, Product> collection;
    private JButton minus;
    private final int standardSizeText = 20;

    public HomeFrame(String login, ResourceBundle resourceBundle, ClientApp clientApp) {
        super(new JFrame(), resourceBundle);
        this.login = login;
        this.nameUser = createUserName();
        this.clientApp = clientApp;
    }

    @Override
    public void run() {
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        getFrame().setName(localisation(Constants.TITLE));
        getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getFrame().setPreferredSize(preferredSize);
        getFrame().setMinimumSize(minimumSize);
        getFrame().setLocation(LOCATION, LOCATION);
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
        minus = createPictureButton("minus", Color.white, Pictures.MINUS.getPath(), new Minus(this, minus));
        panel.add(minus);
        JButton plus = createPictureButton("plus", Color.white, Pictures.PLUS.getPath(), plusListener);
        panel.add(plus);
        downPanel.add(panel, BorderLayout.EAST);
        downPanel.setPreferredSize(new Dimension(getFrame().getWidth(), DOWN_PANEL_HEIGHT));
        createTable();
        mainPanel.setOpaque(true);
        mainPanel.setPreferredSize(new Dimension(getFrame().getWidth() / 2, getFrame().getHeight() / 2));
        upPanel.setBackground(Color.BLACK);
        upPanel.setPreferredSize(new Dimension(getFrame().getWidth(), UP_PANEL_HEIGHT));
        upPanel.setLayout(new BoxLayout(upPanel, BoxLayout.X_AXIS));
        getFrame().getContentPane().add(BorderLayout.CENTER, mainPanel);
        getFrame().getContentPane().add(BorderLayout.CENTER, mainPanel);
        getFrame().getContentPane().add(BorderLayout.NORTH, upPanel);
        getFrame().getContentPane().add(BorderLayout.SOUTH, downPanel);
        getFrame().getContentPane().add(BorderLayout.WEST, createLeftPanel());
        getFrame().repaint();
        repaintAll();
        getFrame().setVisible(true);
    }
    private JPanel createLeftPanel() {
        minus = createPictureButton("minus", Color.white, Pictures.MINUS.getPath(), new Minus(this, minus));

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
        leftPanel.setPreferredSize(new Dimension(LEFT_PANEL_WEIGHT, getFrame().getHeight()));
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
        repaintAll();
    }

    private void paintTextLabels() {
        JButton manCost = createButtonCommand(Command.AVERAGE_OF_MANUFACTURE_COST.getString(), Constants.AVERAGE_OF_MANUFACTURE_COST);
        JButton groupCountingByPrice = createButtonCommand(Command.GROUP_COUNTING_BY_PRICE.getString(), Constants.GROUP_COUNTING_BY_PRICE);
        JButton filter = createFilter();

        upPanel.add(createLanguage(Color.white), BorderLayout.WEST);
        upPanel.add(Box.createRigidArea(new Dimension(ENTER_BETWEEN_UP_LABELS, 0)));
        upPanel.add(filter, BorderLayout.WEST);
        upPanel.add(Box.createRigidArea(new Dimension(ENTER_BETWEEN_UP_LABELS, 0)));
        upPanel.add(groupCountingByPrice, BorderLayout.WEST);
        upPanel.add(Box.createRigidArea(new Dimension(ENTER_BETWEEN_UP_LABELS, 0)));
        upPanel.add(manCost, BorderLayout.WEST);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        jPanel.setBackground(Color.BLACK);

        jPanel.add(nameUser, BorderLayout.EAST);
        upPanel.add(jPanel, BorderLayout.AFTER_LINE_ENDS);
        upPanel.add(Box.createRigidArea(new Dimension(ENTER_BETWEEN_UP_LABELS, 0)));
        plusListener.setResourceBundle(getResourceBundle());

    }

    private void createTable() {
        tableModule = new TableModule(getResourceBundle());
        JTable table = new JTable(tableModule);
        table.setFont(new Font("Safari", Font.BOLD, STANDARD_SIZE_TEXT_LABELS_ON_TABLE));
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tableModule.sort(tableHeader.columnAtPoint(e.getPoint()), table);
                repaint();
            }
        });
        tableHeader.setBackground(blue);
        tableHeader.setFont(new Font("Safari", Font.PLAIN, STANDARD_SIZE_TEXT_LABELS_ON_TABLE));
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
                new ChangeProductTableDialog(this, tableModule, getResourceBundle(), HomeFrame.this) {
                    @Override
                    protected void addOkListener() {
                        getOk().addActionListener(new OkListener() {
                            @Override
                            protected Response createResponse(Product product, Long key) {
                                Response response = new Response(Command.UPDATE_ID.getString());
                                response.setProduct(product);
                                response.setFlagUdateID(true);
                                response.setKeyOrID(key);
                                return response;
                            }

                            @Override
                            protected void sentProduct(Long key, Product product) {
                                getDialog().dispatchEvent(new WindowEvent(getDialog(), WindowEvent.WINDOW_CLOSING));
                                treatmentResponseWithoutFrame(HomeFrame.this::show, createResponse(product, key));
                            }

                            @Override
                            public void actionPerformed(ActionEvent e) {
                                try {
                                    Product product = addProduct();
                                    sentProduct(Long.valueOf(getDescription(localisation(Constants.ID))), product);
                                } catch (VariableException ex) {
                                    exception(ex.getMessage());
                                }
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
        userName.setFont(underLine(new Font("Safari", Font.BOLD, USER_NAME_SIZE)));
        return userName;
    }

    private void repaint() {
        mainPanel.repaint();
    }

    private HashMap<Long, Product> repaintAll() {
        upPanel.removeAll();
        upPanel.revalidate();
        paintTextLabels();
        getFrame().remove(mainPanel);
        createTable();
        upPanel.repaint();
        getFrame().repaint();
        Response response = new Response(Command.SHOW.getString());
        response.setAddToHistory(false);
        Response answer = clientApp.workCycle(response, getResourceBundle());
        closeIfExit(answer);
        if (answer.getProductHashMap() != null) {
            tableModule.addProducts(answer.getProductHashMap());
        }
        repaint();
        collection = answer.getProductHashMap();
        return answer.getProductHashMap();
    }

    private void repaintFilter() {
        repaint();
    }

    public void treatmentResponseWithCommandName(String name, IFunction show) {
        treatmentResponseWithoutFrame(show, new Response(name));

    }

    public void treatmentAnimation(IFunctionResponse newResponse) throws VariableException {
        treatmentResponseWithoutFrame(this::show, newResponse.createResponse());
    }

    void treatmentResponseWithoutFrame(IFunction showResponse, Response response) {
        Response answer = clientApp.workCycle(response, getResourceBundle());
        closeIfExit(answer);
        if (answer instanceof ResponseWithError) {
            exception(answer.getCommand());
        } else {
            showResponse.function(answer.getCommand());
            repaintAll();
        }
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
        jButton = new JButton(localisation(Constants.FILTER));
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
        JButton jButton = new JButton(localisation(command));
        jButton.setFont(new Font("Safari", Font.ITALIC, standardSizeText));
        jButton.setBackground(Color.BLACK);
        jButton.setForeground(Color.WHITE);
        jButton.setFocusPainted(false);
        jButton.setBorderPainted(false);
        jButton.addActionListener((ActionEvent actionEvent) -> createButtonCommandAction(name, command));
        return jButton;
    }

    private void createButtonCommandAction(String name, Constants command) {
        Response response = new Response(name);
        Response answer = clientApp.workCycle(response, getResourceBundle());
        closeIfExit(response);
        if (answer instanceof ResponseWithError) {
            exception(answer.getCommand());
        } else {
            show(localisation(command) + '\n' + answer.getCommand());
        }
    }

    private void showNothing(String message) {
    }

    public TableModule getTableModule() {
        return tableModule;
    }

    public HashMap<Long, Product> getGraphicMap() {
        Response response = new Response(Command.SHOW.getString());
        response.setAddToHistory(false);
        Response answer = clientApp.workCycle(response, getResourceBundle());
        closeIfExit(answer);
        collection = answer.getProductHashMap();
        return collection;
    }

    private void showHelp(String s) {
        show(localisation(Constants.HELP));
    }

    public TablePanel getMainPanel() {
        return mainPanel;
    }

    @Override
    public void close() {
        //show(local(Constants.THANK_YOU));
        getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getFrame().dispatchEvent(new WindowEvent(getFrame(), WindowEvent.WINDOW_CLOSING));
    }

    public void closeIfExit(Response newResponse) {
        if (newResponse.getCommand().equals(Command.CLOSE.getString())) {
            close();
        }
    }

    private class CommandWithoutAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            treatmentResponseWithCommandName(((JButton) e.getSource()).getName(), HomeFrame.this::show);
        }

    }


    private final class Plus extends ChangeProductDialog {
        private Plus(ResourceBundle resourceBundle) {
            super(resourceBundle, HomeFrame.this);
        }

        @Override
        protected void addOkListener() {
            getOk().addActionListener(new OkListener() {

                @Override
                protected Response createResponse(Product product, Long key) {
                    Response response = new Response(Command.INSERT_NULL.getString());
                    response.setProduct(product);
                    response.setKeyOrID(key);
                    return response;
                }
                @Override
                protected void sentProduct(Long key, Product product) {
                    getDialog().dispatchEvent(new WindowEvent(getDialog(), WindowEvent.WINDOW_CLOSING));
                    Response answer = clientApp.workCycle(createResponse(product, key), getResourceBundle());
                    System.out.println(answer);
                    closeIfExit(answer);
                    if (answer instanceof ResponseWithError) {
                        exception(answer.getCommand());
                    } else {
                        show(answer.getCommand());
                        tableModule.addProduct(key, product);
                        repaintAll();
                    }

                }
            });
        }

    }

    private class Script implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle(localisation(Constants.CHOOSE_DIRECTORY));
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                Response response = new Response(Command.EXECUTE_SCRIPT.getString());
                response.setMessage(fileChooser.getSelectedFile().getAbsolutePath());
                treatmentResponseWithoutFrame(HomeFrame.this::showScript, response);
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
