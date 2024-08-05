package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompensationServiceImpl implements CompensationService {

  private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private CompensationRepository compensationRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Compensation create(Compensation compensation) {
      LOG.debug("Creating Compensation [{}]", compensation);
      //The compensation is cross referenced to the list of existing employees to see which employee it is associated with
      Employee employee = employeeRepository.findByEmployeeId(compensation.getEmployee().getEmployeeId());
      //That compensation object is then given a reference to the associated employee
      compensation.setEmployee(employee);
      //Once the association is complete, the compensation object is stored
      compensationRepository.insert(compensation);

      return compensation;
    }

    @Override
    public Compensation read(String id) {
      LOG.debug("Creating Compensation for Employee with id [{}]", id);
      //The ID provided is cross referenced to the existing Employee Repository to provide the Employee object
      Employee employee = employeeRepository.findByEmployeeId(id);
      //That employee object is then utilized to find the compensation by employee in the Compensation Repository
      Compensation compensation = compensationRepository.findCompensationByEmployee(employee);
      
      if (compensation == null) {
        throw new RuntimeException("Invalid employeeId: " + id);
      }

      return compensation;
    }
}
