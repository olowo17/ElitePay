package com.michael.libertybank.controllers;

import com.michael.libertybank.dto.Login.LoginRequest;
import com.michael.libertybank.dto.response.AuthenticationResponse;
import com.michael.libertybank.services.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUserEntity(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(loginService.loginUser(loginRequest), HttpStatus.OK);
    }

}
