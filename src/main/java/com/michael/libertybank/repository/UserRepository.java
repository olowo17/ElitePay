package com.michael.libertybank.repository;

import com.michael.libertybank.dto.FullNameDto;
import com.michael.libertybank.exception.CustomerNotFoundException;
import com.michael.libertybank.exception.EmployeeNotFoundException;
import com.michael.libertybank.model.User;
import com.michael.libertybank.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface  UserRepository extends JpaRepository <User,Long> {
    Optional<User> findUserByEmailAndRole(String email, Role role);
    List<User> findByRole(Role role);
    Optional<User> findCustomerByEmail(String email);

    Page<User> findAll(Pageable pageable);
    @Query("SELECT new  com.michael.libertybank.dto.FullNameDto (c.firstName, c.lastName) FROM User c WHERE c.id = :customerId")
    FullNameDto getCustomerFullName(@Param("customerId") Long customerId);

}