package com.tech.employee_management.payroll.config;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EntityScan("com.tech.employee_management.payroll.entities")
@EnableJpaRepositories(basePackages ="com.tech.employee_management.payroll.repo",
        entityManagerFactoryRef = "employeePayrollEntityManagerFactory",
        transactionManagerRef = "globalTransactionManager")
@Slf4j
public class PayrollDatabaseConfig {

    @Bean
    public DataSource employeePayrollDataSource(PayrollDataSourceProperties properties) {

        AtomikosDataSourceBean dataSource = new AtomikosDataSourceBean();
        dataSource.setUniqueResourceName("employee-payroll");
        dataSource.setXaDataSourceClassName("org.postgresql.xa.PGXADataSource");
        Properties xaProperties = new Properties();
        xaProperties.setProperty("user", properties.getUser());
        xaProperties.setProperty("password", properties.getPassword());
        xaProperties.setProperty("url", properties.getUrl());
        dataSource.setXaProperties(xaProperties);

        return dataSource;
    }



    @Bean
    public LocalContainerEntityManagerFactoryBean employeePayrollEntityManagerFactory(@Qualifier("employeePayrollDataSource") DataSource employeePayrollDataSource,
                                                                                      JpaProperties employeePayrollJpaProperties, JpaVendorAdapter jpaVendorAdapter) {
        EntityManagerFactoryBuilder builder = createEntityManagerFactoryBuilder(employeePayrollJpaProperties, jpaVendorAdapter);
        return builder.
                dataSource(employeePayrollDataSource)
                .packages("com.tech.employee_management.payroll.entities")
                .persistenceUnit("employeePayroll")
                .jta(true)
                .build();
    }

    private EntityManagerFactoryBuilder createEntityManagerFactoryBuilder(JpaProperties jpaProperties, JpaVendorAdapter jpaVendorAdapter) {
        return new EntityManagerFactoryBuilder(jpaVendorAdapter, jpaProperties.getProperties(), null);
    }

    @Bean
    @ConfigurationProperties(prefix = "payroll.datasource")
    public PayrollDataSourceProperties payrollDataSourceProperties() {

        log.info("payroll datasource properties being initialised");
        return new PayrollDataSourceProperties();
    }


    @Data
    public static class PayrollDataSourceProperties {
        private String url;
        private String user;
        private String password;
    }
}
