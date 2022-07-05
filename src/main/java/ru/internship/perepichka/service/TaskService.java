package ru.internship.perepichka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.perepichka.dao.TaskRepository;
import ru.internship.perepichka.entity.Task;
import ru.internship.perepichka.exception.BadCommandException;
import ru.internship.perepichka.exception.BadIdException;
import ru.internship.perepichka.util.ParseData;

import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final EmployeeService employeeService;

    @Autowired
    public TaskService(TaskRepository taskRepository, EmployeeService employeeService) {
        this.taskRepository = taskRepository;
        this.employeeService = employeeService;
    }

    public String addTask(String args) {
        Task task = ParseData.parseTaskLine(new BadCommandException(""), args);
        task.setEmployee(employeeService.getReferenceById(task.getEmployee().getId()));
        taskRepository.save(task);
        return "Done!";
    }

    public String getTask(String args) {
        long id = ParseData.parseId(new BadCommandException(""), args);
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            return optionalTask.get().toString();
        } else {
            throw new BadIdException("No task with id " + id);
        }
    }

    public void updateTask(String args) {
        throw new UnsupportedOperationException();
    }

    public void deleteTask(String args) {
        throw new UnsupportedOperationException();
    }
}
