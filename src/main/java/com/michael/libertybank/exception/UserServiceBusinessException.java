package com.michael.libertybank.exception;

public class UserServiceBusinessException extends  RuntimeException{
    public UserServiceBusinessException(String message) {
        super(message);
    }
}
