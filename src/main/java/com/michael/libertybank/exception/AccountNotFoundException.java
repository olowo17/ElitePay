package com.michael.libertybank.exception;

public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException(String message) {

        super(message);
    }
}
