package com.demo.crud.poc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by muhdk on 26/06/2018.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmployeeAlreadyExistsException extends Exception {


    public EmployeeAlreadyExistsException(String message) {
        super(message);
    }
}
