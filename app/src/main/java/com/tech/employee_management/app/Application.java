package com.tech.employee_management.app;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.tech"})
public class Application {


    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }
}
