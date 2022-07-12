package ru.internship.perepichka.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.internship.perepichka.dao.EmployeeRepository;
import ru.internship.perepichka.entity.Employee;
import ru.internship.perepichka.entity.Task;
import ru.internship.perepichka.exception.BadCommandException;
import ru.internship.perepichka.exception.BadIdException;
import ru.internship.perepichka.util.DataParser;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public String getEmployeeTasksString(String args) {
        List<Task> tasks = getEmployeeTasks(args);
        StringBuilder builder = new StringBuilder();

        for (Task task : tasks) {
            builder.append(task.toString());
        }
        return builder.toString();
    }

    private List<Task> getEmployeeTasks(String args) {
        long id = DataParser.parseId(new BadCommandException(""), args);
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        if (optionalEmployee.isPresent()) {
            return optionalEmployee.get().getTasks();
        } else {
            throw new BadIdException("No user with id " + id);
        }
    }

    public String deleteUsers() {
        employeeRepository.deleteAll();
        return "All data was deleted successfully";
    }

    public Employee getReferenceById(long id) {
        return employeeRepository.getReferenceById(id);
    }

    public boolean existsById(long id) {
        return employeeRepository.existsById(id);
    }

}
