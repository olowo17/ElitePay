package com.michael.libertybank.services;

import com.michael.libertybank.exception.AccountNotFoundException;


public interface IAdminService {
    public String deleteAccount(String accountNumber) throws AccountNotFoundException;
    public String deleteAllAccounts();
}
