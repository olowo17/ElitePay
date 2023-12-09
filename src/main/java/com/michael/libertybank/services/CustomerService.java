package com.michael.libertybank.services;

import com.michael.libertybank.dto.signUp.CustomerRequestDTO;
import com.michael.libertybank.dto.signUp.CustomerResponseDTO;
import com.michael.libertybank.exception.CustomerServiceBusinessException;
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
    private CustomerRepository customerRepository;
    @Override
    public CustomerResponseDTO registerUser(CustomerRequestDTO customerRequestDto) throws CustomerServiceBusinessException {
        CustomerResponseDTO customerResponseDTO;
        try{
            log.info("CustomerService: registerNewUser execution started");
            Customer customer = ValueMapper.convertToUserEntity(customerRequestDto);
            log.debug("CustomerService: register New Customer request parameter {}",
                    ValueMapper.jsonAsString(customerRequestDto));

            Customer customerResults = customerRepository.save(customer);
            customerResponseDTO = ValueMapper.convertToUserDto(customerResults);
            log.debug("CustomerService: register new product received response from Database {}",
                    ValueMapper.jsonAsString(customerRequestDto));
        }catch (Exception e){
            log.error("Exception Occurred wile persisting customer to database, Exception message {}", e.getMessage());
            throw new CustomerServiceBusinessException("Exception occurred wile creating a new customer");
        }

        log.info("CustomerService:createNewUser execution ended.");
        return customerResponseDTO;
    }

    @Override
    public List<CustomerResponseDTO> getAllUsers() throws CustomerServiceBusinessException {
        List<CustomerResponseDTO> customerResponseDTOS = null;
        try {
            log.info("CustomerService:getCustomer execution started.");

            List<Customer> customerList = customerRepository.findAll();

            if (!customerList.isEmpty()) {
                customerResponseDTOS = customerList.stream()
                        .map(ValueMapper::convertToUserDto)
                        .collect(Collectors.toList());
            } else {
                customerResponseDTOS = Collections.emptyList();
            }
        }
        catch (Exception ex) {
            log.error("Exception occurred while retrieving customers from database , Exception message {}", ex.getMessage());
            throw new CustomerServiceBusinessException("Exception occurred while fetch all products from Database");
        }

        log.info("CustomerService:getCustomers execution ended.");
        return customerResponseDTOS;


    }
}
