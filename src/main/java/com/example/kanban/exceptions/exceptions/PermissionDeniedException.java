package com.example.kanban.exceptions.exceptions;

public class PermissionDeniedException extends Exception{
    public PermissionDeniedException(String errorMessage){
        super(errorMessage);
    }
}
