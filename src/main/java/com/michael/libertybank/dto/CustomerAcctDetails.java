package com.michael.libertybank.dto;

import com.michael.libertybank.model.AccountType;

import java.math.BigDecimal;

public record CustomerAcctDetails(String accountNumber, AccountType accountType, BigDecimal accountBalance, Long id) {
}
