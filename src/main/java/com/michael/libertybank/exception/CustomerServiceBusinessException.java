package com.michael.libertybank.exception;

public class CustomerServiceBusinessException extends  RuntimeException{
    public CustomerServiceBusinessException(String message) {
        super(message);
    }
}
