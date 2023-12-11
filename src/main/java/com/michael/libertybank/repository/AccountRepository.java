package com.michael.libertybank.repository;

import com.michael.libertybank.dto.account.AccountDetailsResponseDTO;
import com.michael.libertybank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository <Account,Long>{
    @Query("SELECT a.accountNumber, a.accountType, a.dateOpened, c.firstName, c.lastName " +
            "FROM Account a " +
            "JOIN a.customer c")
    List<AccountDetailsResponseDTO> getAccountDetails();

    Optional<Account> findByAccountNumber(String accountNumber);
}
