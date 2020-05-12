package com.example.kanban.exceptions.exceptions;

public class JSONerror {

    String error;

    public String getText() {
        return error;
    }

    public void setText(String text) {
        this.error = text;
    }
}
