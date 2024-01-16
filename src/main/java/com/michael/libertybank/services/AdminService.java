package com.michael.libertybank.services;

import com.michael.libertybank.exception.AccountNotFoundException;
import com.michael.libertybank.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminService implements IAdminService{
    AccountRepository accountRepository;
    @Override
    public String deleteAccount(String accountNumber) {
       var account = accountRepository.findAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        accountRepository.delete(account);
        return "Account no: " + accountNumber + " has been deleted successfully";
    }

    @Override
    public String deleteAllAccounts() {
         accountRepository.deleteAll();
         return "All accounts have been deleted successfully";
    }
}
