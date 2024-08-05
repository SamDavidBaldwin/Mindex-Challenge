package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

  private String employeeUrl; 
  private String compensationUrl;
  private String compensationIDUrl;

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Before
  public void setup() {
      employeeUrl = "http://localhost:" + port + "/employee";
      compensationUrl = "http://localhost:" + port + "/compensation";
      compensationIDUrl = "http://localhost:" + port + "/compensation/{id}";
  }
  
@Test
  public void testCompensationCreationandUpdate() {
    //Follow same structure as the Employee Creation Process
    Employee testEmployee = new Employee();
    testEmployee.setFirstName("John");
    testEmployee.setLastName("Doe");
    testEmployee.setDepartment("Engineering");
    testEmployee.setPosition("Developer");

    Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();

    assertNotNull(createdEmployee.getEmployeeId());
    assertEmployeeEquivalence(testEmployee, createdEmployee);

    //Create a compensation object
    Compensation testcompensation = new Compensation();
    testcompensation.setEffectiveDate(LocalDate.now());
    testcompensation.setEmployee(createdEmployee);
    testcompensation.setSalary(100);

    //Create checks for Compensation
    Compensation createdCompensation = restTemplate.postForEntity(compensationUrl, testcompensation, Compensation.class).getBody();
    assertNotNull(createdCompensation);
    assertNotNull(createdCompensation.getEmployee().getEmployeeId());
    assertCompensationEquivalence(testcompensation, createdCompensation);

    //Read Checks for Compensation
    Compensation readCompensation = restTemplate.getForEntity(compensationIDUrl, Compensation.class, createdCompensation.getEmployee().getEmployeeId()).getBody();
    assertEquals(createdEmployee.getEmployeeId(), readCompensation.getEmployee().getEmployeeId());
    assertCompensationEquivalence(createdCompensation, readCompensation);
  }

  private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
    assertEquals(expected.getFirstName(), actual.getFirstName());
    assertEquals(expected.getLastName(), actual.getLastName());
    assertEquals(expected.getDepartment(), actual.getDepartment());
    assertEquals(expected.getPosition(), actual.getPosition());
  }

  private static void assertCompensationEquivalence(Compensation expected, Compensation actual){
    assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
    assertEmployeeEquivalence(expected.getEmployee(),actual.getEmployee());
    assertEquals(expected.getSalary(), actual.getSalary());
  }
    


}
