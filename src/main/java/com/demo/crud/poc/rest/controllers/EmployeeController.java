package com.demo.crud.poc.rest.controllers;

import com.demo.crud.poc.exception.EmployeeAlreadyExistsException;
import com.demo.crud.poc.exception.EmployeeDoNotExistException;
import com.demo.crud.poc.exception.InvalidEmployeeRequestException;
import com.demo.crud.poc.model.Employee;
import com.demo.crud.poc.services.IEmployeeManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Api(value = "EmplyeeManagement", description = "CRUD Demo for Employee")
public class EmployeeController {

    private IEmployeeManagerService employeeManagerService;

    @Autowired
    public void setEmployeeManagerService(IEmployeeManagerService employeeManagerService) {
        this.employeeManagerService = employeeManagerService;
    }


    @RequestMapping(value = "/employees/", method = RequestMethod.POST)
    @ApiOperation(value = "Add a new Employee", response = Employee.class)
    public ResponseEntity create(@RequestBody Employee employee) throws InvalidEmployeeRequestException, EmployeeAlreadyExistsException {
        final Employee employeeSaved = employeeManagerService.add(employee);

        return new ResponseEntity(employeeSaved, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/employees", method = RequestMethod.GET)
    @ApiOperation(value = "List all the employees", response = Employee.class)
    public ResponseEntity allEmployees() {
        final List<Employee> employees = employeeManagerService.findAll();

        return new ResponseEntity(employees, HttpStatus.OK);
    }

    @RequestMapping(value = "/employees/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Get employee by id", response = Employee.class)
    public ResponseEntity employeeById(@PathVariable("id") final Long id) throws EmployeeDoNotExistException {
        final Optional<Employee> employee = employeeManagerService.find(id);

        if (employee.isPresent()) {
            return new ResponseEntity(employee, HttpStatus.OK);

        }


        throw new EmployeeDoNotExistException("Employee with id " + id + " do not exist");
    }

    @RequestMapping(value = "/employees/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "Update Existing employee", response = Employee.class)
    public ResponseEntity update(@PathVariable("id") final Long employeeId, @RequestBody Employee employee) throws EmployeeDoNotExistException, InvalidEmployeeRequestException {
        final Employee employeeSaved = employeeManagerService.update(employeeId, employee);

        return new ResponseEntity(employeeSaved, HttpStatus.OK);
    }

    @RequestMapping(value = "/employees/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete Existing employee", response = Employee.class)
    public ResponseEntity delete(@PathVariable("id") final Long employeeId) throws EmployeeDoNotExistException {
        employeeManagerService.delete(employeeId);

        return new ResponseEntity("Employee deleted", HttpStatus.OK);
    }
}

   