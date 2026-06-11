package com.roombooking.repository;

import com.roombooking.model.Employee;

import java.util.Optional;

public interface EmployeeRepository {

    Optional<Employee> findById(String employeeId);
}
