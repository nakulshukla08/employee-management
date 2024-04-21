package com.tech.employee_management.web.annotation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;

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
        MultiValueMap<String, Object> attrs = metadata.getAllAnnotationAttributes(ConditionalController.class.getName());
        if (attrs != null) {
            for (Object value : attrs.get("moduleName")) {
                String[] enabledModules = enabledModule.split(",");
                if (Arrays.asList(enabledModules).contains(value)) {
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
