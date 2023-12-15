package com.michael.libertybank.services;
import com.michael.libertybank.dto.signUp.CustomerRequestDTO;
import com.michael.libertybank.dto.signUp.CustomerResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICustomerService {
    public CustomerResponseDTO registerUser(CustomerRequestDTO customerRequestDto);
    public Page<CustomerResponseDTO> getAllUsers(Pageable pageable);

}
