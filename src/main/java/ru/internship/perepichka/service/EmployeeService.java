package ru.internship.perepichka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.perepichka.dao.EmployeeRepository;
import ru.internship.perepichka.entity.Employee;
import ru.internship.perepichka.entity.Task;
import ru.internship.perepichka.exception.BadCommandException;
import ru.internship.perepichka.exception.BadIdException;
import ru.internship.perepichka.util.ParseData;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public String getEmployeeTasksString(String args) {
        List<Task> tasks = getEmployeeTasks(args);
        StringBuilder builder = new StringBuilder();
        for (Task task : tasks){
            builder.append(task.toString());
        }
        return builder.toString();
    }

    public List<Task> getEmployeeTasks(String args) {
        long id = ParseData.parseId(new BadCommandException(""), args);
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

    public Employee getReferenceById(long id) {
        return employeeRepository.getReferenceById(id);
    }

}
