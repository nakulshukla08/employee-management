package com.tech.employee_management.profile.config;

import com.tech.employee_management.api.payroll.PayrollApi;
import com.tech.employee_management.api.profile.ProfileApi;
import com.tech.employee_management.events.profile.ProfileEvent;
import com.tech.employee_management.profile.impl.ProfileApiImpl;
import com.tech.employee_management.profile.outbound.async.AsyncGateway;
import com.tech.employee_management.profile.outbound.async.ProfileHttpAsyncGateway;
import com.tech.employee_management.profile.outbound.async.ProfileSpringEventsGateway;
import com.tech.employee_management.profile.outbound.sync.PayrollApiHttpImpl;
import com.tech.employee_management.profile.repo.DepartmentRepository;
import com.tech.employee_management.profile.repo.EmployeeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Configuration
public class ProfileConfig {

    @Value("${employee-management.api.enabled-module}")
    private List<String> enabledModules;

    @Value("${payroll.service.url}")
    private String payrollServiceURL;


    @Bean
    public ProfileApi profileApi(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, AsyncGateway<ProfileEvent> profileEventPublisher, PayrollApi payrollApi){

        return new ProfileApiImpl(employeeRepository, departmentRepository, profileEventPublisher, payrollApi);
    }

    @Bean
    @ConditionalOnProperty(name = "deployment.mode", havingValue = "micro")
    public PayrollApi payrollApi(WebClient.Builder webClientBuilder) {
        return new PayrollApiHttpImpl(webClientBuilder, payrollServiceURL);
    }

    @Bean
    public AsyncGateway<ProfileEvent> profileEventPublisher(ApplicationEventPublisher eventPublisher, PayrollApi payrollApi){
        if(payrollApi instanceof PayrollApiHttpImpl){
            return new ProfileHttpAsyncGateway(payrollApi);
        }
        return new ProfileSpringEventsGateway(eventPublisher);
    }

}
