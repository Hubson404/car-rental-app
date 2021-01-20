package org.hubson404.carrentalapp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.hubson404.carrentalapp.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInformation handle(MethodArgumentNotValidException exception) {
        Map<String, List<String>> errorDetails = exception.getFieldErrors()
                .stream()
                .filter(fieldError -> fieldError.getDefaultMessage() != null)
                .collect(Collectors.groupingBy(FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())));
        return new ErrorInformation(errorDetails);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInformation handleIllegalArgumentException(IllegalArgumentException exception) {
        String errorDetails = exception.getMessage();
        return new ErrorInformation(errorDetails);
    }

    @ExceptionHandler(CarReservationNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInformation handleCarReservationNotFoundException(CarReservationNotFoundException exception) {
        String errorDetails = exception.getMessage();
        return new ErrorInformation(errorDetails);
    }

    @ExceptionHandler(CarReturnNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInformation handleCarReturnNotFoundException(CarReturnNotFoundException exception) {
        String errorDetails = exception.getMessage();
        return new ErrorInformation(errorDetails);
    }

    @ExceptionHandler(DepartmentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorInformation DepartmentNotFoundExceptionHandler(DepartmentNotFoundException exception) {
        String errorDetails = exception.getMessage();
        return new ErrorInformation(errorDetails);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorInformation EmployeeNotFoundExceptionHandler(EmployeeNotFoundException exception) {
        String errorDetails = exception.getMessage();
        return new ErrorInformation(errorDetails);
    }

    @ExceptionHandler(AppUserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorInformation AppUserNotFoundExceptionHandler(AppUserNotFoundException exception) {
        String errorDetails = exception.getMessage();
        return new ErrorInformation(errorDetails);
    }

    @ExceptionHandler(CarNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorInformation handleCarNotFoundException(CarNotFoundException exception) {
        String errorDetails = exception.getMessage();
        return new ErrorInformation(errorDetails);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorInformation CustomerNotFoundExceptionHandler(CustomerNotFoundException exception) {
        String errorDetails = exception.getMessage();
        return new ErrorInformation(errorDetails);
    }

    @ExceptionHandler(InsufficientDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInformation InsufficientDataExceptionHandler(InsufficientDataException exception) {
        String errorDetails = exception.getMessage();
        return new ErrorInformation(errorDetails);
    }

    @ExceptionHandler(IllegalEmployeeIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInformation IllegalEmployeeIdExceptionHandler(IllegalEmployeeIdException exception) {
        String errorDetails = exception.getMessage();
        return new ErrorInformation(errorDetails);
    }

}
