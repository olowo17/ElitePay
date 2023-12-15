package com.michael.libertybank.repository;

import com.michael.libertybank.model.Account;
import com.michael.libertybank.model.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository <Account,Long>{
//    @Query("SELECT a.accountHolder" + "FROM Account a " + "WHERE a.accountNumber = :accountNumber")
//    String getAccountHolderFullName (@Param("accountNumber") String accountNumber);
    Optional<Account> findAccountByAccountNumber(String accountNumber);
    List<Account> findByAccountType(AccountType accountType);

}
