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
    private File _currentFile;
    private String _currentContent;

    public Notebook(File notebookDirectory) {
        _notebookDirectory = notebookDirectory;
    }


    public File getNotebookDirectory() {
        return _notebookDirectory;
    }

    public void openNote(File file) {
        _currentFile = file;

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            _currentContent = "";
            while (br.ready()) {
                _currentContent += br.readLine();
            }
            fireEvent(NotebookEventListener.FILE_OPENED);
        } catch (Exception e) {
            _currentFile = null;
            _currentContent = null;
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public String getCurrentContent() {
        return _currentContent;
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
