package com.tech.employee_management.profile.outbound.mapper;

public interface Mapper<I,R> {

    R map (I input);

    I reverseMap (R input);
}
