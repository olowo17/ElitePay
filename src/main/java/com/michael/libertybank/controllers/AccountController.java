package com.michael.libertybank.controllers;
import com.michael.libertybank.dto.CustomerAcctDetails;
import com.michael.libertybank.dto.account.AccountDetailsResponseDTO;
import com.michael.libertybank.dto.account.AccountRequestDto;
import com.michael.libertybank.model.Account;
import com.michael.libertybank.model.AccountType;
import com.michael.libertybank.repository.AccountRepository;
import com.michael.libertybank.services.AccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
@Slf4j
@Data
public class AccountController {
    private final AccountService accountService;
    private final AccountRepository accountRepository;
    @GetMapping("/accountName")
    public ResponseEntity<?> getAccountByCustomerName(@RequestParam String accountHolder){
        var acctDetails = accountRepository.getCustomerAcctDetails(accountHolder);
        Map<String, List<CustomerAcctDetails>> customerDetails = new HashMap<>();
        customerDetails.put(accountHolder,acctDetails);
        return new ResponseEntity<>(customerDetails, HttpStatus.OK);
    }

    @GetMapping("/records")
    public Page<AccountDetailsResponseDTO> getAllAccounts(@RequestParam int pageNo, @RequestParam int recordSize) {
        return accountService.getAllAccounts(pageNo,recordSize);
    }
    @GetMapping("/accountNumber")
    public ResponseEntity<Account> getAccountByAccountNumber(@RequestParam String accountNumber) {
        Optional<Account> accountOptional = accountService.getByAccountNumber(accountNumber);
        return accountOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/accountType")
    public  Page<Account> findByAccountType(@RequestParam AccountType accountType, Pageable pageable){
        return accountService.findByAccountType(accountType, pageable);
    }


    @PostMapping()
    public String createCustomerAccount (@RequestBody @Valid AccountRequestDto accountRequestDto){
       return accountService.createAccountNumber(accountRequestDto);
    }


}

