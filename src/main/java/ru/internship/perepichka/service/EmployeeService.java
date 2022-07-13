package ru.internship.perepichka.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.internship.perepichka.dao.EmployeeRepository;
import ru.internship.perepichka.entity.Employee;
import ru.internship.perepichka.entity.Task;
import ru.internship.perepichka.exception.BadIdException;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    List<Task> getEmployeeTasks(long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        if (optionalEmployee.isPresent()) {
            return optionalEmployee.get().getTasks();
        } else {
            throw new BadIdException("No user with id " + id);
        }
    }

    public void deleteUsers() {
        employeeRepository.deleteAll();
    }

    public Employee getReferenceById(long id) {
        return employeeRepository.getReferenceById(id);
    }

    public boolean existsById(long id) {
        return employeeRepository.existsById(id);
    }

}
