package com.tech.employee_management.payroll.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PayrollRequest {

    private String employeeId;

}
