package com.tech.employee_management.events.profile;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@ToString
public class EmployeeState {

    private String employeeId;
    private String firstName;
    private String lastName;
    private String departmentName;
    private String salary;
}
