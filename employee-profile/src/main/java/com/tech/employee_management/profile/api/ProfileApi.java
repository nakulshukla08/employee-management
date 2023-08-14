package com.tech.employee_management.profile.api;

public interface ProfileApi {

    ProfileResponse getEmployeeProfile(ProfileRequest request);

    void createEmployeeProfile(ProfileRequest request);
}
