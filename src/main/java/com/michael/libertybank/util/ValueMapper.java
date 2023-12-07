package com.michael.libertybank.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.michael.libertybank.dto.signUp.EmployeeRequestDTO;
import com.michael.libertybank.dto.signUp.EmployeeResponseDTO;
import com.michael.libertybank.dto.signUp.UserRequestDTO;
import com.michael.libertybank.dto.signUp.UserResponseDTO;
import com.michael.libertybank.model.Customer;
import com.michael.libertybank.model.Employee;
import com.michael.libertybank.model.Role;


import java.io.Serializable;
import java.time.LocalDateTime;

public class ValueMapper implements Serializable {
    public static Customer convertToUserEntity(UserRequestDTO userSignUpRequest ){
        var user = new Customer();
        user.setFirstName(userSignUpRequest.getFirstName());
        user.setLastName(userSignUpRequest.getLastName());
        user.setEmail(userSignUpRequest.getEmail());
        user.setPassword(userSignUpRequest.getPassword());
        user.setAddress(userSignUpRequest.getAddress());
        user.setPhoneNumber(userSignUpRequest.getPhoneNumber());
        user.setAccounts(userSignUpRequest.getAccounts());
        user.setRole(Role.USER);
        user.setDateJoined(LocalDateTime.now());
        return user;
    }
    public static UserResponseDTO convertToUserDto(Customer customer){
        var userSignUpResponse = new UserResponseDTO();
        userSignUpResponse.setId(customer.getId());
        userSignUpResponse.setFirstName(customer.getFirstName());
        userSignUpResponse.setLastName(customer.getLastName());
        userSignUpResponse.setEmail(customer.getEmail());
        userSignUpResponse.setPhoneNumber(customer.getPhoneNumber());
        userSignUpResponse.setAccounts(customer.getAccounts());
        userSignUpResponse.setDateJoined(customer.getDateJoined());
        userSignUpResponse.setAddress(customer.getAddress());
        userSignUpResponse.setRole(customer.getRole());
        return userSignUpResponse;
    }

    public static Employee convertToEmployeeEntity(EmployeeRequestDTO employeeSignUpRequest){
        var employee = new Employee();
        employee.setEmployeeFirstName(employeeSignUpRequest.getEmployeeFirstName());
        employee.setEmployeeLastName(employeeSignUpRequest.getEmployeeLastName());
        employee.setUserName(employeeSignUpRequest.getUserName());
        employee.setPassword(employeeSignUpRequest.getPassword());
        // iF I NEEDED THE DROP DOWN FROM THE FRONT END
//        employee.setRole(employeeSignUpRequest.getRole());
      return employee;
    }

    public static EmployeeResponseDTO convertToEmployeeDto (Employee employee){
        var employeeSignUpResponse = new EmployeeResponseDTO();

        employeeSignUpResponse.setId(employee.getId());
        employeeSignUpResponse.setEmployeeFirstName(employee.getEmployeeFirstName());
        employeeSignUpResponse.setEmployeeLastName(employee.getEmployeeLastName());
        employeeSignUpResponse.setUserName(employee.getUserName());
        // automatically setting the role as employee
//        employeeSignUpResponse.setRole(Role.EMPLOYEE);
        employeeSignUpResponse.setRole(employee.getRole());
        // but since the admin extended the user, the employee has to specify if he is an admin or employee
        return employeeSignUpResponse;

    }

    public static String jsonAsString(Object obj){
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
