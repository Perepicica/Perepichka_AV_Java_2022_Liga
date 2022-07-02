package ru.internship.perepichka.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.internship.perepichka.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}

