package com.example.demo.exception;
/*
*Create by User on 7/27/2021
*/

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class EmployeeNotFoundAdvice {
    //وقتی کلاس EmployeeNotFoundException ترو(throw) شد این کلاس تجرا میشود

    @ResponseBody
    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFound(EmployeeNotFoundException ex){
        return ex.getMessage();
    }
}
