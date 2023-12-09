package com.michael.libertybank.controllers;

import com.michael.libertybank.dto.account.AccountDetailsResponseDTO;
import com.michael.libertybank.dto.account.AccountRequestDto;
import com.michael.libertybank.model.Account;
import com.michael.libertybank.repository.AccountRepository;
import com.michael.libertybank.services.AccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
@Slf4j
public class AccountController {
    private final AccountService accountService;
    AccountRepository accountRepository;

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
    @GetMapping("/{accountNumber}")
    public ResponseEntity<Account> getAccountByAccountNumber(@PathVariable String accountNumber) {
        Optional<Account> accountOptional = accountService.getByAccountNumber(accountNumber);

        return accountOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

//    @GetMapping
//    public List<Account> getAccounts(){
//        return accountRepository.findAll();
//    }
    @PostMapping()
    public AccountDetailsResponseDTO openNewAccount(@RequestBody @Valid AccountRequestDto accountRequestDto) {
        return accountService.createAccountNumber(accountRequestDto);
    }

}

