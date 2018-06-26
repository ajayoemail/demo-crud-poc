package com.demo.crud.poc.config;

import com.demo.crud.poc.exception.EmployeeAlreadyExistsException;
import com.demo.crud.poc.exception.EmployeeDoNotExistException;
import com.demo.crud.poc.exception.InvalidEmployeeRequestException;
import com.demo.crud.poc.model.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({InvalidEmployeeRequestException.class, EmployeeAlreadyExistsException.class})
    public final ResponseEntity<ErrorDetails> handleInvalidException(Exception ex, WebRequest request) {

        return new ResponseEntity<>(getErrorDetails(ex, request), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({EmployeeDoNotExistException.class})
    public final ResponseEntity<ErrorDetails> handleEmployeeDoNotExist(Exception ex, WebRequest request) {

        return new ResponseEntity<>(getErrorDetails(ex, request), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler({Exception.class})
    public final ResponseEntity<ErrorDetails> handleAllException(Exception ex, WebRequest request) {

        return new ResponseEntity<>(getErrorDetails(ex, request), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorDetails getErrorDetails(Exception ex, WebRequest request) {
        return new ErrorDetails(new Date(), ex.getMessage(),
                request.getDescription(false));
    }
}