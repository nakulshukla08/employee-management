package com.tech.employee_management.profile.repo;

import com.tech.employee_management.profile.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AddressRepository extends JpaRepository<Address, Integer> {
}
