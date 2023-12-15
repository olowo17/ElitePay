package com.michael.libertybank.repository;

import com.michael.libertybank.dto.FullNameDto;
import com.michael.libertybank.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CustomerRepository extends JpaRepository <Customer,Long> {
    @Query("SELECT new  com.michael.libertybank.dto.FullNameDto (c.firstName, c.lastName) FROM Customer c WHERE c.id = :customerId")
    FullNameDto getCustomerFullName(@Param("customerId") Long customerId);


}