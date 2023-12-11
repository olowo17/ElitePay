package com.michael.libertybank.dto.signUp;

import com.michael.libertybank.model.Account;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CustomerRequestDTO {
    @NotBlank(message = "firstName shouldn't be NULL OR EMPTY")
    private String firstName;
    @NotBlank(message = "lastName shouldn't be NULL OR EMPTY")
    private String LastName;
    @NotBlank(message = "password shouldn't be NULL OR EMPTY")
    private String password;
    @NotBlank(message = "phoneNumber shouldn't be NULL OR EMPTY")
    private String phoneNumber;
    @NotBlank(message = "email shouldn't be NULL OR EMPTY")
    private String email;
    @NotBlank(message = "address shouldn't be NULL OR EMPTY")
    private String address;

}
