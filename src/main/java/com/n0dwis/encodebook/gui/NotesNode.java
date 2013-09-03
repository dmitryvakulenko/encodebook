package com.n0dwis.encodebook.gui;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class NotesNode {

    private File _file;

    private ArrayList<NotesNode> _childNotesList = new ArrayList<>();

    public NotesNode(File file) {
        _file = file;
    }

    @Override
    public String toString() {
        return _file.getName();
    }


    public File getFile() {
        return _file;
    }

    public boolean isNote() {
        return _file.isFile();
    }

    public NotesNode getChildNodeByIndex(int index) {
        return _childNotesList.get(index);
    }

    public int getChildNotesCount() {
        if (isNote()) {
            throw new RuntimeException("Note " + _file.getName() + " haven't children.");
        }

        return _childNotesList.size();
    }

    public int getIndexOfChild(NotesNode child) {
        return _childNotesList.indexOf(child);
    }


    public void updateChildNotesList() {
        if (isNote()) {
            return;
        }

        File[] childFiles = _file.listFiles();
        Arrays.sort(childFiles, new Comparator<File>() {
            @Override
            public int compare(File file, File file2) {
                boolean isDir1 = file.isDirectory();
                boolean isDir2 = file2.isDirectory();
                if (isDir1 && !isDir2) {
                    return -1;
                }

                if (!isDir1 && isDir2) {
                    return 1;
                }

                return file.getName().compareTo(file2.getName());
            }
        });

        _childNotesList.clear();
        for (File f : childFiles) {
            NotesNode n = new NotesNode(f);
            n.updateChildNotesList();
            _childNotesList.add(n);
        }
    }
}
