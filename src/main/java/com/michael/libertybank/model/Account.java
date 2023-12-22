package com.michael.libertybank.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
@AllArgsConstructor
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountNumber;
    private String accountHolder;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "accounts should be SAVINGS OR CURRENT")
    private AccountType accountType;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dateOpened;

    private BigDecimal accountBalance;

    @OneToMany(mappedBy = "senderAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Transaction> sentTransactions = new ArrayList<>();

    @OneToMany(mappedBy = "receiverAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Transaction> receivedTransactions = new ArrayList<>();

    private static final Random random = new Random();

    public Account() {
        this.accountNumber = generateAccountNumber();
        this.dateOpened = LocalDateTime.now();
        this.accountBalance = BigDecimal.ZERO;

    }

    private String generateAccountNumber() {
        StringBuilder accountNumberBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            accountNumberBuilder.append(random.nextInt(10));
        }
        return accountNumberBuilder.toString();
    }

}
