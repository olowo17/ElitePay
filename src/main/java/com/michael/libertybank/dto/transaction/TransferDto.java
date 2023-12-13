package com.michael.libertybank.dto.transaction;

import java.math.BigDecimal;

public record TransferDto(String senderAcctNumber, String receiverAcctNumber, BigDecimal transactionAmt) {
}
