package com.tech.employee_management.api.payroll;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetPayrollRequest {

    private String employeeId;

}
