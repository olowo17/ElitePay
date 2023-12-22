package com.michael.libertybank.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.michael.libertybank.dto.signUp.SignUpRequestDTO;
import com.michael.libertybank.dto.signUp.SignUpResponseDTO;
import com.michael.libertybank.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@Data
@Slf4j
@Component
public class Converter implements Serializable {

    public static String jsonAsString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User convertToCustomerEntity(SignUpRequestDTO userSignUpRequest) {
        var user = new User();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setFirstName(userSignUpRequest.getFirstName());
        user.setLastName(userSignUpRequest.getLastName());
        user.setEmail(userSignUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userSignUpRequest.getPassword()));
        user.setAddress(userSignUpRequest.getAddress());
        user.setPhoneNumber(userSignUpRequest.getPhoneNumber());
        user.setRole(userSignUpRequest.getRole());
        user.setDateJoined(LocalDateTime.now());
        return user;
    }

    public static SignUpResponseDTO convertToCustomerDto(User user) {
        var userSignUpResponse = new SignUpResponseDTO();
        userSignUpResponse.setId(user.getId());
        userSignUpResponse.setFirstName(user.getFirstName());
        userSignUpResponse.setLastName(user.getLastName());
        userSignUpResponse.setEmail(user.getEmail());
        userSignUpResponse.setPhoneNumber(user.getPhoneNumber());
        userSignUpResponse.setDateJoined(user.getDateJoined());
        userSignUpResponse.setAddress(user.getAddress());
        userSignUpResponse.setRole(user.getRole());
        return userSignUpResponse;
    }



}
