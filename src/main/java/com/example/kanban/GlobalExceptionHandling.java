package com.example.kanban;

import com.example.kanban.entities.Exceptions.EmailNotFoundResetPassword;
import com.example.kanban.entities.Exceptions.JSONException;
import com.example.kanban.entities.Exceptions.JSONerror;
import com.example.kanban.entities.Exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
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
    @ExceptionHandler(value = ObjectNotFoundException.class)
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
