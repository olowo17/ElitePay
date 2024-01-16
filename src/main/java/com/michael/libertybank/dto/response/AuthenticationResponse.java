package com.michael.libertybank.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.michael.libertybank.model.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 2749436181809833674L;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("name")
    private String name;

    @NotBlank(message = "role must not be blank")
    @JsonProperty("role")
    private Role role;
}