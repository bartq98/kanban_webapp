package com.example.kanban.entities.Exceptions;

public class PermissionDeniedException extends Exception{
    public PermissionDeniedException(String errorMessage){
        super(errorMessage);
    }
}
