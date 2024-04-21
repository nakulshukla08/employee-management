package com.tech.employee_management.payroll.repo;

import com.tech.employee_management.payroll.entities.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface SalaryRepository extends JpaRepository<Salary, Integer> {

    Optional<Salary> findByEmployeeId(Integer employeeId);
}
