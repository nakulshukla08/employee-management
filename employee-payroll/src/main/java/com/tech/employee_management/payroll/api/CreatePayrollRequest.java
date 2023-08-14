package com.tech.employee_management.payroll.api;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreatePayrollRequest {

    private String employeeId;

    private String grossSalary;
}
