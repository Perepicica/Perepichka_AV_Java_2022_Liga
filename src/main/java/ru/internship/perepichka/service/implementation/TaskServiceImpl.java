package ru.internship.perepichka.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final EmployeeServiceImpl employeeServiceImpl;

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
                    if (newTask.getHeader() != null) task.setHeader(newTask.getHeader());
                    if (newTask.getDescription() != null) task.setDescription(newTask.getDescription());
                    if (newTask.getStatus() != null) task.setStatus(newTask.getStatus());
                    if (newTask.getDeadline() != null) task.setDeadline(newTask.getDeadline());
                    if (newTask.getEmployee() != null) task.setEmployee(newTask.getEmployee());
                    if (newTask.getProject() != null) task.setProject(newTask.getProject());
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
}
