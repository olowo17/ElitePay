package com.michael.libertybank.dto.account;

import com.michael.libertybank.model.AccountType;

import java.util.List;

public record AccountRequestDto(Long userId, Long employeeId, AccountType accountType) {
}
