package com.michael.libertybank.dto.signUp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.michael.libertybank.model.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SignUpRequestDTO {
    @NotBlank(message = "firstName shouldn't be NULL OR EMPTY")
    private String firstName;

    @NotBlank(message = "lastName shouldn't be NULL OR EMPTY")
    private String lastName;

    @Size(min = 6, message = "password length must not be less than 6 characters")
    private String password;


    @NotBlank(message = "phoneNumber shouldn't be NULL OR EMPTY")
    private String phoneNumber;

    @Email(message = "please enter a valid email")
    @NotBlank(message = "email shouldn't be NULL OR EMPTY")
    private String email;

    @NotBlank(message = "address shouldn't be NULL OR EMPTY")
    private String address;

    @NotNull(message = "role should be 'USER', 'EMPLOYEE' OR 'ADMIN'")
    @Enumerated(EnumType.STRING)
    private Role role;

}
