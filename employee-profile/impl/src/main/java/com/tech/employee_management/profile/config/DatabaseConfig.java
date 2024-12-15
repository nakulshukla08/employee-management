package com.tech.employee_management.profile.config;

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
@EntityScan("com.tech.employee_management.profile.entities")
@EnableJpaRepositories( basePackages ="com.tech.employee_management.profile.repo",
        entityManagerFactoryRef = "employeeProfileEntityManagerFactory",
        transactionManagerRef = "globalTransactionManager")
@Slf4j
public class DatabaseConfig {
    
    @Bean
    public DataSource employeeProfileDataSource(ProfileDataSourceProperties properties) {

        AtomikosDataSourceBean dataSource = new AtomikosDataSourceBean();
        dataSource.setUniqueResourceName("employee-profile");
        dataSource.setXaDataSourceClassName("org.postgresql.xa.PGXADataSource");
        Properties xaProperties = new Properties();
        xaProperties.setProperty("user", properties.getUser());
        xaProperties.setProperty("password", properties.getPassword());
        xaProperties.setProperty("url", properties.getUrl());
        dataSource.setXaProperties(xaProperties);

        return dataSource;
    }



    @Bean
    public LocalContainerEntityManagerFactoryBean employeeProfileEntityManagerFactory(@Qualifier("employeeProfileDataSource") DataSource employeeProfileDataSource,
                                                                                      JpaProperties employeeProfileJpaProperties, JpaVendorAdapter jpaVendorAdapter) {
        EntityManagerFactoryBuilder builder = createEntityManagerFactoryBuilder(employeeProfileJpaProperties, jpaVendorAdapter);
        return builder.
                dataSource(employeeProfileDataSource)
                .packages("com.tech.employee_management.profile.entities")
                .persistenceUnit("employeeProfile")
                .jta(true)
                .build();
    }

    private EntityManagerFactoryBuilder createEntityManagerFactoryBuilder(JpaProperties jpaProperties, JpaVendorAdapter jpaVendorAdapter) {
        return new EntityManagerFactoryBuilder(jpaVendorAdapter, jpaProperties.getProperties(), null);
    }

    @Bean

    public ProfileDataSourceProperties profileDataSourceProperties() {

        log.info("profile datasource properties being initialised");
        return new ProfileDataSourceProperties();
    }


    @Data
    @ConfigurationProperties(prefix = "profile.datasource")
   public static class ProfileDataSourceProperties {
        private String url;
        private String user;
        private String password;
    }
}
