package com.michael.libertybank.exception;

public class AccountServiceBusinessException extends  RuntimeException{
    public AccountServiceBusinessException(String message) {
        super(message);
    }
}
