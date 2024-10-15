package com.tech.employee_management.profile.web.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@JsonSerialize
@Builder
@Getter
@ToString
public class CreateProfile {

    private String firstName;
    private String lastName;
    private String departmentName;
    private String salary;
}
