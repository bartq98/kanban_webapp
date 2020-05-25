package com.example.kanban.services;

import com.example.kanban.exceptions.ExceptionsFactory;
import com.example.kanban.exceptions.exceptions.*;
import org.springframework.stereotype.Service;

@Service
public class ExceptionsFacade {

    public BoardNotFoundException throwBoardNotFoundException(String message){
        return new ExceptionsFactory().makeBoardNotFoundExc(message);
    }
    public EmailNotFoundResetPassword throwEmailNotFoundResetPassword(String message){
        return new ExceptionsFactory().makeEmailNotFoundResetPassword(message);
    }
    public JSONException throwJSONException(String message){
        return new ExceptionsFactory().makeJsonException(message);
    }
    public ObjectNotFoundException throwObjectNotFoundException(String message){
        return new ExceptionsFactory().makeObjectNotFoundException(message);
    }
    public PermissionDeniedException throwPermissionDeniedException(String message){
        return new ExceptionsFactory().makePermissionDeniedException(message);
    }
}
