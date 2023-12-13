package com.michael.libertybank.services;
import com.michael.libertybank.dto.account.AccountDetailsResponseDTO;
import com.michael.libertybank.dto.account.AccountRequestDto;
import com.michael.libertybank.exception.AccountServiceBusinessException;
import com.michael.libertybank.exception.EmployeeNotFoundException;
import com.michael.libertybank.model.Account;
import com.michael.libertybank.model.AccountType;
import com.michael.libertybank.repository.AccountRepository;
import com.michael.libertybank.repository.EmployeeRepository;
import com.michael.libertybank.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private AccountDetailsResponseDTO convertToAccountResponse (Account account){
        return modelMapper.map(account,AccountDetailsResponseDTO.class);
    }

    @Override
    public List<Account> findByAccountType(AccountType accountType) {
        return accountRepository.findByAccountType(accountType);
    }

    @Override
    public Optional<Account> getByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    @Override
    public String createAccountNumber(AccountRequestDto accountRequestDto) {
        Account newAccount = new Account();
        var customerId = accountRequestDto.customerId();
        var getCustomerFromRepository = userRepository.findById(customerId)
                .orElseThrow(() -> new EmployeeNotFoundException("Customer not found"));
        newAccount.setCustomer(getCustomerFromRepository);

        newAccount.setAccountType(accountRequestDto.accountType());
        accountRepository.save(newAccount);


        return "Account number : " + newAccount.getAccountNumber() + " has been created for "
                + getCustomerFromRepository.getFirstName() + " " + getCustomerFromRepository.getLastName();

    }

    @Override
    public List<AccountDetailsResponseDTO> getAllAccounts() {
       List <AccountDetailsResponseDTO>  accountDetailsResponseDTOs = null;

        try{
            List<Account> accountList = accountRepository.findAll();
            if (!accountList.isEmpty()){
                accountDetailsResponseDTOs=accountList.stream()
                        .map(this::convertToAccountResponse).collect(Collectors.toList());
            }
            else {
                accountDetailsResponseDTOs = Collections.emptyList();
            }
        }catch (Exception ex){
            log.error("Exception occurred while retrieving accounts from database , Exception message : {}", ex.getMessage());
            throw new AccountServiceBusinessException("Exception occurred while fetch all products from Database");
        }
        return accountDetailsResponseDTOs;

}

}
