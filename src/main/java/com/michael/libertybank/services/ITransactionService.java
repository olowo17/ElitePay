package com.michael.libertybank.services;

import com.michael.libertybank.dto.transaction.DepositDto;
import com.michael.libertybank.dto.transaction.TransferDto;
import com.michael.libertybank.dto.transaction.WithdrawalDto;
import com.michael.libertybank.model.Transaction;
import org.springframework.data.domain.Page;
import java.util.List;

public interface ITransactionService {
    public String transferToAnotherAcct(TransferDto transferDto);
    public String withdraw(WithdrawalDto withdrawalDto);
    public String deposit(DepositDto depositDto);
    public Transaction generateTransactionReceipt(String transactionId);
    public Page <Transaction> getAllTransaction(int pageNo, int recordCount);
    public List<Transaction> findByAccountNumber(String accountNumber);
}
