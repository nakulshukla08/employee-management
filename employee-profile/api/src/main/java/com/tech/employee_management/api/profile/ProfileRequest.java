package com.tech.employee_management.api.profile;


import com.tech.employee_management.domain.profile.EmployeeProfile;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProfileRequest {

    private EmployeeProfile profile;
    private boolean includePayroll;

}
