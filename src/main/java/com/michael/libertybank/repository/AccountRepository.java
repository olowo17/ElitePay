package com.michael.libertybank.repository;

import com.michael.libertybank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository <Account,Long>{
}
