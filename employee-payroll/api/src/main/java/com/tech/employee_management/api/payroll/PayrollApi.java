package com.tech.employee_management.api.payroll;


public interface PayrollApi {

    GetPayrollResponse getPayroll(GetPayrollRequest request);

    void createPayroll(CreatePayrollRequest request);

}
