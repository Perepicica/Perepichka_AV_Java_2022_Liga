package ru.internship.perepichka.service;


import ru.internship.perepichka.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> getAllTasks();

    Optional<Task> getTaskById(String id);

    Task createTask(Task task);

    Task updateTask(String id, Task task);

    void deleteTask(String id);

}
