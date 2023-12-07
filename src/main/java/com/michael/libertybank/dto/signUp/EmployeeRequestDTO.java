package com.michael.libertybank.dto.signUp;

import com.michael.libertybank.model.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EmployeeRequestDTO {
    @NotBlank(message = "employee FirstName shouldn't be NULL OR EMPTY")
    private String employeeFirstName;
    @NotBlank(message = "employee lastName shouldn't be NULL OR EMPTY")
    private String employeeLastName;
    @NotBlank(message = "username shouldn't be NULL OR EMPTY")
    private String userName;
    @NotBlank(message = "password shouldn't be NULL OR EMPTY")
    private String password;
    @NotNull(message = "role should  be  EMPLOYEE or ADMIN")
    @Enumerated(EnumType.STRING)
    private Role role;

}
