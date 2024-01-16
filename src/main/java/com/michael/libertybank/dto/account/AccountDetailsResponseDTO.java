package com.michael.libertybank.dto.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.michael.libertybank.model.AccountType;
import com.michael.libertybank.model.User;
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dateOpened;
    private User user;

}
