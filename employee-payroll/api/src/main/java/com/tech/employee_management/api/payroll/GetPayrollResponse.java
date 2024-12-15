package com.tech.employee_management.api.payroll;

import com.tech.employee_management.domain.payroll.Payroll;
import lombok.Data;

@Data
public class GetPayrollResponse {

    private String employeeId;

    private Payroll payroll;

}
