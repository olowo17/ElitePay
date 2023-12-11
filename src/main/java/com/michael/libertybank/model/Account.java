package com.michael.libertybank.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Random;

@Data
@AllArgsConstructor
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    @JsonManagedReference
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "accounts should be SAVINGS  OR CURRENT")
    private AccountType accountType;

    private String accountNumber;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dateOpened;

    // Define a static Random object
    private static final Random random = new Random();

    public Account() {
        this.accountNumber = generateAccountNumber();
        this.dateOpened = LocalDateTime.now();
    }

    private String generateAccountNumber() {
        StringBuilder accountNumberBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            accountNumberBuilder.append(random.nextInt(10));
        }
        return accountNumberBuilder.toString();
    }
}
