package com.n0dwis.encodebook;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Notebook {

    private static List<NotebookEventListener> listeners = new ArrayList<>();
    private static Notebook _instance;

    public static Notebook getInstance() {
        return _instance;
    }

    public static void addListener(NotebookEventListener newListener) {
        listeners.add(newListener);
    }

    public static Notebook open(File notebookDirectory) {
        _instance = new Notebook(notebookDirectory);
        fireEvent(NotebookEventListener.NOTEBOOK_OPENED);
        return _instance;
    }


    private File _notebookPath;

    public Notebook(File notebookDirectory) {
        _notebookPath = notebookDirectory;
    }


    public void close() {
        _instance = null;
        fireEvent(NotebookEventListener.NOTEBOOK_OPENED);
    }


    public List<Note> getNotesList() {
        ArrayList<Note> notesList = new ArrayList<>();
        _recursiveBuildFilesList(_notebookPath, notesList);
        return notesList;
    }


    private void _recursiveBuildFilesList(File parent, List<Note> notesList) {
        try {
            File[] filesList = parent.listFiles();
//            Arrays.sort(filesList, );
            for (File curFile : filesList) {
                if (curFile.isDirectory()) {
                    _recursiveBuildFilesList(curFile, notesList);
                } else {
                    notesList.add(new Note(curFile));
                }
            }
        } catch (SecurityException e) {
            return;
        }

    }

    private static void fireEvent(int eventType) {
        for (NotebookEventListener l : listeners) {
            l.processNotebookEven(eventType, _instance);
        }
    }
}
