package com.n0dwis.encodebook.gui;

import com.n0dwis.encodebook.Encodebook;
import com.n0dwis.encodebook.Notebook;
import com.n0dwis.encodebook.NotebookEventListener;
import com.n0dwis.encodebook.gui.actions.ExitAction;
import com.n0dwis.encodebook.gui.actions.OpenNoteAction;
import com.n0dwis.encodebook.gui.actions.OpenNoteBookAction;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame implements NotebookEventListener {

    JSplitPane splitter = null;
    JTree notes = null;
    JEditorPane editor = null;

    public MainFrame() throws HeadlessException {
        super("EncodeBook");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        splitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        Encodebook.presenter = new NotebookPresenter(splitter);
        contentPane.add(splitter, BorderLayout.CENTER);

        setPreferredSize(new Dimension(600, 400));

        setJMenuBar(_createMenu());
    }

    private JMenuBar _createMenu() {
        JMenuBar mainMenu = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        mainMenu.add(fileMenu);

        JMenuItem openNotebook = new JMenuItem();
        openNotebook.setAction(new OpenNoteBookAction());

        JMenuItem exit = new JMenuItem();
        exit.setAction(new ExitAction());

        fileMenu.add(openNotebook);
        fileMenu.add(new JMenuItem("Close notebook"));
        fileMenu.addSeparator();
        fileMenu.add(exit);

        return mainMenu;
    }

    @Override
    public void processNotebookEven(int eventType, Notebook notebook) {
        if (eventType == FILE_OPENED) {
            int location = splitter.getDividerLocation();
            splitter.remove(2);
            JTextArea editor = new JTextArea();
            splitter.setRightComponent(editor);
            DefaultStyledDocument doc = new DefaultStyledDocument();
            doc.putProperty(DefaultEditorKit.EndOfLineStringProperty, "\n");
            editor.setDocument(doc);
            OpenNoteAction action = new OpenNoteAction(editor);
            action.doAction(notebook.getCurrentNote());
            splitter.setDividerLocation(location);
            splitter.resetToPreferredSizes();
        }

        if (eventType == NOTEBOOK_CLOSED) {
            splitter.remove(1);
            splitter.remove(2);
            revalidate();
        }
    }
}
