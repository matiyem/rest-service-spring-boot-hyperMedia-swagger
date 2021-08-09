package com.example.demo.exception;
/*
*Create by User on 7/27/2021
*/

public class EmployeeNotFoundException extends RuntimeException {
     public EmployeeNotFoundException(Long id){
        super("could not find employee " + id);
    }
}
