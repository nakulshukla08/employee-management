package com.tech.employee_management.web.config;

import com.tech.employee_management.api.payroll.GetPayrollResponse;
import com.tech.employee_management.api.profile.ProfileResponse;
import com.tech.employee_management.web.mapper.Mapper;
import com.tech.employee_management.web.mapper.PayrollMapper;
import com.tech.employee_management.web.mapper.ProfileMapper;
import com.tech.employee_management.web.model.PayrollApiResponse;
import com.tech.employee_management.web.model.ProfileApiResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApigwAutoConfig {

    @Bean
    public Mapper<GetPayrollResponse, PayrollApiResponse> payrollMapper(){
        return new PayrollMapper();
    }

    @Bean
    public Mapper<ProfileResponse, ProfileApiResponse> profileMapper(){
        return new ProfileMapper();
    }

}
