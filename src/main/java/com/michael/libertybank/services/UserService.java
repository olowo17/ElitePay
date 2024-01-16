package com.michael.libertybank.services;

import com.michael.libertybank.dto.signUp.SignUpRequestDTO;
import com.michael.libertybank.dto.signUp.SignUpResponseDTO;
import com.michael.libertybank.exception.AccountNotFoundException;
import com.michael.libertybank.exception.CustomerNotFoundException;
import com.michael.libertybank.exception.CustomerServiceBusinessException;
import com.michael.libertybank.model.Role;
import com.michael.libertybank.model.User;
import com.michael.libertybank.repository.AccountRepository;
import com.michael.libertybank.repository.UserRepository;
import com.michael.libertybank.util.Converter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserService implements IUserService {
    private UserRepository userRepository;
    AccountRepository accountRepository;
    @Override
    public SignUpResponseDTO registerUser(SignUpRequestDTO signUpRequestDto) throws CustomerServiceBusinessException {
        SignUpResponseDTO signUpResponseDTO;
        try{
            log.info("UserService: registerNewUser execution started");
            User user = Converter.convertToCustomerEntity(signUpRequestDto);
            log.debug("UserService: register New User request parameter {}",
                    Converter.jsonAsString(signUpRequestDto));

            User userResults = userRepository.save(user);
            signUpResponseDTO = Converter.convertToCustomerDto(userResults);
            log.debug("UserService: register new product received response from Database {}",
                    Converter.jsonAsString(signUpRequestDto));
        }catch (Exception e){
            log.error("Exception Occurred wile persisting customer to database, Exception message {}", e.getMessage());
            throw new CustomerServiceBusinessException("Exception occurred wile creating a new customer");
        }

        log.info("UserService:createNewUser execution ended.");
        return signUpResponseDTO;
    }

    @Override
    public Page<SignUpResponseDTO> getAllUsers(Pageable pageable) throws CustomerServiceBusinessException {
        List<SignUpResponseDTO> signUpResponseDTOS = null;

        List<User> userList;
        try {
            log.info("UserService:getUser execution started.");

            userList = userRepository.findAll(pageable).getContent();

            if (!userList.isEmpty()) {
                signUpResponseDTOS = userList.stream()
                        .map(Converter::convertToCustomerDto)
                        .collect(Collectors.toList());
            } else {
                signUpResponseDTOS = Collections.emptyList();
            }
        } catch (Exception ex) {
            log.error("Exception occurred while retrieving customers from database, Exception message {}", ex.getMessage());
            throw new CustomerServiceBusinessException("Exception occurred while fetching all products from Database");
        }

        log.info("UserService:getCustomers execution ended.");
        return new PageImpl<>(signUpResponseDTOS, pageable, userList.size());
    }
    public List<User> getUsersByRole(Role role){
        return userRepository.findByRole(role);
    }
    public User getUserByEmail(String email){
        return userRepository.findCustomerByEmail(email).orElseThrow(()-> new CustomerNotFoundException(" user not found"));
    }

}

