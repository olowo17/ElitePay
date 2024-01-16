package com.michael.libertybank.services;
import com.michael.libertybank.dto.transaction.DepositDto;
import com.michael.libertybank.dto.transaction.TransactionDetails;
import com.michael.libertybank.dto.transaction.TransferDto;
import com.michael.libertybank.dto.transaction.WithdrawalDto;
import com.michael.libertybank.exception.AccountNotFoundException;
import com.michael.libertybank.exception.InsufficientBalanceException;
import com.michael.libertybank.exception.TransactionNotFoundException;
import com.michael.libertybank.model.Account;
import com.michael.libertybank.model.Transaction;
import com.michael.libertybank.repository.AccountRepository;
import com.michael.libertybank.repository.UserRepository;
import com.michael.libertybank.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class TransactionService implements  ITransactionService{
    AccountRepository accountRepository;
    TransactionRepository transactionRepository;
    UserRepository userRepository;

    private Account findAccount (String accountNumber, String msg){
        return accountRepository.findAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(msg));
    }

    private Transaction createDebitTransaction(Account account,BigDecimal amount){
        Transaction newTransaction = new Transaction();
        newTransaction.setTransactionAmount(amount);
        newTransaction.setSenderAccount(account);
        newTransaction.setSenderAccountNumber(account.getAccountNumber());
        newTransaction.setSenderFullName(account.getAccountHolder());
        return  newTransaction;
    }
    private Transaction createCreditTransaction(Account account,BigDecimal amount){
        Transaction newTransaction = new Transaction();
        newTransaction.setTransactionAmount(amount);
        newTransaction.setReceiverAccount(account);
        newTransaction.setReceiverFullName(account.getAccountHolder());
        newTransaction.setReceiverAccountNumber(account.getAccountNumber());
        return  newTransaction;
    }
    private Transaction createTransferTransaction (Account senderAct, Account receiveAct, BigDecimal transferAmt){
        Transaction newTransaction = new Transaction();
        newTransaction.setSenderAccount(senderAct);
        newTransaction.setReceiverAccount(receiveAct);
        newTransaction.setTransactionAmount(transferAmt);
        newTransaction.setSenderFullName(senderAct.getAccountHolder());
        newTransaction.setReceiverFullName(receiveAct.getAccountHolder());
        newTransaction.setReceiverAccountNumber(receiveAct.getAccountNumber());
        newTransaction.setSenderAccountNumber(senderAct.getAccountNumber());
        return newTransaction;
    }

    // Reconcile the account balance for debit
    private void debit(String accountNumber, BigDecimal debitAmount) {
        var accountToBeDebited = findAccount(accountNumber," Debit Account not found" );
        if (accountToBeDebited.getAccountBalance().compareTo(debitAmount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance in the debit account.");
        }

        accountToBeDebited.setAccountBalance(accountToBeDebited.getAccountBalance().subtract(debitAmount));
        Transaction debitTransaction = createDebitTransaction(accountToBeDebited,debitAmount);
        accountToBeDebited.getSentTransactions().add(debitTransaction);
        accountRepository.save(accountToBeDebited);
    }
    // Reconcile the account balance for credit
    private void credit(String accountNumber, BigDecimal creditAmount) {
        var accountToBeCredited = findAccount(accountNumber," Destination Account not found" );
        accountToBeCredited.setAccountBalance(accountToBeCredited.getAccountBalance().add(creditAmount));
        Transaction creditTransaction = createCreditTransaction(accountToBeCredited,creditAmount);
        accountToBeCredited.getReceivedTransactions().add(creditTransaction);
        accountRepository.save(accountToBeCredited);
    }
    // Reconcile the account balance for inter account transfers
    private void transfer(String senderActNumber, String receiverActNumber, BigDecimal amount){
        var senderAct = findAccount(senderActNumber," Sender Account not found" );
        var receiverAct = findAccount(receiverActNumber," Sender Account not found" );
        senderAct.setAccountBalance(senderAct.getAccountBalance().subtract(amount));
        receiverAct.setAccountBalance(receiverAct.getAccountBalance().add(amount));

        Transaction transferTransaction = createTransferTransaction(senderAct,receiverAct,amount);
        senderAct.getSentTransactions().add(transferTransaction);

        accountRepository.save(senderAct);
        accountRepository.save(receiverAct);
    }


    @Override
    public String transferToAnotherAcct(TransferDto transferDto) {
        try {
            this.transfer(transferDto.senderAcctNumber(),transferDto.receiverAcctNumber(),transferDto.transactionAmt());
//            var beneficiary =accountRepository.getCustomerFullName(transferDto.receiverAcctNumber())
            return String.format("Transfer of %s to %s successful", transferDto.transactionAmt(), transferDto.receiverAcctNumber());
        } catch (AccountNotFoundException | InsufficientBalanceException e) {
            return "Transfer failed: " + e.getMessage();
        }
    }

    @Override
    public String withdraw(WithdrawalDto withdrawalDto) {
        try{
            this.debit(withdrawalDto.acctNumber(),withdrawalDto.withdrawalAmt());
            return "Successful";
        }catch (AccountNotFoundException | InsufficientBalanceException e){
            return "withdrawal failed: " + e.getMessage();
        }

    }

    @Override
    public String deposit(DepositDto depositDto) {
       try{
           this.credit(depositDto.acctNumber(),depositDto.depositAmt());
           return "successful";
       }
       catch (AccountNotFoundException e){
           return "unsuccessful: " + e.getMessage();
       }
    }

    @Override
    public TransactionDetails generateTransactionReceipt(String transactionId) {
        TransactionDetails transactionDetails = new TransactionDetails();
        var transaction= transactionRepository.findByTransactionId(transactionId).
                orElseThrow(()->new TransactionNotFoundException(" Transaction Not Found"));
        transactionDetails.setTransactionId(transaction.getTransactionId());
        transactionDetails.setTransactionDate(transaction.getTransactionDate());
        transactionDetails.setTransactionCurrency(transaction.getCurrency());
        transactionDetails.setTransactionAmount(transaction.getTransactionAmount());
        transactionDetails.setSenderFullName(transaction.getSenderFullName());
        transactionDetails.setReceiverFullName(transaction.getReceiverFullName());
        return transactionDetails;

    }

    @Override
    public Page <Transaction> getAllTransaction(int pageNo, int recordCount) {
        Pageable pageable = PageRequest.of(pageNo, recordCount);
        return transactionRepository.findAll(pageable);
    }

    @Override
    public List<Transaction> findByAccountNumber(String accountNumber) {
        return transactionRepository.findBySenderOrReceiverAccountNumber(accountNumber);
    }


    @Override
    public Page<Transaction> getTransactionsByDate(
            String accountNumber,
            LocalDateTime startDate,
            LocalDateTime endDate,
            int page,
            int size) {

        PageRequest pageable = PageRequest.of(page, size);
        return transactionRepository.findBySenderOrReceiverAccountNumberAndDate(
                accountNumber, startDate, endDate, pageable);
    }
}
