package ru.internship.perepichka.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.internship.perepichka.dao.EmployeeRepository;
import ru.internship.perepichka.dto.TaskFilters;
import ru.internship.perepichka.entity.Employee;
import ru.internship.perepichka.entity.Task;
import ru.internship.perepichka.exception.BadIdException;
import ru.internship.perepichka.specification.EmployeeSpecification;

import java.util.*;

@RequiredArgsConstructor
@Service
@Transactional
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    List<Task> getEmployeeTasks(String id) {
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

    public Employee getReferenceById(String id) {
        return employeeRepository.getReferenceById(id);
    }

    public boolean existsById(String id) {
        return employeeRepository.existsById(id);
    }

    public String getEmployeeWithMaxTasks(TaskFilters filters){
        List<Employee> employees = employeeRepository.findAll(EmployeeSpecification.getEmployeesWithFilteredTasks(filters));
        Map<String,Integer> counts = new HashMap<>();
        for(Employee employee : employees ) {
            counts.merge(employee.getId(), 1, Integer::sum);
        }
        return Collections.max(counts.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
    }

}
