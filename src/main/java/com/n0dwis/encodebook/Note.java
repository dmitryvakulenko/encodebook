package com.n0dwis.encodebook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Note {

    private File _noteFile;

    public Note(File noteFile) {
        _noteFile = noteFile;
    }

    public String getName() {
        return _noteFile.getName();
    }

    public boolean isContainer() {
        return _noteFile.isDirectory();
    }

    // TODO it will be better to throw something like NotException here
    public BufferedReader getReader() throws FileNotFoundException {
        return new BufferedReader(new FileReader(_noteFile));
    }
}
