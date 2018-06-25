package com.demo.crud.poc.services;

import com.demo.crud.poc.exception.EmployeeDoNotExistException;
import com.demo.crud.poc.exception.InvalidEmployeeRequestException;
import com.demo.crud.poc.model.Employee;

import java.util.Optional;

/**
 * Created by muhdk on 25/06/2018.
 */
public interface IEmployeeManagerService {
    Employee add(Employee employee) throws InvalidEmployeeRequestException;

    Employee update(Long id, Employee employee) throws InvalidEmployeeRequestException, EmployeeDoNotExistException;

    boolean delete(Long id) throws EmployeeDoNotExistException;

    Optional<Employee> find(Long id);
}
