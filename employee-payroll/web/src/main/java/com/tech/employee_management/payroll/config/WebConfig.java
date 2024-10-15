package com.tech.employee_management.payroll.config;

import com.tech.employee_management.api.payroll.GetPayrollResponse;
import com.tech.employee_management.payroll.web.mapper.Mapper;
import com.tech.employee_management.payroll.web.mapper.PayrollMapper;
import com.tech.employee_management.payroll.web.model.PayrollApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class WebConfig {
    @Bean
    public Mapper<GetPayrollResponse, PayrollApiResponse> payrollMapper(){
        log.info("Initialise mapper");
        return new PayrollMapper();
    }
}