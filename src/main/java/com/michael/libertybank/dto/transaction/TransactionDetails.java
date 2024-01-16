package com.michael.libertybank.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetails {
    private String transactionId;
    private String senderFullName;
    private String receiverFullName;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime transactionDate;

    private BigDecimal transactionAmount;
    private Currency transactionCurrency;
}
