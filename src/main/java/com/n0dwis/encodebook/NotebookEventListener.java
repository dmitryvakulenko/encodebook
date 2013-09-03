package com.n0dwis.encodebook;


public interface NotebookEventListener {

    public static final int NOTEBOOK_OPENED = 1;
    public static final int NOTEBOOK_CLOSED = 2;
    public static final int FILE_OPENED = 3;

    public void processNotebookEven(int eventType, Notebook notebook);
}
