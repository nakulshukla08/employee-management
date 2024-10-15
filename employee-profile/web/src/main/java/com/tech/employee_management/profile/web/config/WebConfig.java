package com.tech.employee_management.profile.web.config;

import com.tech.employee_management.api.profile.ProfileResponse;
import com.tech.employee_management.profile.web.mapper.Mapper;
import com.tech.employee_management.profile.web.mapper.ProfileMapper;
import com.tech.employee_management.profile.web.model.ProfileApiResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("profileWebConfig")
public class WebConfig {
    @Bean
    public Mapper<ProfileResponse, ProfileApiResponse> profileMapperNew(){
        return new ProfileMapper();
    }

}