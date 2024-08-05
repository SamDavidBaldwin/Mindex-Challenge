package com.mindex.challenge.data;

import java.time.LocalDate;

public class Compensation {
  private int salary;
  private LocalDate effectiveDate;
  private Employee employee;

  public Compensation(){
  }

  /*
   * It makes slightly more sense to associate the Employee with the Compensation for a couple reasons. A Compensation object will require an employee
   * to exist in order for the compensation to be assigned to an individual, and the same can not be said in opposite given the provided implementation.
   * This OTO relationship results in it being more practical to have the compensation associated with the Employee and to hold a reference due to the 
   * specificity of compensation. Additionally, given the specification given by the problem statement, that "endpoints should persist and fetch 
   * 'Compensation' data for a specific employee", implying that the common access of Compensation objects is direct, and not through a given employee.
   * This isn't to say that the opposite is impossible or incorrect, but rather, reasoning for the choices that I made. To do the opposite, an adjustment 
   * to the Employee repository would need to be made, and the Compensation class would likely have an employee reference.
   */

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public String getEmployeeID(Employee employee){
    return this.employee.getEmployeeId();
  }
  
  public LocalDate getEffectiveDate() {
    return effectiveDate;
  }

  public int getSalary() {
    return salary;
  }

  public void setEffectiveDate(LocalDate effectivDate) {
    this.effectiveDate = effectivDate;
  }
  
  public void setSalary(int salary) {
    this.salary = salary;
  }

}
