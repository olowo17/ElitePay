package com.michael.libertybank.dto.signUp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.michael.libertybank.model.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class EmployeeResponseDTO {
    private Long id;
    private String employeeFirstName;
    private String employeeLastName;
    private String userName;
    @Enumerated(EnumType.STRING)
    private Role role;
}
