package com.michael.libertybank.dto.account;

import com.michael.libertybank.model.AccountType;




public record AccountRequestDto ( Long customerId, AccountType accountType){
}
