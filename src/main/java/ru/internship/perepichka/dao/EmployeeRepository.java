package ru.internship.perepichka.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.internship.perepichka.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
}

