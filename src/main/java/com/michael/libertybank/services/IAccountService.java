package com.michael.libertybank.services;

import com.michael.libertybank.dto.account.AccountDetailsResponseDTO;
import com.michael.libertybank.dto.account.AccountRequestDto;
import com.michael.libertybank.model.Account;
import com.michael.libertybank.model.AccountType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

public interface IAccountService {
    public Page<AccountDetailsResponseDTO> getAllAccounts(int pageNo, int recordCount);
    public String createAccountNumber (AccountRequestDto accountRequestDto);
    public Optional<Account> getByAccountNumber(String accountNumber);
    public Page<Account> findByAccountType(AccountType accountType, Pageable pageable);

}
