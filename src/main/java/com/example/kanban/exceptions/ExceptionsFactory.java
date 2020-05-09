package com.example.kanban.exceptions;

import com.example.kanban.exceptions.exceptions.*;

public class ExceptionsFactory {
    public ExceptionsFactory(){}
    public BoardNotFoundException makeBoardNotFoundExc(String message){
        return new BoardNotFoundException(message);
    }
    public EmailNotFoundResetPassword makeEmailNotFoundResetPassword(String message){
        return new EmailNotFoundResetPassword(message);
    }
    public JSONException makeJsonException(String message){
        return new JSONException(message);
    }
    public ObjectNotFoundException makeObjectNotFoundException(String message){
        return new ObjectNotFoundException(message);
    }
    public PermissionDeniedException makePermissionDeniedException(String message){
        return new PermissionDeniedException(message);
    }
}
