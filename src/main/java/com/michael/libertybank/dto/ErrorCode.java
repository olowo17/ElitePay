package com.michael.libertybank.dto;


public enum ErrorCode {

    EMAIL_ALREADY_IN_USE,

    INVALID_CREDENTIALS,

    INVALID_TOKEN,

    MISSING_TOKEN,

    CONSTRAINT_VIOLATION,

    INSUFFICIENT_BALANCE,
    WALLET_DOES_NOT_EXIST,
    INVALID_INPUT_PROVIDED,

    CURRENCY_CODE_MISMATCH,

    TOKEN_EXPIRED
}