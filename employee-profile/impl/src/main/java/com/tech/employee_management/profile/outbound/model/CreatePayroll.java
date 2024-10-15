package com.tech.employee_management.profile.outbound.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;

@JsonSerialize
@Builder
@Getter
public class CreatePayroll {

    private String employeeId;
    private String grossSalary;
}
