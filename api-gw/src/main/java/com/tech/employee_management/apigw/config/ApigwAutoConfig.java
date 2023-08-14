package com.tech.employee_management.apigw.config;

import com.tech.employee_management.apigw.mapper.Mapper;
import com.tech.employee_management.apigw.mapper.PayrollMapper;
import com.tech.employee_management.apigw.mapper.ProfileMapper;
import com.tech.employee_management.apigw.model.PayrollApiResponse;
import com.tech.employee_management.apigw.model.ProfileApiResponse;
import com.tech.employee_management.payroll.domain.PayrollResponse;
import com.tech.employee_management.profile.api.ProfileResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApigwAutoConfig {

    @Bean
    public Mapper<PayrollResponse, PayrollApiResponse> payrollMapper(){
        return new PayrollMapper();
    }

    @Bean
    public Mapper<ProfileResponse, ProfileApiResponse> profileMapper(){
        return new ProfileMapper();
    }

}
