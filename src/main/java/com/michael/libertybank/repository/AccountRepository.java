package com.michael.libertybank.repository;
import com.michael.libertybank.model.Account;
import com.michael.libertybank.model.AccountType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Page<Account> findAll(Pageable pageable);

    Optional<Account> findAccountByAccountNumber(String accountNumber);

    Page<Account> findByAccountType(AccountType accountType, Pageable pageable);
}
