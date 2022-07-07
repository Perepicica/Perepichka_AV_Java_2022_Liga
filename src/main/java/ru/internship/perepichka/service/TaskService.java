package ru.internship.perepichka.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.internship.perepichka.dao.TaskRepository;
import ru.internship.perepichka.entity.Task;
import ru.internship.perepichka.exception.BadCommandException;
import ru.internship.perepichka.exception.BadIdException;
import ru.internship.perepichka.util.DataForTaskUpdate;
import ru.internship.perepichka.util.CommandParser;
import ru.internship.perepichka.util.DataParser;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final EmployeeService employeeService;

    public String addTask(String args) {
        Task task = DataParser.parseTaskLine(new BadCommandException(""), args);

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
        long id = DataParser.parseId(new BadCommandException(""), args);
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            return optionalTask.get();
        } else {
            throw new BadIdException("No task with id " + id);
        }
    }

    public String updateTask(String args) {
        DataForTaskUpdate data = CommandParser.parseUpdateCommand(args);
        Task task = getTask(data.getTaskId());

        switch (data.getFieldName()) {
            case "header" -> task.setHeader(data.getNewValue());
            case "description" -> task.setDescription(data.getNewValue());
            case "employeeId" -> updateEmployeeId(task, data.getNewValue());
            case "deadLine" -> task.setDeadline(DataParser.getDeadLine(new BadCommandException(""), data.getNewValue(), ""));
            case "status" -> task.setStatus(DataParser.getStatusType(new BadCommandException(""), data.getNewValue(), ""));
            default ->
                    throw new BadCommandException("Wrong field name, options for update: header, description,employeeId,deadLine, status");
        }

        taskRepository.save(task);
        return "Task was updated successfully";
    }

    public void updateEmployeeId(Task task, String strId) {
        long employeeId = DataParser.parseId(new BadCommandException(""), strId);
        if (employeeService.findById(employeeId).isEmpty()) {
            throw new BadIdException("No user with id " + employeeId);
        }
        task.setEmployee(employeeService.getReferenceById(employeeId));
    }

    public String deleteTask(String args) {
        long id = DataParser.parseId(new BadCommandException(""), args);
        if (taskRepository.findById(id).isEmpty()) {
            throw new BadIdException("No task with id " + id);
        }
        taskRepository.deleteById(id);
        return "Task was deleted successfully";
    }
}
