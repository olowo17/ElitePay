package com.michael.libertybank.services;
import com.michael.libertybank.dto.signUp.EmployeeRequestDTO;
import com.michael.libertybank.dto.signUp.EmployeeResponseDTO;

import java.util.List;

public interface IEmployeeService {
    public EmployeeResponseDTO registerEmployee(EmployeeRequestDTO employeeSignUpRequest);
    public List<EmployeeResponseDTO> getAllEmployees();

}
