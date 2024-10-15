package com.tech.employee_management.web.annotation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class EnabledModuleCondition implements Condition {


    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {


        log.info("Property : "+ System.getProperty("active.module"));
        String enabledModule = context.getEnvironment().getProperty("employee-management.api.enabled-module");
        log.info("Enabled modules : {}", enabledModule);
        if(enabledModule == null || enabledModule.isBlank()){
            return true;
        }
        MultiValueMap<String, Object> attrs = metadata.getAllAnnotationAttributes(FunctionalModule.class.getName());
        if (attrs != null) {
            for (Object value : attrs.get("name")) {
                String[] enabledModules = enabledModule.split(",");
                List<String> trimmedEnabledModules = Arrays.stream(enabledModules)
                        .map(String::trim)
                        .collect(Collectors.toList());
                if (trimmedEnabledModules.contains(value.toString().trim())) {
                    log.info("Enabling controller for module : {}", value);
                    return true;
                }
            }
            return false;
        }
        log.info("Disabling controller for module : {}", attrs);
        return false;
    }
}
