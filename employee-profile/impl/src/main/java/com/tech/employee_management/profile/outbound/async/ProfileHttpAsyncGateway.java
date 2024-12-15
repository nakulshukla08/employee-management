package com.tech.employee_management.profile.outbound.async;

import com.tech.employee_management.api.payroll.CreatePayrollRequest;
import com.tech.employee_management.api.payroll.PayrollApi;
import com.tech.employee_management.events.profile.EmployeeState;
import com.tech.employee_management.events.profile.ProfileEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

@AllArgsConstructor
@Slf4j
public class ProfileHttpAsyncGateway implements AsyncGateway<ProfileEvent>{

    private final PayrollApi payrollApi;

    @Override
    @Async
    public void publish(ProfileEvent event) {

        log.info("Publishing employeeProfile event over HTTP: {}", event);
        EmployeeState state = event.getEmployeeState();
        payrollApi.createPayroll(CreatePayrollRequest.builder().employeeId(state.getEmployeeId())
                .grossSalary(state.getSalary())
                .build());
    }
}
