package com.tech.employee_management.web.mapper;

import com.tech.employee_management.api.profile.ProfileResponse;
import com.tech.employee_management.web.model.ProfileApiResponse;

public class ProfileMapper implements Mapper<ProfileResponse, ProfileApiResponse>{
    @Override
    public ProfileApiResponse map(ProfileResponse input) {
        ProfileApiResponse response =  new ProfileApiResponse();
        response.setDepartmentName(input.getDepartmentName());
        response.setFirstName(input.getFirstName());
        response.setEmployeeId(input.getEmployeeId());
        response.setLastName(input.getLastName());
        if(input.getPayroll() != null){
            response.setSalary(input.getPayroll().getSalaryAmount());
        }
        return response;
    }
}
