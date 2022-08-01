package ru.internship.perepichka.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.internship.perepichka.dao.EmployeeRepository;
import ru.internship.perepichka.entity.Employee;
import ru.internship.perepichka.exception.BadIdException;
import ru.internship.perepichka.service.EmployeeService;

import java.util.*;

@RequiredArgsConstructor
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    @Override
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(String id) {
        return repository.findById(id);
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return repository.save(employee);
    }

    @Override
    public Employee updateEmployee(String id, Employee newEmployee) {
        return repository.findById(id)
                .map(employee -> {
                    if (newEmployee.getName() != null) employee.setName(newEmployee.getName());
                    if (newEmployee.getEmail() != null) employee.setEmail(newEmployee.getEmail());
                    if (newEmployee.getPassword() != null) employee.setPassword(newEmployee.getPassword());
                    return repository.save(employee);
                })
                .orElseThrow(() -> new BadIdException("Employee with such id: " + id + "not found"));
    }

    @Override
    public void deleteEmployee(String id) {
        repository.deleteById(id);
    }

}
