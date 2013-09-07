package com.n0dwis.encodebook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.util.ArrayList;
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


    private File _notebookDirectory;
    private Note _currentNote;

    public Notebook(File notebookDirectory) {
        _notebookDirectory = notebookDirectory;
    }


    public File getNotebookDirectory() {
        return _notebookDirectory;
    }

    public void openNote(File file) {
        _currentNote = new Note(file);
        fireEvent(NotebookEventListener.FILE_OPENED);
    }

    public Note getCurrentNote() {
        return _currentNote;
    }

    public void close() {
        _instance = null;
        fireEvent(NotebookEventListener.NOTEBOOK_OPENED);
    }

    private static void fireEvent(int eventType) {
        for (NotebookEventListener l : listeners) {
            l.processNotebookEven(eventType, _instance);
        }
    }
}
