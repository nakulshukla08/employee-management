package com.tech.employee_management.web.mapper;

import com.tech.employee_management.api.payroll.GetPayrollResponse;
import com.tech.employee_management.web.model.PayrollApiResponse;


public class PayrollMapper implements Mapper<GetPayrollResponse, PayrollApiResponse>{
    @Override
    public PayrollApiResponse map(GetPayrollResponse input) {
        return new PayrollApiResponse();
    }
}
