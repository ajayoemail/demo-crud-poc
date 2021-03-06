package com.demo.crud.poc.services.impl;

import com.demo.crud.poc.exception.EmployeeAlreadyExistsException;
import com.demo.crud.poc.exception.EmployeeDoNotExistException;
import com.demo.crud.poc.exception.InvalidEmployeeRequestException;
import com.demo.crud.poc.model.Employee;
import com.demo.crud.poc.repository.IEmployeeRepository;
import com.demo.crud.poc.services.IEmployeeManagerService;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by muhdk on 25/06/2018.
 */
@Service("employeeManagerServiceImpl")
public class EmployeeManagerServiceImpl implements IEmployeeManagerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeManagerServiceImpl.class);


    private final EmployeeValidationServiceImpl validationService;
    private final IEmployeeRepository employeeRepository;

    public EmployeeManagerServiceImpl(final EmployeeValidationServiceImpl validationService, IEmployeeRepository employeeRepo) {

        this.validationService = validationService;
        this.employeeRepository = employeeRepo;
    }


    /**
     * add a new employe
     * @param employee
     * @return
     * @throws InvalidEmployeeRequestException if any of the mandatory fields are missing
     * @throws EmployeeAlreadyExistsException  if employee exists with same name
     */
    @Override
    public Employee add(final Employee employee) throws InvalidEmployeeRequestException, EmployeeAlreadyExistsException {


        LOGGER.info("Adding Employee {}", employee);
        validationService.validateEmployee(employee);

        LOGGER.info("Checking if employee exists with same name");

        final Optional<Employee> existingEmployee = employeeRepository.findByName(employee.getName());

        if (existingEmployee.isPresent()) {

            throw new EmployeeAlreadyExistsException("Employee with the name already in the database");
        }
        final Employee employeeAdded = employeeRepository.save(employee);

        LOGGER.info("Employee was added succesfully {} ", employee);

        return employeeAdded;


    }

    @Override
    public Employee update(final Long id, final Employee employee) throws InvalidEmployeeRequestException, EmployeeDoNotExistException {

        LOGGER.info("Updating Employee {} with id {}", employee, id);
        validationService.validateEmployee(employee);

        final Optional<Employee> existingEmployee = employeeRepository.findById(id);

        if (existingEmployee.isPresent()) {
            LOGGER.info("Employee exists");
            employee.setId(id);
            final Employee savedEmployee = employeeRepository.save(employee);

            LOGGER.info("Employee was successfully updated {}", savedEmployee);
            return savedEmployee;

        } else {
            LOGGER.error("Employee with ID {} do not exists", id);
            throw new EmployeeDoNotExistException("Employee with Id:" + id + " do not exist in the database.");
        }


    }

    @Override
    public boolean delete(final Long id) throws EmployeeDoNotExistException {

        LOGGER.info("deleting Employee with id {}", id);

        final Optional<Employee> existingEmployee = employeeRepository.findById(id);

        if (existingEmployee.isPresent()) {

            employeeRepository.delete(existingEmployee.get());
            return true;
        } else {
            LOGGER.error("Employee with ID {} do not exists", id);
            throw new EmployeeDoNotExistException("Employee with Id:" + id + " do not exist in the database.");
        }


    }

    @Override
    public Optional<Employee> find(final Long id) {

        LOGGER.info("Finding Employee with the id {}", id);
        final Optional<Employee> employeeWithId = employeeRepository.findById(id);

        employeeWithId.ifPresent(e -> LOGGER.info("employee found {} ", e));

        return employeeWithId;
    }

    @Override
    public List<Employee> findAll() {

        return Lists.newArrayList(employeeRepository.findAll());
    }
}
