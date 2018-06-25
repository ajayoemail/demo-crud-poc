package com.demo.crud.poc.exception;

/**
 * Created by muhdk on 25/06/2018.
 */
public class InvalidEmployeeRequestException extends Exception {


    public InvalidEmployeeRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
