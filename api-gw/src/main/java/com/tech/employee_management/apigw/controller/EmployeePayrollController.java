package com.tech.employee_management.apigw.controller;


import com.tech.employee_management.apigw.annotation.ConditionalController;
import com.tech.employee_management.apigw.mapper.Mapper;
import com.tech.employee_management.apigw.model.CreatePayroll;
import com.tech.employee_management.apigw.model.PayrollApiResponse;
import com.tech.employee_management.payroll.api.CreatePayrollRequest;
import com.tech.employee_management.payroll.api.PayrollApi;
import com.tech.employee_management.payroll.domain.PayrollRequest;
import com.tech.employee_management.payroll.domain.PayrollResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
@ConditionalController(moduleName = "employee-payroll")
public class EmployeePayrollController {

    private PayrollApi payrollApi;
    private Mapper<PayrollResponse, PayrollApiResponse> payrollMapper;

    @RequestMapping(value = "/payroll/{employeeId}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PayrollApiResponse> getEmployeePayroll(@PathVariable  String employeeId){
        log.info("Incoming request for getting payroll, employeeId: {}", employeeId);
        return ResponseEntity.ok(
                    payrollMapper.map(
                        payrollApi.getPayroll(PayrollRequest.builder()
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
