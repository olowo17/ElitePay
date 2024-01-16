package com.michael.libertybank.exception;

import com.michael.libertybank.dto.ErrorCode;

import java.io.Serial;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class UnAuthorizedException extends LibertyBankException{

    @Serial
    private static final long serialVersionUID = -321597052401234702L;

    public UnAuthorizedException(String message) {
        super(message, UNAUTHORIZED);
    }

    public UnAuthorizedException(String message, ErrorCode errorCode) {
        super(message, UNAUTHORIZED, errorCode);
    }
}
