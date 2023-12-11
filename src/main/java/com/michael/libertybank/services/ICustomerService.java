package com.michael.libertybank.services;
import com.michael.libertybank.dto.signUp.CustomerRequestDTO;
import com.michael.libertybank.dto.signUp.CustomerResponseDTO;

import java.util.List;

public interface ICustomerService {
    public CustomerResponseDTO registerUser(CustomerRequestDTO customerRequestDto);
    public List<CustomerResponseDTO> getAllUsers();

}
