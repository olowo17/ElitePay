package com.michael.libertybank.services;
import com.michael.libertybank.dto.account.AccountDetailsResponseDTO;
import com.michael.libertybank.dto.account.AccountRequestDto;
import com.michael.libertybank.exception.AccountServiceBusinessException;
import com.michael.libertybank.exception.EmployeeNotFoundException;
import com.michael.libertybank.model.Account;
import com.michael.libertybank.model.AccountType;
import com.michael.libertybank.repository.AccountRepository;
import com.michael.libertybank.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
@Slf4j
public class AccountService implements IAccountService{
    private UserRepository userRepository;
    private AccountRepository accountRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private AccountDetailsResponseDTO convertToAccountResponse (Account account){
        return modelMapper.map(account,AccountDetailsResponseDTO.class);
    }

    @Override
    public Page<Account> findByAccountType(AccountType accountType, Pageable pageable) {
        return accountRepository.findByAccountType(accountType,pageable);
    }

    @Override
    public Optional<Account> getByAccountNumber(String accountNumber) {
        return accountRepository.findAccountByAccountNumber(accountNumber);
    }

    @Override
    public String createAccountNumber(AccountRequestDto accountRequestDto) {
        Account newAccount = new Account();
        var customerId = accountRequestDto.customerId();
        var accountHolder = userRepository.getCustomerFullName(customerId);
        var fullName = accountHolder.firstName() + " " + accountHolder.lastName();

        var getCustomerFromRepository = userRepository.findById(customerId)
                .orElseThrow(() -> new EmployeeNotFoundException("User not found"));
        newAccount.setUser(getCustomerFromRepository);

        newAccount.setAccountType(accountRequestDto.accountType());

        newAccount.setAccountHolder(fullName);

        accountRepository.save(newAccount);


        return "Account number : " + newAccount.getAccountNumber() + " has been created for "
                + fullName;

    }

    @Override
    public Page<AccountDetailsResponseDTO> getAllAccounts(int pageNo, int recordSize) {
        List<AccountDetailsResponseDTO> accountDetailsResponseDTOs = null;
        Pageable pageable = PageRequest.of(pageNo, recordSize, Sort.by("accountHolder"));

        try {
            Page<Account> accountList = accountRepository.findAll(pageable);
            if (!accountList.isEmpty()) {
                accountDetailsResponseDTOs = accountList.map(this::convertToAccountResponse).getContent();
            } else {
                accountDetailsResponseDTOs = Collections.emptyList();
            }
        } catch (Exception ex) {
            log.error("Exception occurred while retrieving accounts from database , Exception message : {}", ex.getMessage());
            throw new AccountServiceBusinessException("Exception occurred while fetch all products from Database");
        }
        return new PageImpl<>(accountDetailsResponseDTOs, pageable, accountDetailsResponseDTOs.size());
    }


}
