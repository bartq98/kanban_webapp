package com.example.kanban.exceptions;

public class EmailNotFoundResetPassword extends Exception{
    public EmailNotFoundResetPassword(String errorMessage){
        super(errorMessage);
    }
}
