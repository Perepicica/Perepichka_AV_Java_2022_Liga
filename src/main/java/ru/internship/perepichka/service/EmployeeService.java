package ru.internship.perepichka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.perepichka.dao.EmployeeRepository;
import ru.internship.perepichka.entity.Employee;
import ru.internship.perepichka.entity.Task;
import ru.internship.perepichka.exception.BadCommandException;
import ru.internship.perepichka.exception.BadIdException;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    public List<Task> getEmployeeTasks(String args) {
        long id;
        try {
            id = Long.parseLong(args);
        } catch (NumberFormatException ex) {
            throw new BadCommandException("User id should be long type");
        }

        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            return optionalEmployee.get().getTasks();
        } else {
            throw new BadIdException("No user with id " + id);
        }
    }

    public void deleteUsers() {
        throw new UnsupportedOperationException();
    }

}
