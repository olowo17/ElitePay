package com.michael.libertybank.dto.Login;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeLoginRequest {
    @NotBlank(message = "username shouldn't be NULL OR EMPTY")
    private String userName;
    @NotBlank(message = "password shouldn't be NULL OR EMPTY")
    private String password;
}
