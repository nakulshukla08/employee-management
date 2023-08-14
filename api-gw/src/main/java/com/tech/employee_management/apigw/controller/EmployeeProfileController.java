package com.tech.employee_management.apigw.controller;


import com.tech.employee_management.apigw.annotation.ConditionalController;
import com.tech.employee_management.apigw.mapper.Mapper;
import com.tech.employee_management.apigw.model.CreateProfile;
import com.tech.employee_management.apigw.model.ProfileApiResponse;
import com.tech.employee_management.profile.api.ProfileApi;
import com.tech.employee_management.profile.api.ProfileRequest;
import com.tech.employee_management.profile.api.ProfileResponse;
import com.tech.employee_management.profile.domain.EmployeeProfile;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
@ConditionalController(moduleName = "employee-profile")
public class EmployeeProfileController {

    private ProfileApi profileApi;
    private Mapper<ProfileResponse, ProfileApiResponse> profileMapper;

    @GetMapping("/profile/{employeeId}")
    public ProfileApiResponse getEmployeeProfile(@PathVariable  String employeeId){
        return profileMapper.map(
                profileApi.getEmployeeProfile(ProfileRequest.builder()
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
