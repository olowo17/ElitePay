package com.michael.libertybank.exception;

public class EmployeeServiceBusinessException extends  RuntimeException{
    public EmployeeServiceBusinessException(String message) {
        super(message);
    }
}
