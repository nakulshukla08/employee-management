package com.tech.employee_management.payroll.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
@Setter
@ToString
public class Salary {


    @Id
    @Column(nullable = false, updatable = false, name = "salary_id")
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "primary_sequence")
    private Integer salaryId;

    @Column(nullable = false, updatable = false, name = "employee_id")
    private Integer employeeId;

    @Column(precision = 12, scale = 2, name = "gross_salary")
    private BigDecimal grossSalary;

    @Column(precision = 12, scale = 2, name = "net_salary")
    private BigDecimal netSalary;

}
