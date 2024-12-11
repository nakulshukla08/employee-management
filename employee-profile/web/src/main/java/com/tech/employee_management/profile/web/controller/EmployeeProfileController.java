package com.tech.employee_management.profile.web.controller;


import com.tech.employee_management.api.profile.ProfileApi;
import com.tech.employee_management.api.profile.ProfileRequest;
import com.tech.employee_management.api.profile.ProfileResponse;
import com.tech.employee_management.domain.profile.EmployeeProfile;

import com.tech.employee_management.profile.web.mapper.Mapper;
import com.tech.employee_management.profile.web.model.CreateProfile;
import com.tech.employee_management.profile.web.model.ProfileApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
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
        ProfileResponse response = profileApi.createEmployeeProfile(ProfileRequest.builder()
                        .profile(
                                EmployeeProfile.builder()
                                .departmentName(createProfile.getDepartmentName())
                                .firstName(createProfile.getFirstName())
                                .lastName(createProfile.getLastName())
                                .salary(createProfile.getSalary())
                                .build()
                        )
                .build());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ProfileApiResponse
                                .builder()
                                .employeeId(response.getEmployeeId())
                                .firstName(response.getFirstName())
                                .lastName(response.getLastName())
                                .departmentName(response.getDepartmentName())
                                .build()
                );
    }
}
