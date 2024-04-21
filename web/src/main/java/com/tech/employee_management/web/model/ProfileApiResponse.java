package com.tech.employee_management.web.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

@Data
@JsonDeserialize
public class ProfileApiResponse {

    private String employeeId;
    private String firstName;
    private String lastName;
    private String departmentName;
    private String salary;
}
