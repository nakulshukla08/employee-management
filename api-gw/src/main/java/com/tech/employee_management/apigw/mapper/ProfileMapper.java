package com.tech.employee_management.apigw.mapper;

import com.tech.employee_management.apigw.model.ProfileApiResponse;
import com.tech.employee_management.profile.api.ProfileResponse;

public class ProfileMapper implements Mapper<ProfileResponse, ProfileApiResponse>{
    @Override
    public ProfileApiResponse map(ProfileResponse input) {
        ProfileApiResponse response =  new ProfileApiResponse();
        response.setDepartmentName(input.getDepartmentName());
        response.setFirstName(input.getFirstName());
        response.setEmployeeId(input.getEmployeeId());
        response.setLastName(input.getLastName());
        return response;
    }
}
