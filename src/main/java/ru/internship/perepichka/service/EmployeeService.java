package ru.internship.perepichka.service;

import ru.internship.perepichka.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<Employee> getAllEmployees();

    Optional<Employee> getEmployeeById(String id);

    Employee createEmployee(Employee employee);

    Employee updateEmployee(String id, Employee employee);

    void deleteEmployee(String id);

}
