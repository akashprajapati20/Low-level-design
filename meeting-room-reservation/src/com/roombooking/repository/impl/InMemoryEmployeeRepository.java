package com.roombooking.repository.impl;

import com.roombooking.model.Employee;
import com.roombooking.repository.EmployeeRepository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryEmployeeRepository implements EmployeeRepository {

    private final Map<String, Employee> store = new ConcurrentHashMap<>();

    public InMemoryEmployeeRepository(Collection<Employee> employees) {
        for (Employee employee : employees) {
            store.put(employee.getId(), employee);
        }
    }

    @Override
    public Optional<Employee> findById(String employeeId) {
        return Optional.ofNullable(store.get(employeeId));
    }
}
