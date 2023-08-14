package com.tech.employee_management.profile.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProfileResponse {
    private String employeeId;
    private String firstName;
    private String lastName;
    private String departmentName;
}
