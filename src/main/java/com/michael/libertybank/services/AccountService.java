package com.michael.libertybank.services;

import com.michael.libertybank.dto.account.AccountDetailsResponseDTO;
import com.michael.libertybank.dto.account.AccountRequestDto;
import com.michael.libertybank.exception.AccountServiceBusinessException;
import com.michael.libertybank.model.Account;
import com.michael.libertybank.repository.AccountRepository;
import com.michael.libertybank.repository.EmployeeRepository;
import com.michael.libertybank.repository.CustomerRepository;
import com.michael.libertybank.util.ValueMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class AccountService implements IAccountService{
    private EmployeeRepository employeeRepository;
    private CustomerRepository userRepository;
    private static AccountRepository accountRepository;
    @Override
    public AccountDetailsResponseDTO createAccountNumber(AccountRequestDto accountRequestDto) throws AccountServiceBusinessException {
            AccountDetailsResponseDTO accountResponseDTO;

            try{
                log.info("AccountService: registerAccount execution started");
                var account = ValueMapper.convertToAccountEntity(accountRequestDto);
                log.debug("CustomerService: register New Employee request parameter {}",
                        ValueMapper.jsonAsString(accountRequestDto));
                Account accountResult = accountRepository.save(account);
                accountResponseDTO = ValueMapper.convertToAccountDto(accountResult);
                log.debug("CustomerService: register new account received response from Database {}",
                        ValueMapper.jsonAsString(accountRequestDto));
            }catch (Exception e){
                log.error("Exception Occurred wile persisting user to database, Exception message {}", e.getMessage());
                throw new AccountServiceBusinessException("Exception occurred wile creating a new account");
            }

            log.info("CustomerService:createNewUser execution ended.");
            return accountResponseDTO;
        }


//        return "Account number : " + newAccount.getAccountNumber() + " has been created for "
//                + getUserFromUserRepository.getFirstName() + " " + getUserFromUserRepository.getLastName();


    @Override
    public List<AccountDetailsResponseDTO> getAllAccounts() throws  AccountServiceBusinessException {
            List<AccountDetailsResponseDTO> AccountResponseDTO = null;
            try {
                log.info("AccountService:getAccounts execution started.");

                List<Account> accountList = accountRepository.findAll();

                if (! accountList.isEmpty()) {
                    AccountResponseDTO = accountList.stream()
                            .map(ValueMapper::convertToAccountDto)
                            .collect(Collectors.toList());
                } else {
                    AccountResponseDTO= Collections.emptyList();
                }
            }
            catch (Exception ex) {
                log.error("Exception occurred while retrieving accounts from database , Exception message {}", ex.getMessage());
                throw new AccountServiceBusinessException("Exception occurred while fetch all products from Database");
            }

            log.info("AccountService:getProducts execution ended.");
            return AccountResponseDTO;

    }

    @Override
    public Optional<Account> getByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }
}
