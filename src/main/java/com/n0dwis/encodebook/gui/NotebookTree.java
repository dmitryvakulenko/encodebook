package com.n0dwis.encodebook.gui;

import com.n0dwis.encodebook.Notebook;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;


public class NotebookTree implements TreeModel {

    private NotesNode _root;

    public NotebookTree(Notebook notebook) {
        _root = new NotesNode(notebook.getNotebookDirectory());
        _root.updateChildNotesList();
    }

    @Override
    public Object getRoot() {
        return _root;
    }

    @Override
    public Object getChild(Object o, int i) {
        NotesNode parent = (NotesNode)o;
        return parent.getChildNodeByIndex(i);
    }

    @Override
    public int getChildCount(Object o) {
        NotesNode parent = (NotesNode)o;
        return parent.getChildNotesCount();
    }

    @Override
    public boolean isLeaf(Object o) {
        return ((NotesNode)o).isNote();
    }

    @Override
    public void valueForPathChanged(TreePath treePath, Object o) {
        throw new RuntimeException("Change isn't allowed.");
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        NotesNode parentNode = (NotesNode)parent;
        NotesNode childNode = (NotesNode)child;
        return parentNode.getIndexOfChild(childNode);
    }

    @Override
    public void addTreeModelListener(TreeModelListener treeModelListener) {

    }

    @Override
    public void removeTreeModelListener(TreeModelListener treeModelListener) {

    }

}
