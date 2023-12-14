package com.michael.libertybank.repository;

import com.michael.libertybank.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface CustomerRepository extends JpaRepository <Customer,Long> {
    @Query ("SELECT c.firstName, c.lastName FROM Customer c ")
    String getCustomerFullName ();
}

