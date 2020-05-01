package com.example.kanban.exceptions;

public class ObjectNotFoundException extends Exception{
    public ObjectNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
