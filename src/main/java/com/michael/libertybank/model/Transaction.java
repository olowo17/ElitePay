package com.michael.libertybank.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @GeneratedValue(strategy = GenerationType.UUID)
    private String transactionId;
    private LocalDateTime transactionDate;
    private String senderAccountNumber;
    private String receiverAccountNumber;
    private String transactionAmount;
}
