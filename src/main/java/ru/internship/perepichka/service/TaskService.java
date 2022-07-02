package ru.internship.perepichka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.perepichka.dao.TaskRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void addTask(String args){
        throw new UnsupportedOperationException();
    }

    public void getTask(String args){
        throw new UnsupportedOperationException();
    }

    public void updateTask(String args){
        throw new UnsupportedOperationException();
    }

    public void deleteTask(String args){
        throw new UnsupportedOperationException();
    }
}
