package com.tech.employee_management.api.payroll;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreatePayrollRequest {

    private String employeeId;

    private String grossSalary;
}
