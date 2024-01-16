package com.michael.libertybank.services;
import com.michael.libertybank.dto.signUp.SignUpRequestDTO;
import com.michael.libertybank.dto.signUp.SignUpResponseDTO;
import com.michael.libertybank.exception.AccountNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {
    public SignUpResponseDTO registerUser(SignUpRequestDTO signUpRequestDto);
    public Page<SignUpResponseDTO> getAllUsers(Pageable pageable);


}
