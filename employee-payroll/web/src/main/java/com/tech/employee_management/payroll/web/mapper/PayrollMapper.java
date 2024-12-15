package com.tech.employee_management.payroll.web.mapper;

import com.tech.employee_management.api.payroll.GetPayrollResponse;
import com.tech.employee_management.payroll.web.model.PayrollApiResponse;


public class PayrollMapper implements Mapper<GetPayrollResponse, PayrollApiResponse> {
    @Override
    public PayrollApiResponse map(GetPayrollResponse input) {
        PayrollApiResponse response = null;
        if(input != null){
            response = new PayrollApiResponse();
            response.setEmployeeId(input.getEmployeeId());
            response.setSalary(input.getPayroll().getSalaryAmount());
        }
        return response;
    }

    @Override
    public GetPayrollResponse reverseMap(PayrollApiResponse input) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }
}
