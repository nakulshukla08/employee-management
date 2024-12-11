package com.tech.employee_management.app;


import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

@SpringBootApplication(scanBasePackages = {"com.tech"})
@EnableTransactionManagement
@EnableConfigurationProperties
public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter(){
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(true);
        return hibernateJpaVendorAdapter;
    }

   // @Primary
    @Bean(name = "globalTransactionManager")
    public JtaTransactionManager globalTransactionManager () throws Throwable {
        JtaTransactionManager tm = new JtaTransactionManager();
        tm.setTransactionManager(userTransactionManager());
        tm.setUserTransaction(userTransactionManager());
        return tm;
    }

    @Bean
    public UserTransactionImp userTransactionImp() throws Throwable {
        UserTransactionImp userTransactionImp = new UserTransactionImp();
        userTransactionImp.setTransactionTimeout(60000); // Adjust timeout as needed
        return userTransactionImp;
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    public UserTransactionManager userTransactionManager() throws Throwable{
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.init();
        return userTransactionManager;
    }


}
