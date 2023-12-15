package com.michael.libertybank.services;

import com.michael.libertybank.dto.signUp.CustomerRequestDTO;
import com.michael.libertybank.dto.signUp.CustomerResponseDTO;
import com.michael.libertybank.exception.CustomerServiceBusinessException;
import com.michael.libertybank.model.Customer;
import com.michael.libertybank.repository.CustomerRepository;
import com.michael.libertybank.util.ValueMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
            Customer customer = ValueMapper.convertToCustomerEntity(customerRequestDto);
            log.debug("CustomerService: register New Customer request parameter {}",
                    ValueMapper.jsonAsString(customerRequestDto));

            Customer customerResults = customerRepository.save(customer);
            customerResponseDTO = ValueMapper.convertToCustomerDto(customerResults);
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
    public Page<CustomerResponseDTO> getAllUsers(Pageable pageable) throws CustomerServiceBusinessException {
        List<CustomerResponseDTO> customerResponseDTOS = null;

        List<Customer> customerList;
        try {
            log.info("CustomerService:getCustomer execution started.");

            customerList = customerRepository.findAll(pageable).getContent();

            if (!customerList.isEmpty()) {
                customerResponseDTOS = customerList.stream()
                        .map(ValueMapper::convertToCustomerDto)
                        .collect(Collectors.toList());
            } else {
                customerResponseDTOS = Collections.emptyList();
            }
        } catch (Exception ex) {
            log.error("Exception occurred while retrieving customers from database, Exception message {}", ex.getMessage());
            throw new CustomerServiceBusinessException("Exception occurred while fetching all products from Database");
        }

        log.info("CustomerService:getCustomers execution ended.");
        return new PageImpl<>(customerResponseDTOS, pageable, customerList.size());
    }


}

