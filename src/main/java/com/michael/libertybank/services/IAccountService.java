package com.michael.libertybank.services;

import com.michael.libertybank.dto.account.AccountRequestDto;
import com.michael.libertybank.model.Account;

import java.util.List;

public interface IAccountService {
    public List<Account> getAllAccounts ();
    public String createAccountNumber (AccountRequestDto accountRequestDto);
}
