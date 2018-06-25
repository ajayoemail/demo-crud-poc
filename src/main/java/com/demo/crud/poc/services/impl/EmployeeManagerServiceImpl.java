package com.demo.crud.poc.services.impl;

import com.demo.crud.poc.exception.EmployeeDoNotExistException;
import com.demo.crud.poc.exception.InvalidEmployeeRequestException;
import com.demo.crud.poc.model.Employee;
import com.demo.crud.poc.repository.IEmployeeRepository;
import com.demo.crud.poc.services.IEmployeeManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by muhdk on 25/06/2018.
 */
@Service("employeeManagerServiceImpl")
public class EmployeeManagerServiceImpl implements IEmployeeManagerService {

    private final EmployeeValidationServiceImpl validationService;
    private final IEmployeeRepository employeeRepository;

    public EmployeeManagerServiceImpl(final EmployeeValidationServiceImpl validationService, IEmployeeRepository employeeRepo) {

        this.validationService = validationService;
        this.employeeRepository = employeeRepo;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeManagerServiceImpl.class);


    @Override
    public Employee add(final Employee employee) throws InvalidEmployeeRequestException {


        validationService.validateEmployee(employee);

        final Employee employeeAdded = employeeRepository.save(employee);

        return employeeAdded;


    }

    @Override
    public Employee update(final Long id, final Employee employee) throws InvalidEmployeeRequestException, EmployeeDoNotExistException {


        validationService.validateEmployee(employee);

        final Optional<Employee> existingEmployee = employeeRepository.findById(id);

        if (existingEmployee.isPresent()) {

            employee.setId(id);
            final Employee save = employeeRepository.save(employee);
            return save;

        } else {
            throw new EmployeeDoNotExistException("Employee with Id:" + id + " do not exist in the database.");
        }


    }
}
