package com.tech.employee_management.payroll.repo;

import com.tech.employee_management.payroll.entities.TaxClass;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TaxClassRepository extends JpaRepository<TaxClass, Integer> {
}
