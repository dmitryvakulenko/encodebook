package com.n0dwis.encodebook.gui.actions;


import com.n0dwis.encodebook.Note;

import javax.swing.*;
import javax.swing.text.DefaultStyledDocument;
import java.io.BufferedReader;

public class OpenNoteAction {

    private JTextArea _editor;

    public OpenNoteAction(JTextArea editor) {
        this._editor = editor;
    }

    public void doAction(Note note) {
        try {
            _readNoteContent(note);
        } catch (Exception e) {
            throw new RuntimeException("Error reading note. " + e.getMessage(), e);
        }

    }

    private void _readNoteContent(Note note) throws Exception {
        BufferedReader reader = note.getReader();
        DefaultStyledDocument doc = (DefaultStyledDocument)_editor.getDocument();
        int position = 0;
        while (reader.ready()) {
            String s = reader.readLine();
            doc.insertString(position, s + "\n", null);
            position += s.length() + 1;
        }
    }
}
