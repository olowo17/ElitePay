package com.michael.libertybank.services;

import com.michael.libertybank.dto.transaction.DepositDto;
import com.michael.libertybank.dto.transaction.TransactionDetails;
import com.michael.libertybank.dto.transaction.TransferDto;
import com.michael.libertybank.dto.transaction.WithdrawalDto;
import com.michael.libertybank.model.Transaction;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface ITransactionService {
    public String transferToAnotherAcct(TransferDto transferDto);
    public String withdraw(WithdrawalDto withdrawalDto);
    public String deposit(DepositDto depositDto);
    public TransactionDetails generateTransactionReceipt(String transactionId);
    public Page <Transaction> getAllTransaction(int pageNo, int recordCount);
    public List<Transaction> findByAccountNumber(String accountNumber);

    Page<Transaction> getTransactionsByDate(
            String accountNumber,
            LocalDateTime startDate,
            LocalDateTime endDate,
            int page,
            int size);
}
