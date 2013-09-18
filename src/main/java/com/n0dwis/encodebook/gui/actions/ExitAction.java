package com.n0dwis.encodebook.gui.actions;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ExitAction extends AbstractAction {

    public ExitAction() {
        super("Exit");
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Frame mainFrame = Frame.getFrames()[0];
        mainFrame.setVisible(false);
        mainFrame.dispose();
    }
}
