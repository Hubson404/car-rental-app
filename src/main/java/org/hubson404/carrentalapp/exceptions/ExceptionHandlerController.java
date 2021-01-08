package org.hubson404.carrentalapp.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(InsufficientDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void InsufficientDataExceptionHandler(InsufficientDataException exception) {
        log.error(exception.getMessage());
    }

    @ExceptionHandler(DepartmentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void DepartmentNotFoundExceptionHandler(DepartmentNotFoundException exception) {
        log.error(exception.getMessage());
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void EmployeeNotFoundExceptionHandler(EmployeeNotFoundException exception) {
        log.error(exception.getMessage());
    }

    @ExceptionHandler(IllegalEmployeeIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void IllegalEmployeeIdExceptionHandler(IllegalEmployeeIdException exception) {
        log.error(exception.getMessage());
    }

    @ExceptionHandler(CarNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void CarNotFoundExceptionHandler(CarNotFoundException exception) {
        log.error(exception.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void IllegalArgumentExceptionHandler(IllegalArgumentException exception) {
        log.error(exception.getMessage());
    }
}
