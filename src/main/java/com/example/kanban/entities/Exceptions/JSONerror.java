package com.example.kanban.entities.Exceptions;

public class JSONerror {

    String error;

    public String getText() {
        return error;
    }

    public void setText(String text) {
        this.error = text;
    }
}
