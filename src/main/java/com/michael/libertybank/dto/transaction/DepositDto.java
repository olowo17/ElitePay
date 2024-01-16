package com.michael.libertybank.dto.transaction;

import java.math.BigDecimal;

public record DepositDto(String acctNumber, BigDecimal depositAmt) {
}
