package com.tech.employee_management.apigw.mapper;

import com.tech.employee_management.apigw.model.PayrollApiResponse;

public interface Mapper<I,R> {

    R map (I input);
}
