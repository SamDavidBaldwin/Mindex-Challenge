package com.mindex.challenge.dao;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;

import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface CompensationRepository extends MongoRepository<Compensation, String> {
  //The only query we need is to get the Employee object to parse the existing Compensation Repository object
  //Accessing by employee means we don't have to parse through multiple Compensation objects that are the same in the case where multiple individuals who are compensated equally had the same effective date
  Compensation findCompensationByEmployee(Employee employee);
}
