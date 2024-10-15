package com.tech.employee_management.profile.impl;

import com.tech.employee_management.api.payroll.GetPayrollRequest;
import com.tech.employee_management.api.payroll.GetPayrollResponse;
import com.tech.employee_management.api.payroll.PayrollApi;
import com.tech.employee_management.api.profile.ProfileApi;
import com.tech.employee_management.api.profile.ProfileRequest;
import com.tech.employee_management.api.profile.ProfileResponse;
import com.tech.employee_management.domain.payroll.Payroll;
import com.tech.employee_management.domain.profile.EmployeeProfile;
import com.tech.employee_management.events.profile.EmployeeState;
import com.tech.employee_management.events.profile.ProfileEvent;
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
    private PayrollApi payrollApi;

    @Override
    public ProfileResponse getEmployeeProfile(ProfileRequest request) {
        String employeeId = request.getProfile().getEmployeeId();
        Optional<Employee> employeeOpt = employeeRepository.findById(Integer.parseInt(employeeId));
        if(employeeOpt.isEmpty()){
            throw new EntityNotFoundException(String.format("Employee not found with id : %s", employeeId));
        }
        Employee employee = employeeOpt.get();
        Payroll payroll = getPayroll(request);
        return ProfileResponse.builder()
                .employeeId(String.valueOf(employee.getEmployeeId()))
                .departmentName(employee.getDepartment().getDepartmentName())
                .firstName(employee.getLastName())
                .lastName(employee.getLastName())
                .payroll(payroll)
                .build();
    }

    private Payroll getPayroll(ProfileRequest request) {
        Payroll payroll = null;
        if(request.isIncludePayroll()){
            GetPayrollRequest payrollRequest = GetPayrollRequest.builder()
                                                .employeeId(request.getProfile()
                                                        .getEmployeeId())
                                                .build();
            GetPayrollResponse payrollResponse = payrollApi.getPayroll(payrollRequest);
            payroll = payrollResponse.getPayroll();
        }
        return payroll;
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