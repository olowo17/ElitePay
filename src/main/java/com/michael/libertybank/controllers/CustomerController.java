package com.michael.libertybank.controllers;

import com.michael.libertybank.dto.APIResponse;
import com.michael.libertybank.dto.signUp.UserRequestDTO;
import com.michael.libertybank.dto.signUp.UserResponseDTO;
import com.michael.libertybank.services.CustomerService;
import com.michael.libertybank.services.EmployeeService;
import com.michael.libertybank.util.ValueMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Slf4j
public class CustomerController {
    public static final String SUCCESS = "Success";
    private EmployeeService employeeService;
    private CustomerService customerService;
    // USERS
    @PostMapping
    public ResponseEntity<APIResponse> createNewUser(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        log.info("Customer Controller:: create new user request body {}", ValueMapper.jsonAsString(userRequestDTO));
        UserResponseDTO userResponseDTO = customerService.registerUser(userRequestDTO);

        APIResponse< UserResponseDTO> responseDto = APIResponse
                .< UserResponseDTO>builder()
                .status(SUCCESS)
                .results(userResponseDTO)
                .build();
        log.info("EmployeeController::createNewUser response {}", ValueMapper.jsonAsString(responseDto));

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<APIResponse> getAllUsers(){
        List<UserResponseDTO> users = customerService.getAllUsers();
        APIResponse<List<UserResponseDTO>> responseDTO = APIResponse
                .<List<UserResponseDTO>> builder()
                .status(SUCCESS)
                .results(users)
                .build();
        log.info("CustomerController::getUsers response {}", ValueMapper.jsonAsString(responseDTO));

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }



}
