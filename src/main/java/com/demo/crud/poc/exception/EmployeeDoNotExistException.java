package com.demo.crud.poc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by muhdk on 25/06/2018.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmployeeDoNotExistException extends Exception {


    public EmployeeDoNotExistException(String message) {
        super(message);
    }

}
