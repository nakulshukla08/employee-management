package com.tech.employee_management.api.profile;

public interface ProfileApi {

    ProfileResponse getEmployeeProfile(ProfileRequest request);

    void createEmployeeProfile(ProfileRequest request);
}
