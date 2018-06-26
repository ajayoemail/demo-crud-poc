package com.demo.crud.poc.rest.controllers;

import com.demo.crud.poc.exception.EmployeeAlreadyExistsException;
import com.demo.crud.poc.exception.EmployeeDoNotExistException;
import com.demo.crud.poc.exception.InvalidEmployeeRequestException;
import com.demo.crud.poc.model.Employee;
import com.demo.crud.poc.services.IEmployeeManagerService;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.util.Lists;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.when;

/**
 * Created by muhdk on 26/06/2018.
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("JUNIT")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTest {

    private static final PodamFactoryImpl podam = new PodamFactoryImpl();
    @LocalServerPort
    int port;
    @MockBean
    private IEmployeeManagerService managerService;

    @Test
    public void test_get_all_employees() throws InterruptedException {
        when(managerService.findAll()).thenReturn(Lists.newArrayList(createTestEmployees(10)));
        final JsonPath jsonPath = given().port(port).basePath("/api/employees").get("").then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .assertThat()
                .extract().body().jsonPath();
        ;

        List<Employee> employeeList = jsonPath.getList("", Employee.class);


        Assert.assertThat(employeeList.size(), Matchers.equalTo(10));

        employeeList.forEach(e -> {

            Assert.assertNotNull(e.getName());
            Assert.assertNotNull(e.getAddress());
            Assert.assertNotNull(e.getDateJoined());
        });
    }

    @Test
    public void test_add_employee_exception() throws EmployeeAlreadyExistsException, InvalidEmployeeRequestException {

        when(managerService.add(Mockito.any())).thenThrow(InvalidEmployeeRequestException.class);
        final Employee testEmployee = createTestEmployees(1).get(0);

        given().contentType("application/json").port(port).basePath("/api/employees/").with().body(testEmployee).log().all().post()
                .then().assertThat().statusCode(HttpStatus.BAD_REQUEST.value());


    }

    @Test
    public void test_add_employee_valid() throws EmployeeAlreadyExistsException, InvalidEmployeeRequestException {
        final Employee returnEmployee = createTestEmployees(1).get(0);
        when(managerService.add(Mockito.any())).thenReturn(returnEmployee);
        final Employee testEmployee = createTestEmployees(1).get(0);

        final Response post = given().contentType("application/json").port(port).basePath("/api/employees/").with().body(testEmployee).log().all().post();


        post.then().assertThat().statusCode(HttpStatus.CREATED.value());

        final Employee as = post.body().as(Employee.class);
        assertEmployee(returnEmployee, as);


    }

    @Test
    public void test_updated_employee() throws EmployeeDoNotExistException, InvalidEmployeeRequestException {

        final Employee employee = createTestEmployees(1).get(0);
        when(managerService.update(Mockito.anyLong(), Mockito.any())).thenReturn(employee);

        final Employee requestEmployee = createTestEmployees(1).get(0);

        final Response post = given().contentType("application/json").port(port).basePath("/api/employees/1").with().body(requestEmployee).log().all().put();


        post.then().assertThat().statusCode(HttpStatus.OK.value());

        final Employee as = post.body().as(Employee.class);
        assertEmployee(employee, as);


    }

    @Test
    public void test_delete_employee() throws EmployeeDoNotExistException, InvalidEmployeeRequestException {


        when(managerService.delete(Mockito.anyLong())).thenReturn(true);


        final Response post = given().contentType("application/json").port(port).basePath("/api/employees/1").with().log().all().delete();


        post.then().assertThat().statusCode(HttpStatus.OK.value());


    }


    @Test
    public void test_delete_employee_nullpointer() throws EmployeeDoNotExistException, InvalidEmployeeRequestException {


        when(managerService.delete(Mockito.anyLong())).thenThrow(NullPointerException.class);


        final Response post = given().contentType("application/json").port(port).basePath("/api/employees/1").with().log().all().delete();


        post.then().assertThat().statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());


    }

    private void assertEmployee(Employee employee, Employee as) {
        Assert.assertThat(as.getName(), Matchers.equalTo(employee.getName()));
        Assert.assertThat(as.getAddress(), Matchers.equalTo(employee.getAddress()));
        Assert.assertThat(as.getDateJoined(), Matchers.equalTo(employee.getDateJoined()));
    }


    @Test
    public void test_get_employee_valid() {

        final Employee employee = createTestEmployees(1).get(0);
        when(managerService.find(Mockito.anyLong())).thenReturn(Optional.of(employee));

        given().port(port).basePath("/api/employees/" + employee.getId()).get().then().assertThat().statusCode(HttpStatus.OK.value()).log().all();
    }


    @Test
    public void test_get_employee_Invalid() {

        when(managerService.find(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));
        given().port(port).basePath("/api/employees/1").get().then().assertThat().statusCode(HttpStatus.NOT_FOUND.value()).log().all();
    }


    private List<Employee> createTestEmployees(final int count) {


        List<Employee> employees = Lists.newArrayList();
        for (int i = 0; i < count; i++) {

            employees.add(podam.manufacturePojo(Employee.class));
        }

        return employees;
    }
}
