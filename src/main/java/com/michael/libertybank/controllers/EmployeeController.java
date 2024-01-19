package com.michael.libertybank.controllers;

import com.michael.libertybank.dto.APIResponse;
import com.michael.libertybank.dto.signUp.SignUpResponseDTO;
import com.michael.libertybank.exception.CustomerNotFoundException;
import com.michael.libertybank.model.Role;
import com.michael.libertybank.model.User;
import com.michael.libertybank.services.UserService;
import com.michael.libertybank.util.Converter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@AllArgsConstructor
@Slf4j
public class EmployeeController {
    public static final String SUCCESS = "Success";
    private UserService userService;
    @GetMapping
    public ResponseEntity<APIResponse<Page<SignUpResponseDTO>>> getAllUsers(Pageable pageable) {
        Page<SignUpResponseDTO> usersPage = userService.getAllUsers(pageable);

        APIResponse<Page<SignUpResponseDTO>> responseDTO = APIResponse
                .<Page<SignUpResponseDTO>>builder()
                .status(SUCCESS)
                .results(usersPage)
                .build();

        log.info("CustomerController::getUsers response {}", Converter.jsonAsString(responseDTO));

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/role")
    public List<User> getByRole (@RequestParam Role role){
        return userService.getUsersByRole(role);
    }

}