package handler;

import com.michael.libertybank.dto.APIResponse;
import com.michael.libertybank.dto.ErrorDTO;
import com.michael.libertybank.exception.UserNotFoundException;
import com.michael.libertybank.exception.UserServiceBusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class UserServiceExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public APIResponse<?> handleMethodArgumentException(MethodArgumentNotValidException exception) {
        APIResponse<?> serviceResponse = new APIResponse<>();
        List<ErrorDTO> errors = new ArrayList<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(error -> {
                    ErrorDTO errorDTO = new ErrorDTO(error.getField(), error.getDefaultMessage());
                    errors.add(errorDTO);
                });
        serviceResponse.setStatus("FAILED");
        serviceResponse.setErrors(errors);
        return serviceResponse;
    }

    @ExceptionHandler(UserServiceBusinessException.class)
    public APIResponse<?> handleServiceException(UserServiceBusinessException exception) {
        APIResponse<?> serviceResponse = new APIResponse<>();
        serviceResponse.setStatus("FAILED");
        serviceResponse.setErrors(Collections.singletonList(new ErrorDTO("", exception.getMessage())));
        return serviceResponse;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public APIResponse<?> handleUserNotFoundException(UserNotFoundException exception) {
        APIResponse<?> serviceResponse = new APIResponse<>();
        serviceResponse.setStatus("FAILED");
        serviceResponse.setErrors(Collections.singletonList(new ErrorDTO("", exception.getMessage())));
        return serviceResponse;
    }
}
