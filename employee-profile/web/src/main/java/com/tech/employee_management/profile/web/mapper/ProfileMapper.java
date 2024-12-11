package com.tech.employee_management.profile.web.mapper;

import com.tech.employee_management.api.profile.ProfileResponse;
import com.tech.employee_management.profile.web.model.ProfileApiResponse;

import java.util.Objects;

public class ProfileMapper implements Mapper<ProfileResponse, ProfileApiResponse>{
    @Override
    public ProfileApiResponse map(ProfileResponse input) {
        ProfileApiResponse.ProfileApiResponseBuilder builder = ProfileApiResponse.builder();
        builder.employeeId(input.getEmployeeId());
        builder.departmentName(input.getDepartmentName());
        builder.firstName(input.getFirstName());
        builder.lastName(input.getLastName());

        if(input.getPayroll() != null){
            builder.salary(input.getPayroll().getSalaryAmount());
        }
        return builder.build();
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
