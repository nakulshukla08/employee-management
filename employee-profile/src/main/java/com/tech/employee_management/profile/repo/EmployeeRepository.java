package com.tech.employee_management.profile.repo;

import com.tech.employee_management.profile.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
