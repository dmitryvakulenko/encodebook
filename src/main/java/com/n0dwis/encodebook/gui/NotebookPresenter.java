package com.n0dwis.encodebook.gui;

import com.n0dwis.encodebook.Encodebook;
import com.n0dwis.encodebook.Note;
import com.n0dwis.encodebook.Notebook;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeSelectionModel;
import java.io.File;

/**
 * Логика по взаимодействию модели и представления
 */
public class NotebookPresenter {

    /**
     * Текущий блокнот
     */
    private Notebook _notebook = null;

    /**
     * Вьюшка
     */
    private JSplitPane _splitter;

    public NotebookPresenter(JSplitPane splitter) {
        _splitter = splitter;
    }


    public void openNotebook(File notebookPath) {
        _notebook = Notebook.open(notebookPath);
        JTree notes = createNotebookTree(_notebook);
        _splitter.setLeftComponent(notes);
        _splitter.setRightComponent(new JPanel());
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

                Encodebook.presenter.openNote(node.getNote());
            }
        };

        tree.addTreeSelectionListener(l);

        return tree;
    }

    public void openNote(Note note) {

    }
}
