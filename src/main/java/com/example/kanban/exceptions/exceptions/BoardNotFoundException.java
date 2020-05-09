package com.example.kanban.exceptions.exceptions;

public class BoardNotFoundException extends Exception{
    public BoardNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
