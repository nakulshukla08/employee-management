package com.tech.employee_management.api.profile;

import com.tech.employee_management.domain.payroll.Payroll;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProfileResponse {
    private String employeeId;
    private String firstName;
    private String lastName;
    private String departmentName;
    private Payroll payroll;
}
