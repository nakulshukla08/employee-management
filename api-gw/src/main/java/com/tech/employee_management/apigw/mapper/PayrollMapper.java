package com.tech.employee_management.apigw.mapper;

import com.tech.employee_management.apigw.model.PayrollApiResponse;
import com.tech.employee_management.payroll.domain.PayrollResponse;

public class PayrollMapper implements Mapper<PayrollResponse, PayrollApiResponse>{
    @Override
    public PayrollApiResponse map(PayrollResponse input) {
        return new PayrollApiResponse();
    }
}
