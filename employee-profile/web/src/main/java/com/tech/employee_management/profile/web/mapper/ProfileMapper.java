package com.tech.employee_management.profile.web.mapper;

import com.tech.employee_management.api.profile.ProfileResponse;
import com.tech.employee_management.profile.web.model.ProfileApiResponse;

import java.util.Objects;

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

    @Override
    public ProfileResponse reverseMap(ProfileApiResponse input) {

        Objects.requireNonNull(input);
        return ProfileResponse.builder()
                .employeeId(input.getEmployeeId())
                .departmentName(input.getDepartmentName())
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .build();

    }
}
