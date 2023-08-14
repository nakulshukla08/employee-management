package com.tech.employee_management.payroll.domain;

import lombok.Data;

@Data
public class PayrollResponse {

    private String employeeId;

    private Payroll payroll;

}
