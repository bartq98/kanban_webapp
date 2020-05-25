package com.example.kanban.exceptions.exceptions;

public class EmailNotFoundResetPassword extends Exception{
    public EmailNotFoundResetPassword(String errorMessage){
        super(errorMessage);
    }
}
