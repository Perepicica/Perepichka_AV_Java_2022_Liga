package ru.internship.perepichka.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.internship.perepichka.dao.TaskRepository;
import ru.internship.perepichka.entity.Employee;
import ru.internship.perepichka.entity.Task;
import ru.internship.perepichka.exception.BadIdException;
import ru.internship.perepichka.service.TaskService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;

    @Override
    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    @Override
    public Optional<Task> getTaskById(String id) {
        return repository.findById(id);
    }

    @Override
    public Task createTask(Task task) {
        return repository.save(task);
    }

    @Override
    public Task updateTask(String id, Task newTask) {
        return repository.findById(id)
                .map(task -> {
                    task.setHeader(newTask.getHeader());
                    task.setDescription(newTask.getDescription());
                    task.setStatus(newTask.getStatus());
                    task.setDeadline(newTask.getDeadline());
                    task.setEmployee(newTask.getEmployee());
                    task.setProject(newTask.getProject());
                    return repository.save(task);
                }).orElseThrow(() -> new BadIdException("Task with such id: " + id + "not found"));
    }

    @Override
    public void deleteTask(String id) {
        Optional<Task> optTask = getTaskById(id);
        if (optTask.isPresent()) {
            Employee employee = optTask.get().getEmployee();
            employee.getTasks().removeIf(task -> task.getId().equals(id));
        }
        repository.deleteById(id);
    }

    public Long getCountTasksByEmployeeId(String employeeId){
        Assert.notNull(employeeId, "The given id must not be null!");
        return repository
                .findAll()
                .stream()
                .filter(task -> {
                    return task.getEmployee().getId().equals(employeeId);
                })
                .count();
    }
}
