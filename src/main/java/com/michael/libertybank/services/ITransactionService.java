package com.michael.libertybank.services;

import com.michael.libertybank.dto.transaction.DepositDto;
import com.michael.libertybank.dto.transaction.TransferDto;
import com.michael.libertybank.dto.transaction.WithdrawalDto;
import com.michael.libertybank.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface ITransactionService {
    public String transferToAnotherAcct(TransferDto transferDto);
    public String withdraw(WithdrawalDto withdrawalDto);
    public String deposit(DepositDto depositDto);
    public Transaction generateTransactionReceipt(String transactionId);
    public List<Transaction> getAllTransaction();
    public List<Transaction> findByAccountNumber(String accountNumber);
}
