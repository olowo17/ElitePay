package com.michael.libertybank.controllers;

import com.michael.libertybank.dto.account.AccountRequestDto;
import com.michael.libertybank.model.Account;
import com.michael.libertybank.repository.AccountRepository;
import com.michael.libertybank.services.AccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
@Slf4j
public class AccountController {
    private final AccountService accountService;
    AccountRepository accountRepository;

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @PostMapping()
    public String openNewAccount(@RequestBody @Valid AccountRequestDto accountRequestDto) {
        return accountService.createAccountNumber(accountRequestDto);
    }

//    @GetMapping("/joined-users")
//    public List<Object[]> getJoinedAccountsAndUsers() {
//        // Execute the SQL query using JpaRepository
//        List<Object[]> result = accountRepository.findAccountsAndUsers();
//
//        // Do something with the result, e.g., return it
//        return result;
//    }
}

