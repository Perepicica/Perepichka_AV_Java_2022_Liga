package ru.internship.perepichka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.perepichka.dao.TaskRepository;
import ru.internship.perepichka.entity.Task;
import ru.internship.perepichka.init.DataInit;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final EmployeeService employeeService;
    private final DataInit dataInit;

    @Autowired
    public TaskService(TaskRepository taskRepository, EmployeeService employeeService, DataInit dataInit) {
        this.taskRepository = taskRepository;
        this.employeeService = employeeService;
        this.dataInit = dataInit;
    }

    public List<Task> addTask(String args){
        taskRepository.save(dataInit.parseTaskLine(args,0));
        return employeeService.getEmployeeTasks(args.split(",")[3]);
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
