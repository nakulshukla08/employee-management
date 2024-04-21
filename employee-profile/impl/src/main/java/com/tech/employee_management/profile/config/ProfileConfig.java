package com.tech.employee_management.profile.config;

import com.tech.employee_management.api.payroll.PayrollApi;
import com.tech.employee_management.api.profile.ProfileApi;
import com.tech.employee_management.events.profile.ProfileEvent;
import com.tech.employee_management.profile.impl.ProfileApiImpl;
import com.tech.employee_management.profile.outbound.async.AsyncGateway;
import com.tech.employee_management.profile.outbound.async.ProfileSpringEventsGateway;
import com.tech.employee_management.profile.repo.DepartmentRepository;
import com.tech.employee_management.profile.repo.EmployeeRepository;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EntityScan("com.tech.employee_management.profile.entities")
@EnableJpaRepositories( basePackages ="com.tech.employee_management.profile.repo",
        entityManagerFactoryRef = "employeeProfileEntityManagerFactory",
        transactionManagerRef = "employeeProfileTransactionManager")
@EnableTransactionManagement
public class ProfileConfig {

    @Bean
    public ProfileApi profileApi(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, AsyncGateway<ProfileEvent> profileEventPublisher, PayrollApi payrollApi){
        System.out.println("creating profileApiIMPl");
        return new ProfileApiImpl(employeeRepository, departmentRepository, profileEventPublisher, payrollApi);
    }

    @Bean
    public AsyncGateway<ProfileEvent> profileEventPublisher(ApplicationEventPublisher eventPublisher){
        return new ProfileSpringEventsGateway(eventPublisher);
    }

    @Bean
    @Primary
    //@ConfigurationProperties("spring.datasource.employee-profile")
    public DataSourceProperties employeeProfileDataSourceProperties() {
        DataSourceProperties properties = new DataSourceProperties();
        properties.setUrl("jdbc:postgresql://localhost:5432/postgres?currentSchema=employee_profile");
        properties.setDriverClassName("org.postgresql.Driver");
        properties.setName("employee-profile");
        return properties;
    }

    @Bean
    @Primary
    //@ConfigurationProperties("spring.datasource.employee-profile.configuration")
    public DataSource employeeProfileDataSource(@Qualifier("employeeProfileDataSourceProperties") DataSourceProperties employeeProfileDataSourceProperties) {
        return employeeProfileDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean employeeProfileEntityManagerFactory(@Qualifier("employeeProfileDataSource") DataSource employeeProfileDataSource,
                                                                                      JpaProperties employeeProfileJpaProperties) {
        EntityManagerFactoryBuilder builder = createEntityManagerFactoryBuilder(employeeProfileJpaProperties);
        return builder.
                dataSource(employeeProfileDataSource)
                .packages("com.tech.employee_management.profile.entities")
                .persistenceUnit("employeeProfile")
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

    @Bean(name = "employeeProfileTransactionManager")
    public PlatformTransactionManager primaryTransactionManager(
            @Qualifier("employeeProfileEntityManagerFactory") EntityManagerFactory employeeProfileEntityManagerFactory) {

        return new JpaTransactionManager(employeeProfileEntityManagerFactory);
    }

}
