package com.michael.libertybank.controllers;

import com.michael.libertybank.dto.APIResponse;
import com.michael.libertybank.dto.signUp.SignUpRequestDTO;
import com.michael.libertybank.dto.signUp.SignUpResponseDTO;
import com.michael.libertybank.repository.UserRepository;
import com.michael.libertybank.services.UserService;
import com.michael.libertybank.util.Converter;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Slf4j
public class UserController {
    public static final String SUCCESS = "Success";
    private UserService customerService;
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<APIResponse> createNewUser(@RequestBody @Valid SignUpRequestDTO signUpRequestDTO) {
        log.info("User Controller:: create new user request body {}", Converter.jsonAsString(signUpRequestDTO));
        SignUpResponseDTO signUpResponseDTO = customerService.registerUser(signUpRequestDTO);

        APIResponse<SignUpResponseDTO> responseDto = APIResponse
                .<SignUpResponseDTO>builder()
                .status(SUCCESS)
                .results(signUpResponseDTO)
                .build();
        log.info("EmployeeController::createNewUser response {}", Converter.jsonAsString(responseDto));

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }


}
