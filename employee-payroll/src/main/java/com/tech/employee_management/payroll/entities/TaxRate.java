package com.tech.employee_management.payroll.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class TaxRate {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Integer taxRateId;

    @Column(precision = 12, scale = 2)
    private BigDecimal lowerLimit;

    @Column(precision = 12, scale = 2)
    private BigDecimal upperLimit;

    @Column(precision = 7, scale = 2)
    private BigDecimal rate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tax_class_id")
    private TaxClass taxClass;

}
