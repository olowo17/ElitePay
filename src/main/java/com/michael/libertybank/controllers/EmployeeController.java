package com.michael.libertybank.controllers;

import com.michael.libertybank.dto.APIResponse;
import com.michael.libertybank.dto.signUp.EmployeeRequestDTO;
import com.michael.libertybank.dto.signUp.EmployeeResponseDTO;
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
@RequestMapping("/employees")
@AllArgsConstructor
@Slf4j
public class EmployeeController {
    public static final String SUCCESS = "Success";
    private EmployeeService employeeService;
    private CustomerService customerService;

     // EMPLOYEES
    @PostMapping
    public ResponseEntity<APIResponse<EmployeeResponseDTO>> createNewEmployee(@RequestBody @Valid EmployeeRequestDTO employeeRequest) {
        log.info("Employee Controller:: create new employee request body {}", ValueMapper.jsonAsString(employeeRequest));
        EmployeeResponseDTO employeeResponse = employeeService.registerEmployee(employeeRequest);
        APIResponse<EmployeeResponseDTO> responseDto = APIResponse
                .<EmployeeResponseDTO>builder()
                .status(SUCCESS)
                .results(employeeResponse)
                .build();
        log.info("EmployeeController::createNewUser response {}", ValueMapper.jsonAsString(responseDto));

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<APIResponse> getAllUsers(){

        List<EmployeeResponseDTO> employees = employeeService.getAllEmployees();
        APIResponse<List<EmployeeResponseDTO>> responseDTO = APIResponse
                .<List<EmployeeResponseDTO>> builder()
                .status(SUCCESS)
                .results(employees)
                .build();
        log.info("CustomerController::getUsers response {}", ValueMapper.jsonAsString(responseDTO));

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
