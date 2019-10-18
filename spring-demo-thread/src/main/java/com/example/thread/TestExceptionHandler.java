package com.example.thread;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletResponse;

/**
 * @Created with IntelliJ IDEA.
 * @author: weijie
 * @Date: 2019/10/15
 * @Time: 11:19
 * @Description: No Description
 */
@ControllerAdvice
@ResponseBody
public class TestExceptionHandler {

    @ExceptionHandler(value = NullPointerException.class)
    @ResponseStatus(HttpStatus.OK)
    public String NullPointerException(HttpServletResponse response, Exception exception) {
        return "this is a null message";
    }

    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.OK)
    public String NoHandlerFoundException(HttpServletResponse response, Exception exception) {
        return " 404 page" ;
    }


    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public String Exception(HttpServletResponse response, Exception exception) {
        return exception.getMessage();
    }


}
