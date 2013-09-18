package com.n0dwis.encodebook;

import java.io.File;

public class NoteContainer {

    private File _noteFile;

    public NoteContainer(File noteFile) {
        _noteFile = noteFile;
    }

    public String getName() {
        return _noteFile.getName();
    }

    public boolean isContainer() {
        return true;
    }
}
