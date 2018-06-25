package com.demo.crud.poc.services.impl;

import com.demo.crud.poc.exception.InvalidEmployeeRequestException;
import com.demo.crud.poc.model.Employee;
import com.demo.crud.poc.services.IEmployeeManagerService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Created by muhdk on 25/06/2018.
 */
@RunWith(SpringRunner.class)
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

    @Test
    public void test_add_employee_get_employee() {


        try {
            final Employee employee = employeeManagerService.add(DUMMY_EMPLOYEE);
            Assert.assertNotNull(employee);
            Assert.assertTrue(employee.getId() != null);

        } catch (InvalidEmployeeRequestException e) {
            Assert.fail("Exception should not be thrown");
        }
    }

    @Test
    public void test_add_update() {


        try {
            final Employee employee = employeeManagerService.add(DUMMY_EMPLOYEE);
            Assert.assertNotNull(employee);
            Assert.assertTrue(employee.getId() != null);

            employee.setName("NEW NAME");

            employeeManagerService.update(employee.getId(), employee);



        } catch (Exception e) {
            Assert.fail("Exception should not be thrown");
        }
    }


}
