package com.michael.libertybank.repository;

import com.michael.libertybank.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository <Customer,Long> {

}

