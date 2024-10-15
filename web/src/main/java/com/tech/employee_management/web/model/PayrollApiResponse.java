package com.tech.employee_management.web.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tech.employee_management.domain.payroll.Payroll;
import lombok.Data;

@Data
@JsonDeserialize
public class PayrollApiResponse {

    private String employeeId;

    private String salary;
}
