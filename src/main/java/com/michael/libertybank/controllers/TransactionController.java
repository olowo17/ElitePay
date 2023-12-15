package com.michael.libertybank.controllers;

import com.michael.libertybank.dto.FullNameDto;
import com.michael.libertybank.dto.transaction.DepositDto;
import com.michael.libertybank.dto.transaction.TransferDto;
import com.michael.libertybank.dto.transaction.WithdrawalDto;
import com.michael.libertybank.model.Transaction;
import com.michael.libertybank.repository.CustomerRepository;
import com.michael.libertybank.services.TransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/transactions")
@AllArgsConstructor
@Slf4j
public class TransactionController {
    TransactionService transactionService;
    CustomerRepository customerRepository;
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
    @GetMapping("/{pageNo}/{recordCount}")
    public Page<Transaction> getTransactions (@PathVariable int pageNo, @PathVariable int recordCount){
     return transactionService.getAllTransaction(pageNo,recordCount);
    }
    @GetMapping("/receipt/{transactionId}")
    public Transaction getTransactionReceipt(@PathVariable String transactionId){
     return transactionService.generateTransactionReceipt(transactionId);
    }
    @GetMapping("/{accountStatement}")
    public List<Transaction> getAccountStatement (@PathVariable String accountStatement, @RequestParam String accountNumber){
     return transactionService.findByAccountNumber(accountNumber);
    }

    @GetMapping("/accountname/{customerId}")
    public FullNameDto customerFullName (@PathVariable Long customerId) {
        return customerRepository.getCustomerFullName(customerId);
    }

}
