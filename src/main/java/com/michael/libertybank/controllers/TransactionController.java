package com.michael.libertybank.controllers;

import com.michael.libertybank.dto.AccountStatementDto;
import com.michael.libertybank.dto.transaction.DepositDto;
import com.michael.libertybank.dto.transaction.TransactionDetails;
import com.michael.libertybank.dto.transaction.TransferDto;
import com.michael.libertybank.dto.transaction.WithdrawalDto;
import com.michael.libertybank.model.Transaction;
import com.michael.libertybank.repository.UserRepository;
import com.michael.libertybank.services.ReceiptService;
import com.michael.libertybank.services.TransactionService;
import com.michael.libertybank.util.PdfGenerator;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/transactions")
@AllArgsConstructor
@Slf4j
public class TransactionController {
    TransactionService transactionService;
    UserRepository userRepository;
    ReceiptService receiptService;
 @PostMapping("/deposit")
 public String deposit(@RequestBody DepositDto depositDto){
     return transactionService.deposit(depositDto);
 }
 @PostMapping("/withdraw")
    public String withdraw(@RequestBody WithdrawalDto withdrawalDto){
     return transactionService.withdraw(withdrawalDto);
 }
 @PostMapping("/transfer")
    public String transferToAccount (@RequestBody TransferDto transferDto){
     return transactionService.transferToAnotherAcct(transferDto);
    }
    @GetMapping("/records")
    public Page<Transaction> getTransactions (
            @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
            @RequestParam(value = "recordCount", defaultValue = "10") int recordCount){
     return transactionService.getAllTransaction(pageNo,recordCount);
    }
    @GetMapping("/receipt/{transactionId}")
    public void getTransactionDetails(@PathVariable String transactionId, HttpServletResponse response) {
        TransactionDetails transactionDetails = transactionService.generateTransactionReceipt(transactionId);
        PdfGenerator.generateTransactionPdf(transactionDetails, response);
    }
    @GetMapping("/accountStatement")
    public List<Transaction> getAccountStatement (@RequestParam String accountNumber){
     return transactionService.findByAccountNumber(accountNumber);
    }

    @GetMapping("/accountStatement/{accountId}")
    public List<Transaction> getAccountStatementById (@PathVariable Long accountId){
        return transactionService.findByAccountId(accountId);
    }

    @PostMapping("/accountStatement/date/{page}/{size}")
    public Page<Transaction> getTransactionsByDate(
            @RequestBody AccountStatementDto accountStatementDto,
            @PathVariable int page, @PathVariable int size) {
        return transactionService.getTransactionsByDate(
                accountStatementDto.getAccountNumber(),
                accountStatementDto.getStartDate(),
                accountStatementDto.getEndDate(),
                page, size);
    }


}
