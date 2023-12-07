package com.michael.libertybank.services;

import com.michael.libertybank.dto.signUp.UserRequestDTO;
import com.michael.libertybank.dto.signUp.UserResponseDTO;
import com.michael.libertybank.exception.UserServiceBusinessException;
import com.michael.libertybank.model.Customer;
import com.michael.libertybank.repository.CustomerRepository;
import com.michael.libertybank.util.ValueMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerService implements ICustomerService {
    private CustomerRepository userRepository;
    @Override
    public UserResponseDTO registerUser(UserRequestDTO userRequestDto) throws UserServiceBusinessException {
        UserResponseDTO userResponseDTO;
        try{
            log.info("CustomerService: registerNewUser execution started");
            Customer customer = ValueMapper.convertToUserEntity(userRequestDto);
            log.debug("CustomerService: register New Customer request parameter {}",
                    ValueMapper.jsonAsString(userRequestDto));

            Customer customerResults = userRepository.save(customer);
            userResponseDTO = ValueMapper.convertToUserDto(customerResults);
            log.debug("CustomerService: register new product received response from Database {}",
                    ValueMapper.jsonAsString(userRequestDto));
        }catch (Exception e){
            log.error("Exception Occurred wile persisting user to database, Exception message {}", e.getMessage());
            throw new UserServiceBusinessException("Exception occurred wile creating a new user");
        }

        log.info("CustomerService:createNewUser execution ended.");
        return userResponseDTO;
    }

    @Override
    public List<UserResponseDTO> getAllUsers() throws UserServiceBusinessException {
        List<UserResponseDTO> userResponseDTOS = null;
        try {
            log.info("ProductService:getProducts execution started.");

            List<Customer> customerList = userRepository.findAll();

            if (!customerList.isEmpty()) {
                userResponseDTOS = customerList.stream()
                        .map(ValueMapper::convertToUserDto)
                        .collect(Collectors.toList());
            } else {
                userResponseDTOS = Collections.emptyList();
            }
        }
        catch (Exception ex) {
            log.error("Exception occurred while retrieving products from database , Exception message {}", ex.getMessage());
            throw new UserServiceBusinessException("Exception occurred while fetch all products from Database");
        }

        log.info("ProductService:getProducts execution ended.");
        return userResponseDTOS;


    }
}
