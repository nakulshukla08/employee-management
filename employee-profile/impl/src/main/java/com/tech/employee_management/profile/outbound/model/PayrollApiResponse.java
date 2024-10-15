package com.tech.employee_management.profile.outbound.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

@Data
@JsonDeserialize
public class PayrollApiResponse {

    private String employeeId;

    private String salary;
}
