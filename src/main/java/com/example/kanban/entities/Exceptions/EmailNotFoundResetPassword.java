package com.example.kanban.entities.Exceptions;

public class EmailNotFoundResetPassword extends Exception{
    public EmailNotFoundResetPassword(String errorMessage){
        super(errorMessage);
    }
}
