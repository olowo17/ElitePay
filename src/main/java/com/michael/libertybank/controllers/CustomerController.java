package com.michael.libertybank.controllers;

import com.michael.libertybank.dto.APIResponse;
import com.michael.libertybank.dto.signUp.CustomerRequestDTO;
import com.michael.libertybank.dto.signUp.CustomerResponseDTO;
import com.michael.libertybank.services.CustomerService;
import com.michael.libertybank.util.ValueMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@AllArgsConstructor
@Slf4j
public class CustomerController {
    public static final String SUCCESS = "Success";
    private CustomerService customerService;
    // USERS
    @PostMapping
    public ResponseEntity<APIResponse> createNewUser(@RequestBody @Valid CustomerRequestDTO customerRequestDTO) {
        log.info("Customer Controller:: create new user request body {}", ValueMapper.jsonAsString(customerRequestDTO));
        CustomerResponseDTO customerResponseDTO = customerService.registerUser(customerRequestDTO);

        APIResponse< CustomerResponseDTO> responseDto = APIResponse
                .< CustomerResponseDTO>builder()
                .status(SUCCESS)
                .results(customerResponseDTO)
                .build();
        log.info("EmployeeController::createNewUser response {}", ValueMapper.jsonAsString(responseDto));

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<APIResponse> getAllUsers(){
        List<CustomerResponseDTO> users = customerService.getAllUsers();
        APIResponse<List<CustomerResponseDTO>> responseDTO = APIResponse
                .<List<CustomerResponseDTO>> builder()
                .status(SUCCESS)
                .results(users)
                .build();
        log.info("CustomerController::getUsers response {}", ValueMapper.jsonAsString(responseDTO));

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


}
