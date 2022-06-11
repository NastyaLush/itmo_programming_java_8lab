package test.laba.client.frontEnd;

import test.laba.client.frontEnd.Frames.AbstractFrame;
import test.laba.common.exception.VariableException;
import test.laba.common.responses.Response;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

public abstract class OkListener extends AbstractFrame implements ActionListener {
    protected JComponent textKey;
    protected final String command;

    public abstract Response createResponse() throws VariableException;

    public OkListener(String command, JComponent textKey, JFrame jFrame, ResourceBundle resourceBundle) {
        super(jFrame, resourceBundle);
        this.textKey = textKey;
        this.command = command;
    }


}