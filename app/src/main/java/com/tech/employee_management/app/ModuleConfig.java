package com.tech.employee_management.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class ModuleConfig {

    @Bean
    public Set<String> activeModules(){
        return new HashSet<>();
    }
}
