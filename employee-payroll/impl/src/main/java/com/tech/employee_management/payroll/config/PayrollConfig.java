package com.tech.employee_management.payroll.config;

import com.tech.employee_management.api.payroll.PayrollApi;
import com.tech.employee_management.payroll.impl.EventConsumer;
import com.tech.employee_management.payroll.impl.PayrollApiImpl;
import com.tech.employee_management.payroll.repo.SalaryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PayrollConfig {

    @Bean
    public PayrollApi payrollApi(SalaryRepository salaryRepository){
        return new PayrollApiImpl(salaryRepository);
    }

    @Bean
    public EventConsumer eventConsumer(PayrollApi payrollApi){
        return new EventConsumer(payrollApi);
    }


}
