package com.michael.libertybank.dto.account;

import com.michael.libertybank.model.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequestDto{
    private Long customerId;
    private AccountType accountType;
}
