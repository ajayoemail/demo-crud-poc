package com.demo.crud.poc.services.impl;

import com.demo.crud.poc.exception.EmployeeDoNotExistException;
import com.demo.crud.poc.exception.InvalidEmployeeRequestException;
import com.demo.crud.poc.model.Employee;
import com.demo.crud.poc.services.IEmployeeManagerService;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.Optional;

/**
 * Created by muhdk on 25/06/2018.
 */
@RunWith(SpringRunner.class)
@Profile("TEST")
@SpringBootTest
public class EmployeeManagerServiceImplTest {

    private static final Employee DUMMY_EMPLOYEE = new PodamFactoryImpl().manufacturePojo(Employee.class);
    @Autowired
    private IEmployeeManagerService employeeManagerService;

    @Test(expected = InvalidEmployeeRequestException.class)
    public void test_employee_null_object() throws InvalidEmployeeRequestException {
        employeeManagerService.add(null);

    }

    @Test(expected = InvalidEmployeeRequestException.class)
    public void test_employee_name_null() throws InvalidEmployeeRequestException {
        employeeManagerService.add(new Employee());

    }

    //add employee then fetch the employee
    //validate the employee
    @Test
    public void test_add_employee_get_employee() {


        try {
            final Employee employee = employeeManagerService.add(DUMMY_EMPLOYEE);
            Assert.assertNotNull(employee);
            Assert.assertNotNull(employee.getId() != null);
            Assert.assertThat(employee.getName(), Matchers.equalTo(DUMMY_EMPLOYEE.getName()));
            Assert.assertThat(employee.getAddress(), Matchers.equalTo(DUMMY_EMPLOYEE.getAddress()));
            Assert.assertThat(employee.getDateJoined(), Matchers.equalTo(DUMMY_EMPLOYEE.getDateJoined()));

        } catch (InvalidEmployeeRequestException e) {
            Assert.fail("Exception should not be thrown");
        }
    }

    // add employee and update
    @Test
    public void test_add_update() {


        try {
            final Employee employee = employeeManagerService.add(DUMMY_EMPLOYEE);
            Assert.assertNotNull(employee);
            Assert.assertTrue(employee.getId() != null);

            employee.setName("NEW NAME");

            employeeManagerService.update(employee.getId(), employee);

            final Optional<Employee> existingEmployeeUpdated = employeeManagerService.find(employee.getId());

            Assert.assertNotNull(existingEmployeeUpdated.get());
            Assert.assertThat(existingEmployeeUpdated.get().getName(), Matchers.equalTo(employee.getName()));


        } catch (Exception e) {
            Assert.fail("Exception should not be thrown");
        }
    }


    //add employee then delete
    @Test
    public void test_add_delete() {


        try {
            final Employee employee = employeeManagerService.add(DUMMY_EMPLOYEE);
            Assert.assertNotNull(employee);
            Assert.assertTrue(employee.getId() != null);


            employeeManagerService.delete(employee.getId());

            final Optional<Employee> existingEmployeeUpdated = employeeManagerService.find(employee.getId());

            //employee should be deleted from the database
            Assert.assertThat(existingEmployeeUpdated.isPresent(), Matchers.equalTo(false));


        } catch (Exception e) {
            Assert.fail("Exception should not be thrown");
        }
    }


    @Test(expected = EmployeeDoNotExistException.class)
    public void test_delete_non_exist_employee() throws EmployeeDoNotExistException {

        employeeManagerService.delete(-1111111L);
    }

    @Test(expected = EmployeeDoNotExistException.class)
    public void test_update_non_exist_employee() throws EmployeeDoNotExistException, InvalidEmployeeRequestException {

        employeeManagerService.update(-1111111L, DUMMY_EMPLOYEE);
    }

}
