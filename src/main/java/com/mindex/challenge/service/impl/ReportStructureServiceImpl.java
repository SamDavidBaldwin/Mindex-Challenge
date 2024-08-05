package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportStructureServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;
     /**
     * Given that the reports are calculated at the time of a call, there is no need for an update or create
     * An update would be a transient way of demonstrating the existence of direct reports, but given it isn't persistant, the data wouldn't be saved anywhere
     * A create is also pointless, because creating a structure of direct reports would also be transient, and as it wouldn't be saved anywhere would be useless
     * The reports only existy at the time of a read call and therefore only a read method is necessary   
     **/
    @Override
    public ReportingStructure read(String id) {
        LOG.debug("Reading Employee [{}] ReportingStructure", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        /**Calculate reports 
         * A structure similar to a BFS or DFS can be used for traversing the report structure similar to a tree
         * A recursive structure could also be used and it generally depends on the structure of the reports 
         * BFS is better for more shallow or wide trees, DFS is better for thinner, deeper trees, but all have O(n) time
         * Since we can't know the depth or width of the tree initially based on the structure, recursive is probably the safest bet
         **/
        ReportingStructure reports = ReportingStructure.recursiveHelper(employee, employeeRepository);
        return reports;
    }
   //A benefit I realized after writing the solution is that BFS could be used to report the number of reports by level, such as showing the number of direct, first level, second level reports
}
