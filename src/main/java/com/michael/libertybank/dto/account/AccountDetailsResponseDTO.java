package com.michael.libertybank.dto.account;

import com.michael.libertybank.model.AccountType;
import com.michael.libertybank.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetailsResponseDTO {
    private Long id;
    private AccountType accountType;
    private String accountNumber;
    private LocalDateTime dateOpened;
//    private Customer customer;

}
