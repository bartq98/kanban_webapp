package com.example.kanban.exceptions;

public class JSONerror {

    String error;

    public String getText() {
        return error;
    }

    public void setText(String text) {
        this.error = text;
    }
}
