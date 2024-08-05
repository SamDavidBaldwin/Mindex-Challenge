package com.mindex.challenge.data;

import com.mindex.challenge.dao.EmployeeRepository;

public class ReportingStructure {
  private int numberOfReports;
  private String employeeID;
  //The list of reports class field is useful for Testing the Reporting Structure, as it allows for the direct comparison of reporting employee ID numbers
  private String[] listOfReports;
  

  public ReportingStructure() {
  }

  public String getEmployeeID() {
    return employeeID;
  }
  
  public void setEmployeeID(String string) {
    this.employeeID = string;
  }

  public int getNumberOfReports() {
    return numberOfReports;
  }

  public void setNumberOfReports(int numberOfReports) {
    this.numberOfReports = numberOfReports;
  }

  public String[] getListOfReports() {
    return listOfReports;
  }

  public void setListOfReports(String[] listOfReports) {
    this.listOfReports = listOfReports;
  }

  public void addEmployeeToReports(String ID) {
    if (listOfReports == null) {
        listOfReports = new String[1];
        listOfReports[0] = ID;
    } else {
        // Create a new array with one more slot
        String[] newListOfReports = new String[listOfReports.length + 1];
        System.arraycopy(listOfReports, 0, newListOfReports, 0, listOfReports.length);
        newListOfReports[listOfReports.length] = ID;
        listOfReports = newListOfReports;
    }
    // Update the number of reports
    numberOfReports = listOfReports.length;
}

  /** 
     * Method to recursively traverse the employee hierarchy 
     * There is almost certainly a way to do this in fewer lines, such as just doing 1 + treeTraverse(employee.getDirectReports) for all employees, and then
     * adding the number at the end, but this is a system that is generally more controlled, and increments by branch rather than only at the end. It also 
     * makes more robust checking easier by allowing for the checking of each individual. It does he the issue of if there are cyclical sections of the reporting
     * structure, but that isn't neccesarily an issue that is likely to arise. 
     * @param employee --> The current employee that is being checked for reporters 
     * @param reoprts --> The persisting ReportStructure that trackes the reporting employees
     */
    public static void treeTraverse(Employee employee, ReportingStructure reports, EmployeeRepository employeeRepository){
        //Avoiding adding the root employee to the ReportingStructure, for each reporting employee, increment the counter and add the reporting ID
        if(employee.getEmployeeId() != reports.getEmployeeID()){
          //Increment the number of reporting employees
          reports.setNumberOfReports(reports.getNumberOfReports() + 1);
          //Append the reporing employees ID to the reporting list
          reports.addEmployeeToReports(employee.getEmployeeId());
        }
        //Access the Employee object of the ID, to avoid referencing issues  
        employee = employeeRepository.findByEmployeeId(employee.getEmployeeId());
        //If that employee has Direct reports, for each of those reportees, traverse the subtree with that employee at the root
        if(employee.getDirectReports() != null){
            for(Employee reportee : employee.getDirectReports()){
                treeTraverse(reportee, reports, employeeRepository);
            }
        }
    }
    
    /**
     * Method to initially call the recursive method for traversing the tree
     * @param employee --> The root employee that is being checked for reporting employees
     * @return reports --> The ReportStructure that contains information about reporting employees
     */
    public static ReportingStructure recursiveHelper(Employee employee, EmployeeRepository employeeRepository){
        ReportingStructure reports = new ReportingStructure();
        reports.setEmployeeID(employee.getEmployeeId());
        treeTraverse(employee, reports, employeeRepository);

        return reports;

    }


}