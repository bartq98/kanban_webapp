package com.example.kanban.exceptions;

public class BoardNotFoundException extends Exception{
    public BoardNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
