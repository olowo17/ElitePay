package com.michael.libertybank.services;

import com.michael.libertybank.dto.Login.LoginRequest;
import com.michael.libertybank.dto.response.AuthenticationResponse;
import com.michael.libertybank.exception.CustomerNotFoundException;
import com.michael.libertybank.exception.EmployeeNotFoundException;
import com.michael.libertybank.exception.UnAuthorizedException;
import com.michael.libertybank.model.User;
import com.michael.libertybank.model.Role;
import com.michael.libertybank.repository.UserRepository;
import com.michael.libertybank.security.JwtService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.michael.libertybank.dto.ErrorCode.INVALID_CREDENTIALS;
import static com.michael.libertybank.util.GeneralConstants.ERROR_MSG;
import static java.lang.String.format;

@Slf4j
@Service
@AllArgsConstructor
public class LoginService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Transactional
    public AuthenticationResponse loginUser(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()
                    ));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userRepository.findCustomerByEmail(loginRequest.getEmail())
                    .orElseThrow(()-> new CustomerNotFoundException( " customer Not found"));

            var userToken = jwtService.generateJwtToken(user);
            var name = user.getFirstName() + " " + user.getLastName();
            var email = user.getEmail();
            var phoneNumber = user.getPhoneNumber();
            var id = user.getId();
            var savedPassword = user.getPassword();
            var loginPassword = loginRequest.getPassword(); // Assuming it's already plain text
            if (passwordEncoder.matches(loginPassword, savedPassword)) {
                return new AuthenticationResponse(userToken, name, id,  email,phoneNumber, user.getRole() );
            }
            throw new UnAuthorizedException("Incorrect password", INVALID_CREDENTIALS);
        } catch (Exception e) {
            log.error(format(ERROR_MSG, e.getLocalizedMessage()));
            throw new UnAuthorizedException("Invalid email/or password", INVALID_CREDENTIALS);
        }
    }

}