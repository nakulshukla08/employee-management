package com.tech.employee_management.profile.impl;

import com.tech.employee_management.events.profile.EmployeeState;
import com.tech.employee_management.events.profile.ProfileEvent;
import com.tech.employee_management.profile.api.ProfileApi;
import com.tech.employee_management.profile.api.ProfileRequest;
import com.tech.employee_management.profile.api.ProfileResponse;
import com.tech.employee_management.profile.domain.EmployeeProfile;
import com.tech.employee_management.profile.entities.Department;
import com.tech.employee_management.profile.entities.Employee;
import com.tech.employee_management.profile.outbound.async.AsyncGateway;
import com.tech.employee_management.profile.repo.DepartmentRepository;
import com.tech.employee_management.profile.repo.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class ProfileApiImpl implements ProfileApi {

    private EmployeeRepository employeeRepository;
    private DepartmentRepository departmentRepository;
    private AsyncGateway<ProfileEvent> profileEventAsyncGateway;

    @Override
    public ProfileResponse getEmployeeProfile(ProfileRequest request) {
        String employeeId = request.getProfile().getEmployeeId();
        Optional<Employee> employeeOpt = employeeRepository.findById(Integer.parseInt(employeeId));
        if(employeeOpt.isEmpty()){
            throw new EntityNotFoundException(String.format("Employee not found with id : %s", employeeId));
        }
        Employee employee =employeeOpt.get();
        return new ProfileResponse(String.valueOf(employee.getEmployeeId()), employee.getFirstName(), employee.getLastName(), employee.getDepartment().getDepartmentName());
    }

    @Override
    public void createEmployeeProfile(ProfileRequest request) {

        Employee employee = employeeRepository.save(mapEmployee(request));
        ProfileEvent profileEvent = mapProfileRequestToEvent(employee, request);
        profileEventAsyncGateway.publish(profileEvent);
    }

    private ProfileEvent mapProfileRequestToEvent(Employee employee, ProfileRequest request) {
        return ProfileEvent.builder()
                .employeeState(
                        EmployeeState.builder()
                                .employeeId(String.valueOf(employee.getEmployeeId()))
                                .firstName(employee.getFirstName())
                                .lastName(employee.getLastName())
                                .departmentName(employee.getDepartment().getDepartmentName())
                                .salary(request.getProfile().getSalary())
                                .build()
                )
                .build();
    }

    private Employee mapEmployee(ProfileRequest request){
        EmployeeProfile createProfileRequest = request.getProfile();
        String departmentName = createProfileRequest.getDepartmentName();

        Optional<Department> departmentOpt =  departmentRepository.findByDepartmentName(departmentName);
        if(departmentOpt.isEmpty()){
            throw new EntityNotFoundException(String.format("Department not found with name : %s", departmentName));
        }
        Department department = departmentOpt.get();
        department.setDepartmentName(createProfileRequest.getDepartmentName());
        Employee employee = new Employee();
        employee.setDepartment(department);
        employee.setFirstName(createProfileRequest.getFirstName());
        employee.setLastName(createProfileRequest.getLastName());
        return employee;


    }

}