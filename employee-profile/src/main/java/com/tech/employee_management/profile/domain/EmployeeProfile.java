package com.tech.employee_management.profile.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EmployeeProfile {

    private String employeeId;
    private String firstName;
    private String lastName;
    private String departmentName;
    private String salary;
}
