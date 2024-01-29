package com.michael.libertybank.repository;

import com.michael.libertybank.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    Optional<Transaction> findByTransactionId(String transactionId);

    @Query("SELECT t FROM Transaction t " +
            "WHERE t.senderAccount.accountNumber = :accountNumber AND " +
            "t.receiverAccount.accountNumber = :accountNumber")
    List<Transaction> findBySenderOrReceiverAccountNumber(@Param("accountNumber") String accountNumber);

    @Query("SELECT t FROM Transaction t " +
            "WHERE (t.senderAccount.accountNumber = :accountNumber OR" +
            " t.receiverAccount.accountNumber = :accountNumber) " +
            "AND t.transactionDate BETWEEN :startDate AND :endDate")
    Page<Transaction> findBySenderOrReceiverAccountNumberAndDate(
            @Param("accountNumber") String accountNumber,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);

    @Query("SELECT t FROM Transaction t " +
            "WHERE t.senderAccount.id = :accountId OR t.receiverAccount.id = :accountId")
    List<Transaction> findBySenderOrReceiverAccountId(@Param("accountId") Long accountId);
}
