package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.ReportingStructure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureImplTest {

    @LocalServerPort
    private int port;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testReportingStructure() {
    
        /* 
         * Using the existing employee Repository, we can test to see if the correct number of reportees are given, and if the ID's match to what is expected
         * The structure will be as below
         * 
         *              Emp1
         *             /     \
         *          Emp2     Emp3 
         *                  /    \
         *               Emp3     Emp 4
         */
        String rootEmpID = "16a596ae-edd3-4847-99fe-c4518e82c86f";
        int expectedReportees = 4;

        /**This tests the existing employee repository provided by the problem statement. Given that we know the ID values of each of the employees, we can run the created reporting structure 
        * and check if the correct number of reports are provided, as well as the correct ID values. We could test it by creating new employee objects and passing them through the REST API, but 
        * that ultimately isn't really necessary as we can effectively test the behavior locally. 
        **/

        ReportingStructure reports = ReportingStructure.recursiveHelper(employeeRepository.findByEmployeeId(rootEmpID), employeeRepository);
        assertEquals(reports.getNumberOfReports(), expectedReportees);
        assert Arrays.stream(reports.getListOfReports()).anyMatch("b7839309-3348-463b-a7e3-5de1c168beb3"::equals) : "Array does not contain the expected value" ;
        assert Arrays.stream(reports.getListOfReports()).anyMatch("03aa1462-ffa9-4978-901b-7c001562cf6f"::equals) : "Array does not contain the expected value";
        assert Arrays.stream(reports.getListOfReports()).anyMatch("62c1084e-6e34-4630-93fd-9153afb65309"::equals) : "Array does not contain the expected value";
        assert Arrays.stream(reports.getListOfReports()).anyMatch("c0c2293d-16bd-4603-8e08-638a9d18b22c"::equals) : "Array does not contain the expected value" ;

    }   
}
