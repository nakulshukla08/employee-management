package com.tech.employee_management.payroll.impl;

import com.tech.employee_management.api.payroll.GetPayrollResponse;
import com.tech.employee_management.api.payroll.PayrollApi;
import com.tech.employee_management.api.payroll.GetPayrollRequest;
import com.tech.employee_management.api.payroll.CreatePayrollRequest;
import com.tech.employee_management.domain.payroll.Payroll;
import com.tech.employee_management.payroll.entities.Salary;
import com.tech.employee_management.payroll.repo.SalaryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
public class PayrollApiImpl implements PayrollApi {

    private SalaryRepository salaryRepository;
    @Override
    public GetPayrollResponse getPayroll(GetPayrollRequest request) {

        Optional<Salary> salary = salaryRepository.findByEmployeeId(getInternalId(request.getEmployeeId()));
        if(salary.isEmpty()){
            throw new EntityNotFoundException(String.format("Salary not found for employee : %s", request.getEmployeeId()));
        }
        log.info("salary found : {}", salary.get());
        GetPayrollResponse response =  new GetPayrollResponse();
        Payroll payroll = new Payroll(salary.get().getGrossSalary().toString());
        response.setPayroll(payroll);
        return response;
    }

    @Override
   // @Transactional
    public void createPayroll(CreatePayrollRequest request) {
        Salary salary = new Salary();
        salary.setGrossSalary(new BigDecimal(request.getGrossSalary()));
        salary.setEmployeeId(getInternalId(request.getEmployeeId()));
        salaryRepository.saveAndFlush(salary);
    }

    private Integer getInternalId(String id){
        //Actual publicId to internal ID code goes here
        return Integer.parseInt(id);
    }
}
