package com.michael.libertybank.services;

import com.michael.libertybank.dto.account.AccountRequestDto;
import com.michael.libertybank.exception.UserNotFoundException;
import com.michael.libertybank.model.Account;
import com.michael.libertybank.repository.AccountRepository;
import com.michael.libertybank.repository.EmployeeRepository;
import com.michael.libertybank.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class AccountService implements IAccountService{
    private EmployeeRepository employeeRepository;
    private CustomerRepository userRepository;
    private final AccountRepository accountRepository;
    @Override
    public String createAccountNumber(AccountRequestDto accountRequestDto) {
        Account newAccount = new Account();

        var userId = accountRequestDto.userId();
        var getUserFromUserRepository = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Customer not found"));
        newAccount.setCustomer(getUserFromUserRepository);

        newAccount.setAccountType(accountRequestDto.accountType());
//        newAccount.setAccountNumber(newAccount.getAccountNumber());
//        newAccount.setDateOpened(newAccount.getDateOpened());

        accountRepository.save(newAccount);


        return "Account number : " + newAccount.getAccountNumber() + " has been created for "
                + getUserFromUserRepository.getFirstName() + " " + getUserFromUserRepository.getLastName();

    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}
