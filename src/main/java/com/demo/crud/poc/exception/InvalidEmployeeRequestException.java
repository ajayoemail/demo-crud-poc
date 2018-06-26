package com.demo.crud.poc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by muhdk on 25/06/2018.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidEmployeeRequestException extends Exception {


    public InvalidEmployeeRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
