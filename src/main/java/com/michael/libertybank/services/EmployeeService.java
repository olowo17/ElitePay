package com.michael.libertybank.services;


import com.michael.libertybank.dto.signUp.EmployeeRequestDTO;
import com.michael.libertybank.dto.signUp.EmployeeResponseDTO;
import com.michael.libertybank.exception.EmployeeServiceBusinessException;
import com.michael.libertybank.model.Employee;
import com.michael.libertybank.repository.AccountRepository;
import com.michael.libertybank.repository.EmployeeRepository;
import com.michael.libertybank.repository.CustomerRepository;
import com.michael.libertybank.util.ValueMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeeService implements  IEmployeeService{

    private EmployeeRepository employeeRepository;
    private CustomerRepository userRepository;
    private AccountRepository accountRepository;
    @Override
    public EmployeeResponseDTO registerEmployee(EmployeeRequestDTO employeeSignUpRequest) throws
            EmployeeServiceBusinessException {
        EmployeeResponseDTO employeeResponseDto;

        try{
            log.info("EmployeeService: registerNewEmployee execution started");
            var employee = ValueMapper.convertToEmployeeEntity(employeeSignUpRequest);
            log.debug("CustomerService: register New Employee request parameter {}",
                    ValueMapper.jsonAsString(employeeSignUpRequest));

            Employee employeeResults = employeeRepository.save(employee);
            employeeResponseDto = ValueMapper.convertToEmployeeDto(employeeResults);
            log.debug("CustomerService: register new employee received response from Database {}",
                    ValueMapper.jsonAsString(employeeSignUpRequest));
        }catch (Exception e){
            log.error("Exception Occurred wile persisting user to database, Exception message {}", e.getMessage());
            throw new EmployeeServiceBusinessException("Exception occurred wile creating a new user");
        }

        log.info("CustomerService:createNewUser execution ended.");
        return employeeResponseDto;
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployees() throws EmployeeServiceBusinessException{
        List<EmployeeResponseDTO> employeeResponseDTOS = null;
        try {
            log.info("ProductService:getProducts execution started.");

            List<Employee> employeeList = employeeRepository.findAll();

            if (!employeeList.isEmpty()) {

                employeeResponseDTOS = employeeList.stream()
                        .map(ValueMapper::convertToEmployeeDto)
                        .collect(Collectors.toList());
            } else {
                employeeResponseDTOS = Collections.emptyList();
            }
        }
        catch (Exception ex) {
            log.error("Exception occurred while retrieving products from database , Exception message {}", ex.getMessage());
            throw new EmployeeServiceBusinessException("Exception occurred while fetch all products from Database");
        }

        log.info("ProductService:getProducts execution ended.");
        return employeeResponseDTOS;
    }

}
