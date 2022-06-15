package test.laba.client.frontEnd.frames;

import javax.swing.JFrame;
import test.laba.common.exception.VariableException;
import test.laba.common.responses.Response;

import java.awt.event.ActionListener;
import java.util.ResourceBundle;

public abstract class OkListener extends AbstractFrame implements ActionListener {
    private final String command;

    public OkListener(String command, JFrame jFrame, ResourceBundle resourceBundle) {
        super(jFrame, resourceBundle);
        this.command = command;
    }
    public abstract Response createResponse() throws VariableException;

    @Override
    public void repaintForLanguage() {
    }

    public String getCommand() {
        return command;
    }
}
