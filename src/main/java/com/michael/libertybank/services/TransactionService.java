package com.michael.libertybank.services;
import com.michael.libertybank.dto.transaction.DepositDto;
import com.michael.libertybank.dto.transaction.TransferDto;
import com.michael.libertybank.dto.transaction.WithdrawalDto;
import com.michael.libertybank.exception.AccountNotFoundException;
import com.michael.libertybank.exception.InsufficientBalanceException;
import com.michael.libertybank.exception.TransactionNotFoundException;
import com.michael.libertybank.model.Account;
import com.michael.libertybank.model.Transaction;
import com.michael.libertybank.repository.AccountRepository;
import com.michael.libertybank.repository.CustomerRepository;
import com.michael.libertybank.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class TransactionService implements  ITransactionService{
    AccountRepository accountRepository;
    TransactionRepository transactionRepository;
    CustomerRepository customerRepository;

    private Account findAccount (String accountNumber, String msg){
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(msg));
    }


    private Transaction createDebitTransaction(Account account,BigDecimal amount){
        Transaction newTransaction = new Transaction();
        newTransaction.setTransactionAmount(amount);
        newTransaction.setSenderAccount(account);
        return  newTransaction;
    }
    private Transaction createCreditTransaction(Account account,BigDecimal amount){
        Transaction newTransaction = new Transaction();
        newTransaction.setTransactionAmount(amount);
        newTransaction.setReceiverAccount(account);
        return  newTransaction;
    }
    private Transaction createTransferTransaction (Account senderAct, Account receiveAct, BigDecimal transferAmt){
        Transaction newTransaction = new Transaction();
        newTransaction.setSenderAccount(senderAct);
        newTransaction.setReceiverAccount(receiveAct);
        newTransaction.setTransactionAmount(transferAmt);
        return newTransaction;
    }
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
    private void credit(String accountNumber, BigDecimal creditAmount) {
        var accountToBeCredited = findAccount(accountNumber," Destination Account not found" );
        accountToBeCredited.setAccountBalance(accountToBeCredited.getAccountBalance().add(creditAmount));
        Transaction creditTransaction = createCreditTransaction(accountToBeCredited,creditAmount);
        accountToBeCredited.getReceivedTransactions().add(creditTransaction);
        accountRepository.save(accountToBeCredited);
    }

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
//            customerRepository.getCustomerFullName()
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
    public Transaction generateTransactionReceipt(String transactionId) {
        return transactionRepository.findByTransactionId(transactionId).
                orElseThrow(()->new TransactionNotFoundException(" Transaction Not Found"));
    }

    @Override
    public List<Transaction> getAllTransaction() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> findByAccountNumber(String accountNumber) {
        return transactionRepository.findBySenderOrReceiverAccountNumber(accountNumber);
    }
}
