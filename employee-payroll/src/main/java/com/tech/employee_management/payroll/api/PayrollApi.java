package com.tech.employee_management.payroll.api;

import com.tech.employee_management.payroll.domain.PayrollRequest;
import com.tech.employee_management.payroll.domain.PayrollResponse;

public interface PayrollApi {

    PayrollResponse getPayroll(PayrollRequest request);

    void createPayroll(CreatePayrollRequest request);

}
