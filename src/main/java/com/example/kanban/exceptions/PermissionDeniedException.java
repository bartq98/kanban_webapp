package com.example.kanban.exceptions;

public class PermissionDeniedException extends Exception{
    public PermissionDeniedException(String errorMessage){
        super(errorMessage);
    }
}
