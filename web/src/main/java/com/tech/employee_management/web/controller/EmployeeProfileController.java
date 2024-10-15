package com.tech.employee_management.web.controller;


import com.tech.employee_management.api.profile.ProfileApi;
import com.tech.employee_management.api.profile.ProfileRequest;
import com.tech.employee_management.api.profile.ProfileResponse;
import com.tech.employee_management.web.annotation.FunctionalModule;
import com.tech.employee_management.web.mapper.Mapper;
import com.tech.employee_management.web.model.CreateProfile;
import com.tech.employee_management.web.model.ProfileApiResponse;

import com.tech.employee_management.domain.profile.EmployeeProfile;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@RestController
@AllArgsConstructor
@Slf4j
//@FunctionalModule(name = "employee-profile")
public class EmployeeProfileController {

    private ProfileApi profileApi;
    private Mapper<ProfileResponse, ProfileApiResponse> profileMapper;

    @GetMapping("/profile/{employeeId}")
    public ProfileApiResponse getEmployeeProfile(@PathVariable  String employeeId, @RequestParam(required = false) Boolean includePayroll){
        return profileMapper.map(
                profileApi.getEmployeeProfile(ProfileRequest.builder()
                                .includePayroll(includePayroll)
                                .profile(EmployeeProfile.builder()
                                        .employeeId(employeeId)
                                        .build())
                        .build()));
    }

    @PostMapping("/profile")
    public ResponseEntity<ProfileApiResponse> createEmployeeProfile(@RequestBody CreateProfile createProfile){

        log.info("Incoming request to create employee profile : {}",createProfile);
        profileApi.createEmployeeProfile(ProfileRequest.builder()
                        .profile(
                                EmployeeProfile.builder()
                                .departmentName(createProfile.getDepartmentName())
                                .firstName(createProfile.getFirstName())
                                .lastName(createProfile.getLastName())
                                .salary(createProfile.getSalary())
                                .build()
                        )
                .build());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
