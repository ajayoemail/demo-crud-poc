package com.demo.crud.poc.services.impl;

import com.demo.crud.poc.exception.InvalidEmployeeRequestException;
import com.demo.crud.poc.model.Employee;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * Created by muhdk on 25/06/2018.
 */
@Service
public class EmployeeValidationServiceImpl {

    public void validateEmployee(final Employee employee) throws InvalidEmployeeRequestException {
        try {
            notNull(employee, "Employee cannot be null");
            notNull(employee.getName(), "Employee name is required");
            notNull(employee.getAddress(), "Employee address is required");
            notNull(employee.getDateJoined(), "Employee Date of Joining is required");
        } catch (Exception e) {
            throw new InvalidEmployeeRequestException("All the employee details are required", e);
        }

    }


}
