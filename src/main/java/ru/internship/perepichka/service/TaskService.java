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

    private static final String DONE = "Done!";

    @Autowired
    public TaskService(TaskRepository taskRepository, EmployeeService employeeService) {
        this.taskRepository = taskRepository;
        this.employeeService = employeeService;
    }

    public String addTask(String args) {
        Task task = ParseData.parseTaskLine(new BadCommandException(""), args);
        task.setEmployee(employeeService.getReferenceById(task.getEmployee().getId()));
        taskRepository.save(task);
        return DONE;
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

    public String getTaskString(String args) {
        return getTask(args).toString();
    }

    public String updateTask(String args) {
        String[] parts = ParseCommand.parseUpdateCommand(args);
        Task task = getTask(parts[0]);

        switch (parts[1]) {
            case "header" -> task.setHeader(parts[2]);
            case "description" -> task.setDescription(parts[2]);
            case "employeeId" -> task.setEmployee(employeeService.getReferenceById(ParseData.parseId(new BadCommandException(""),parts[2])));
            case "deadLine" -> task.setDeadline(ParseData.getDeadLine(new BadCommandException(""),parts[2],""));
            case "status" -> task.setStatus(ParseData.getStatusType(new BadCommandException(""),parts[2],""));
            default ->
                    throw new BadCommandException("Wrong field name, options for update: header, description,employeeId,deadLine, status");
        }

        taskRepository.save(task);
        return DONE;
    }

    public String deleteTask(String args) {
        long id = ParseData.parseId(new BadCommandException(""), args);
        taskRepository.deleteById(id);
        return DONE;
    }
}
