package com.michael.libertybank.dto.transaction;

import java.math.BigDecimal;

public record WithdrawalDto(String acctNumber, BigDecimal withdrawalAmt) {
}
