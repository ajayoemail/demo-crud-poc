package com.demo.crud.poc.repository;

import com.demo.crud.poc.model.Employee;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by muhdk on 25/06/2018.
 */
public interface IEmployeeRepository extends CrudRepository<Employee, Long> {

    
}
