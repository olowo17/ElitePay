package com.michael.libertybank.exception;

public class TransactionNotFoundException extends RuntimeException{
    public TransactionNotFoundException(String message) {

        super(message);
    }
}
