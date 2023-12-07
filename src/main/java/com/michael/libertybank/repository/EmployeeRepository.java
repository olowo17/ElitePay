package com.michael.libertybank.repository;

import com.michael.libertybank.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository <Employee,Long>{
}
