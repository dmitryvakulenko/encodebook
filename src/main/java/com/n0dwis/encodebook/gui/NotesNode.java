package com.n0dwis.encodebook.gui;


import com.n0dwis.encodebook.Note;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class NotesNode {

    private Note _note;

    private ArrayList<NotesNode> _childNotesList = new ArrayList<>();

    public NotesNode(Note note) {
        _note = note;
    }

    @Override
    public String toString() {
        return _note.getName();
    }


    public Note getNote() {
        return _note;
    }

    public boolean isNote() {
        return _note.isContainer();
    }

    public NotesNode getChildNodeByIndex(int index) {
        return _childNotesList.get(index);
    }

    public int getChildNotesCount() {
        if (isNote()) {
            throw new RuntimeException("Note " + _note.getName() + " haven't children.");
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

        File[] childFiles = _note.listFiles();
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
