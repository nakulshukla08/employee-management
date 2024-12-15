package com.tech.employee_management.payroll.config;

import com.tech.employee_management.api.payroll.PayrollApi;
import com.tech.employee_management.payroll.impl.EventConsumer;
import com.tech.employee_management.payroll.impl.PayrollApiImpl;
import com.tech.employee_management.payroll.repo.SalaryRepository;
import org.flywaydb.core.Flyway;
import org.springframework.boot.ApplicationRunner;
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

    @Bean
    public ApplicationRunner payrollFlywayRunner(PayrollDatabaseConfig.PayrollDataSourceProperties properties) {
        return args -> {
            String url = properties.getUrl();
            String username = properties.getUser();
            String password = properties.getPassword();

            System.out.println("Flyway loading");

            Flyway flyway = Flyway.configure()
                    .dataSource(url, username, password)
                    .locations("classpath:db/migration/payroll")
                    .load();

            flyway.migrate();
        };
    }



}
