package com.example.kanban.handlers;

import com.example.kanban.exceptions.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@ControllerAdvice
public class GlobalExceptionHandling {

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ObjectNotFoundException.class,
            BoardNotFoundException.class,
            PermissionDeniedException.class})
    public ModelAndView ObjectError(Exception ex){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("errormessage",ex.getMessage());
        return modelAndView;
    }

    @ResponseBody
    @ExceptionHandler(value = JSONException.class)
    public JSONerror ExceptionsInJSON(Exception ex){
        JSONerror jsonerror=new JSONerror();
        jsonerror.setText(ex.getMessage());
        return jsonerror;
    }

    @ExceptionHandler(value = EmailNotFoundResetPassword.class)
    public RedirectView EmailError(Exception ex, RedirectAttributes attributes){

        attributes.addFlashAttribute("send_error",ex.getMessage());



        return new RedirectView("forgot-password");
    }

}
