package com.demo.crud.poc.exception;

/**
 * Created by muhdk on 25/06/2018.
 */
public class EmployeeDoNotExistException extends Exception {

    public EmployeeDoNotExistException() {
        super();
    }

    public EmployeeDoNotExistException(String message) {
        super(message);
    }

    public EmployeeDoNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
