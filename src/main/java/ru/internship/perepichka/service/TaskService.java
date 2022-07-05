package ru.internship.perepichka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.perepichka.dao.TaskRepository;
import ru.internship.perepichka.entity.Task;
import ru.internship.perepichka.exception.BadCommandException;
import ru.internship.perepichka.exception.BadIdException;
import ru.internship.perepichka.util.ParseCommand;
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

        taskRepository.findById(task.getId()).ifPresent(v -> {
            throw new BadIdException("Task with id " + task.getId() + " already exists");
        });

        if (employeeService.findById(task.getEmployee().getId()).isEmpty()) {
            throw new BadIdException("No user with id " + task.getEmployee().getId());
        }

        task.setEmployee(employeeService.getReferenceById(task.getEmployee().getId()));
        taskRepository.save(task);
        return "Task was added successfully";
    }

    public String getTaskString(String args) {
        return getTask(args).toString();
    }

    public Task getTask(String args) {
        long id = ParseData.parseId(new BadCommandException(""), args);
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            return optionalTask.get();
        } else {
            throw new BadIdException("No task with id " + id);
        }
    }

    public String updateTask(String args) {
        String[] parts = ParseCommand.parseUpdateCommand(args);
        Task task = getTask(parts[0]);

        switch (parts[1]) {
            case "header" -> task.setHeader(parts[2]);
            case "description" -> task.setDescription(parts[2]);
            case "employeeId" -> updateEmployeeId(task, parts[2]);
            case "deadLine" -> task.setDeadline(ParseData.getDeadLine(new BadCommandException(""), parts[2], ""));
            case "status" -> task.setStatus(ParseData.getStatusType(new BadCommandException(""), parts[2], ""));
            default ->
                    throw new BadCommandException("Wrong field name, options for update: header, description,employeeId,deadLine, status");
        }

        taskRepository.save(task);
        return "Task was updated successfully";
    }

    public void updateEmployeeId(Task task, String strId) {
        long employeeId = ParseData.parseId(new BadCommandException(""), strId);
        if (employeeService.findById(employeeId).isEmpty()) {
            throw new BadIdException("No user with id " + employeeId);
        }
        task.setEmployee(employeeService.getReferenceById(employeeId));
    }

    public String deleteTask(String args) {
        long id = ParseData.parseId(new BadCommandException(""), args);
        if (taskRepository.findById(id).isEmpty()) {
            throw new BadIdException("No task with id " + id);
        }
        taskRepository.deleteById(id);
        return "Task was deleted successfully";
    }
}
