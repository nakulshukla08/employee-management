package com.tech.employee_management.apigw.annotation;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Conditional(EnabledModuleCondition.class)
public @interface ConditionalController {
    String moduleName();
}
