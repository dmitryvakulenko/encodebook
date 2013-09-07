package com.n0dwis.encodebook.gui;

import com.n0dwis.encodebook.Notebook;
import com.n0dwis.encodebook.NotebookEventListener;
import com.n0dwis.encodebook.gui.actions.OpenNoteAction;

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
        contentPane.add(splitter, BorderLayout.CENTER);

        setPreferredSize(new Dimension(600, 400));

        setJMenuBar(_createMenu());
    }

    private JMenuBar _createMenu() {
        JMenuBar mainMenu = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        mainMenu.add(fileMenu);

        JMenuItem openNotebook = new JMenuItem();
        openNotebook.setAction(new AbstractAction("Open notebook") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                final JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                Component component = (Component) actionEvent.getSource();
                JFrame frame = (JFrame) SwingUtilities.getRoot(component);
                fc.showOpenDialog(frame);
                Notebook.open(fc.getSelectedFile());
            }
        });

        JMenuItem exit = new JMenuItem();
        exit.setAction(new AbstractAction("Exit") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Frame mainFrame = Frame.getFrames()[0];
                mainFrame.setVisible(false);
                mainFrame.dispose();
            }
        });

        fileMenu.add(openNotebook);
        fileMenu.add(new JMenuItem("Close notebook"));
        fileMenu.addSeparator();
        fileMenu.add(exit);

        return mainMenu;
    }

    @Override
    public void processNotebookEven(int eventType, Notebook notebook) {
        if (eventType == NOTEBOOK_OPENED) {
            notes = createNotebookTree(notebook);
            notes.setPreferredSize(new Dimension(200, 0));
            splitter.setLeftComponent(notes);
            splitter.setRightComponent(new JPanel());
            splitter.resetToPreferredSizes();
        }

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

    private JTree createNotebookTree(final Notebook notebook) {
        JTree tree = new JTree(new NotebookTree(notebook));

        TreeSelectionModel selModel = new DefaultTreeSelectionModel();
        selModel.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setSelectionModel(selModel);

        TreeSelectionListener l = new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent treeSelectionEvent) {
                NotesNode node = (NotesNode)treeSelectionEvent.getNewLeadSelectionPath().getLastPathComponent();
                if (!node.isNote()) {
                    return;
                }

                notebook.openNote(node.getFile());
            }
        };

        tree.addTreeSelectionListener(l);

        return tree;
    }
}
