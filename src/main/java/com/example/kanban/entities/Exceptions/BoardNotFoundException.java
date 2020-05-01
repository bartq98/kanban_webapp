package com.example.kanban.entities.Exceptions;

public class BoardNotFoundException extends Exception{
    public BoardNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
