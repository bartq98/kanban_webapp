package com.example.kanban.entities.Exceptions;

public class ObjectNotFoundException extends Exception{
    public ObjectNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
