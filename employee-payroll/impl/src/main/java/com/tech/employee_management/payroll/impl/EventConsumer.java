package com.tech.employee_management.payroll.impl;

import com.tech.employee_management.api.payroll.PayrollApi;
import com.tech.employee_management.api.payroll.CreatePayrollRequest;
import com.tech.employee_management.events.profile.EmployeeState;
import com.tech.employee_management.events.profile.ProfileEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;

@AllArgsConstructor
@Slf4j
public class EventConsumer {

    private PayrollApi payrollApi;

    @EventListener
    public void onProfileEvent(ProfileEvent profileEvent) {
        log.info("Received profile event : {}" , profileEvent);
        EmployeeState employeeState = profileEvent.getEmployeeState();
        CreatePayrollRequest payrollRequest = CreatePayrollRequest.builder()
                .employeeId(employeeState.getEmployeeId())
                .grossSalary(employeeState.getSalary())
                .build();
        payrollApi.createPayroll(payrollRequest);
    }
}
