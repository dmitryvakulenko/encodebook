package com.n0dwis.encodebook;

import com.n0dwis.encodebook.gui.MainFrame;
import com.n0dwis.encodebook.gui.NotebookPresenter;

import javax.swing.*;

public class Encodebook {

    private MainFrame _mainFrame;

    public static NotebookPresenter presenter = null;

    Encodebook(final String[] args) {
        _mainFrame = new MainFrame();
        Notebook.addListener(_mainFrame);
    }

    public void show() {
        _mainFrame.pack();
        _mainFrame.setVisible(true);
    }

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Encodebook(args).show();
            }
        });
    }
}
