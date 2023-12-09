package com.michael.libertybank.dto.signUp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.michael.libertybank.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerResponseDTO {
    private Long id;
    private String firstName;
    private String LastName;
    private String phoneNumber;
    private String email;
    private String address;
    private Role role;
    @JsonFormat(pattern = "YYYY-MM-DD")
    private LocalDateTime dateJoined =LocalDateTime.now();
//    private List<Account> accounts = new ArrayList<>();
}
