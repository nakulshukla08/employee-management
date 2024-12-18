package com.tech.employee_management.profile.web.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;

@Builder
@JsonDeserialize
@Data
public class ProfileApiResponse {

    private String employeeId;
    private String firstName;
    private String lastName;
    private String departmentName;
    private String salary;
}
