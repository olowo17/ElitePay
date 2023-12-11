package com.michael.libertybank.services;

import com.michael.libertybank.dto.account.AccountDetailsResponseDTO;
import com.michael.libertybank.dto.account.AccountRequestDto;
import com.michael.libertybank.model.Account;
import com.michael.libertybank.model.AccountType;
//import com.michael.libertybank.model.Account;

import java.util.List;
import java.util.Optional;

public interface IAccountService {
    public List<AccountDetailsResponseDTO> getAllAccounts ();
    public String createAccountNumber (AccountRequestDto accountRequestDto);
    public Optional<Account> getByAccountNumber(String accountNumber);
    public  List<Account> findByAccountType(AccountType accountType);

}
