package ru.internship.perepichka.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.internship.perepichka.dao.TaskRepository;
import ru.internship.perepichka.entity.Task;
import ru.internship.perepichka.exception.BadCommandException;
import ru.internship.perepichka.exception.BadIdException;
import ru.internship.perepichka.dto.DataForTaskUpdate;
import ru.internship.perepichka.util.DataParser;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final EmployeeService employeeService;

    public void addTask(Task task) {

        if (!employeeService.existsById(task.getEmployee().getId())) {
            throw new BadIdException("No user with id " + task.getEmployee().getId());
        }

        task.setEmployee(employeeService.getReferenceById(task.getEmployee().getId()));
        taskRepository.save(task);
    }

    Task getTask(String id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            return optionalTask.get();
        } else {
            throw new BadIdException("No task with id " + id);
        }
    }

    public void updateTask(DataForTaskUpdate data) {
        Task task = getTask(data.getTaskId());

        switch (data.getFieldName()) {
            case "header" -> task.setHeader(data.getNewValue());
            case "description" -> task.setDescription(data.getNewValue());
            case "employeeId" -> updateEmployeeId(task, data.getNewValue());
            case "deadLine" ->
                    task.setDeadline(DataParser.getDeadLine(data.getNewValue()));
            case "status" ->
                    task.setStatus(DataParser.getStatusType(data.getNewValue()));
            default ->
                    throw new BadCommandException("Wrong field name, options for update: header, description,employeeId,deadLine, status");
        }

        taskRepository.save(task);
    }

    public void updateEmployeeId(Task task, String employeeId) {
        if (!employeeService.existsById(employeeId)) {
            throw new BadIdException("No user with id " + employeeId);
        }
        task.setEmployee(employeeService.getReferenceById(employeeId));
    }

    public void deleteTask(String id) {
        if (!taskRepository.existsById(id)) {
            throw new BadIdException("No task with id " + id);
        }
        taskRepository.deleteById(id);
    }
}
