package com.n0dwis.encodebook.gui.actions;

import com.n0dwis.encodebook.Encodebook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class OpenNoteBookAction extends AbstractAction {

    public OpenNoteBookAction() {
        super("Open notebook");
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        final JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        Component component = (Component) actionEvent.getSource();
        JFrame frame = (JFrame) SwingUtilities.getRoot(component);
        fc.showOpenDialog(frame);
        Encodebook.presenter.openNotebook(fc.getSelectedFile());
    }
}
