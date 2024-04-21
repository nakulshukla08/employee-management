package com.tech.employee_management.payroll.config;

import com.tech.employee_management.api.payroll.PayrollApi;
import com.tech.employee_management.payroll.impl.EventConsumer;
import com.tech.employee_management.payroll.impl.PayrollApiImpl;
import com.tech.employee_management.payroll.repo.SalaryRepository;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration(proxyBeanMethods = false)
@EntityScan("com.tech.employee_management.payroll.entities")
@EnableJpaRepositories(basePackages ="com.tech.employee_management.payroll.repo",
        entityManagerFactoryRef = "employeePayrollEntityManagerFactory",
        transactionManagerRef = "employeePayrollTransactionManager")
@EnableTransactionManagement
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
    //@ConfigurationProperties("spring.datasource.employee-payroll")
    public DataSourceProperties employeePayrollDataSourceProperties() {
        System.out.println("DS properties init");
        DataSourceProperties properties = new DataSourceProperties();
        properties.setUrl("jdbc:postgresql://localhost:5432/postgres?currentSchema=employee_payroll");
        properties.setDriverClassName("org.postgresql.Driver");
        properties.setName("employee-payroll");
        return properties;
    }

    @Bean
    //@ConfigurationProperties("spring.datasource.employee-payroll.configuration")
    public DataSource employeePayrollDataSource(@Qualifier("employeePayrollDataSourceProperties") DataSourceProperties employeePayrollDataSourceProperties) {
        return employeePayrollDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean employeePayrollEntityManagerFactory(@Qualifier("employeePayrollDataSource") DataSource employeePayrollDataSource,
                                                                            JpaProperties employeePayrollJpaProperties) {
        EntityManagerFactoryBuilder builder = createEntityManagerFactoryBuilder(employeePayrollJpaProperties);
        return builder.
                dataSource(employeePayrollDataSource)
                .packages("com.tech.employee_management.payroll.entities")
                .persistenceUnit("employeePayroll")
                    .build();
    }

    private EntityManagerFactoryBuilder createEntityManagerFactoryBuilder(JpaProperties jpaProperties) {
        JpaVendorAdapter jpaVendorAdapter = createJpaVendorAdapter(jpaProperties);
        return new EntityManagerFactoryBuilder(jpaVendorAdapter, jpaProperties.getProperties(), null);
    }

    private JpaVendorAdapter createJpaVendorAdapter(JpaProperties jpaProperties) {
        // ... map JPA properties as needed
        return new HibernateJpaVendorAdapter();
    }

    @Bean(name = "employeePayrollTransactionManager")
    public PlatformTransactionManager employeePayrollTransactionManager(
            @Qualifier("employeePayrollEntityManagerFactory") EntityManagerFactory employeePayrollEntityManagerFactory) {

        return new JpaTransactionManager(employeePayrollEntityManagerFactory);
    }
}
