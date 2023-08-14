package com.tech.employee_management.profile.api;


import com.tech.employee_management.profile.domain.EmployeeProfile;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProfileRequest {

    private EmployeeProfile profile;

}
