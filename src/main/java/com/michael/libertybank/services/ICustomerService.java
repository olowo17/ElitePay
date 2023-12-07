package com.michael.libertybank.services;
import com.michael.libertybank.dto.signUp.UserRequestDTO;
import com.michael.libertybank.dto.signUp.UserResponseDTO;

import java.util.List;

public interface ICustomerService {
    public UserResponseDTO registerUser(UserRequestDTO userRequestDto);
    public List<UserResponseDTO> getAllUsers();

}
