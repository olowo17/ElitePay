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

    @Transactional
    public AuthenticationResponse loginUser(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()
                    ));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userRepository.findUserByEmailAndRole(loginRequest.getEmail(), Role.USER)
                    .orElseThrow(()-> new CustomerNotFoundException( " customer Not found"));

            var userToken = jwtService.generateJwtToken(user);
            var name = user.getFirstName() + " " + user.getLastName();
            return new AuthenticationResponse(userToken, name, user.getRole());
        } catch (Exception e) {
            log.error(format(ERROR_MSG, e.getLocalizedMessage()));
            throw new UnAuthorizedException("Invalid email/or password", INVALID_CREDENTIALS);
        }
    }

    @Transactional
    public AuthenticationResponse loginEmployee(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()
                    ));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            User employee = userRepository.findUserByEmailAndRole(loginRequest.getEmail(), Role.EMPLOYEE)
                    .orElseThrow(()->new EmployeeNotFoundException( "Employee not found"));
            var employeeToken = jwtService.generateJwtToken(employee);
            var name = employee.getFirstName() + " " + employee.getLastName();
            return new AuthenticationResponse(employeeToken, name, employee.getRole());
        } catch (Exception e) {
            log.error(format(ERROR_MSG, e.getLocalizedMessage()));
            throw new UnAuthorizedException("Invalid email/or password", INVALID_CREDENTIALS);
        }
    }


    @Transactional
    public AuthenticationResponse loginAdmin(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()
                    ));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            User admin = userRepository.findUserByEmailAndRole(loginRequest.getEmail(), Role.ADMIN)
                    .orElseThrow(()->new EmployeeNotFoundException( "Admin not found"));
            var adminToken = jwtService.generateJwtToken(admin);
            var name = admin.getFirstName() + " " + admin.getLastName();
            return new AuthenticationResponse(adminToken, name, admin.getRole());
        } catch (Exception e) {
            log.error(format(ERROR_MSG, e.getLocalizedMessage()));
            throw new UnAuthorizedException("Invalid email/or password", INVALID_CREDENTIALS);
        }
    }
}