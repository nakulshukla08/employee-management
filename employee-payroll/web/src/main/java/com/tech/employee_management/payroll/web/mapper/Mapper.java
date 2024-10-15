package com.tech.employee_management.payroll.web.mapper;

public interface Mapper<I,R> {

    R map (I input);

    I reverseMap (R input);
}
