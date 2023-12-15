package com.michael.libertybank.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Random;


@Data
@AllArgsConstructor
@Entity
public class Transaction {
    @Id
    @Column(unique = true)
    private String transactionId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime transactionDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_account_id")
    @JsonManagedReference
    private Account senderAccount;

    private String senderAccountNumber;
    private String receiverAccountNumber;
    private String senderFullName;
    private String receiverFullName;
    private Currency currency;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "receiver_account_id")
    @JsonManagedReference
    private Account receiverAccount;

    private BigDecimal transactionAmount;

    public Transaction() {
        this.transactionId = generateTransactionId();
        this.transactionDate = LocalDateTime.now();
        this.currency = Currency.getInstance("NGN");
    }

    private static final Random random = new Random();

    private String generateTransactionId() {
        StringBuilder transactionIdBuilder = new StringBuilder();
        for (int i = 0; i < 15; i++) {
            transactionIdBuilder.append(random.nextInt(15));
        }
        return transactionIdBuilder.toString();
    }
}