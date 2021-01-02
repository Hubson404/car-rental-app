package org.hubson404.carrentalapp.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(InsufficientDataException.class)
    public void InsufficientDataExceptionHandler(InsufficientDataException exception) {
        log.error(exception.getMessage());
    }
}
