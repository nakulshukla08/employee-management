package com.tech.employee_management.web.controller;


import com.tech.employee_management.api.payroll.GetPayrollRequest;
import com.tech.employee_management.api.payroll.GetPayrollResponse;
import com.tech.employee_management.api.payroll.CreatePayrollRequest;
import com.tech.employee_management.api.payroll.PayrollApi;
import com.tech.employee_management.web.annotation.FunctionalModule;
import com.tech.employee_management.web.mapper.Mapper;
import com.tech.employee_management.web.model.CreatePayroll;
import com.tech.employee_management.web.model.PayrollApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@RestController
@AllArgsConstructor
@Slf4j
//@FunctionalModule(name = "employee-payroll")
public class EmployeePayrollController {

    private PayrollApi payrollApi;
    private Mapper<GetPayrollResponse, PayrollApiResponse> payrollMapper;

    @RequestMapping(value = "/payroll/{employeeId}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PayrollApiResponse> getEmployeePayroll(@PathVariable  String employeeId){
        log.info("Incoming request for getting payroll, employeeId: {}", employeeId);
        return ResponseEntity.ok(
                    payrollMapper.map(
                        payrollApi.getPayroll(GetPayrollRequest.builder()
                                        .employeeId(employeeId)
                                        .build()
                )));
    }

    @RequestMapping(value = "/payroll/{employeeId}",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatePayroll> createEmployeePayroll(@PathVariable  String employeeId, @RequestBody CreatePayroll createPayroll){
        log.info("Incoming request for creating payroll, employeeId: {}, salary: {}", employeeId, createPayroll.getGrossSalary());


                        payrollApi.createPayroll(
                                CreatePayrollRequest.builder()
                                        .employeeId(employeeId)
                                        .grossSalary(
                                                createPayroll.getGrossSalary()
                                        )
                                        .build());
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

}
