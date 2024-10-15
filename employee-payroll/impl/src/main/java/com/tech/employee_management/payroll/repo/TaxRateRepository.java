package com.tech.employee_management.payroll.repo;

import com.tech.employee_management.payroll.entities.TaxRate;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TaxRateRepository extends JpaRepository<TaxRate, Integer> {
}
