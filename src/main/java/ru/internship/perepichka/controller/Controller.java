package ru.internship.perepichka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.internship.perepichka.exception.BadCommandException;
import ru.internship.perepichka.service.EmployeeService;
import ru.internship.perepichka.service.TaskService;


@RestController
public class Controller {

    private final EmployeeService employeeService;
    private final TaskService taskService;

    @Autowired
    public Controller(EmployeeService employeeService, TaskService taskService) {
        this.employeeService = employeeService;
        this.taskService = taskService;
    }

    @GetMapping("api/{command}")
    public String processCommand(@PathVariable String command) {
        String[] requestParts = command.split(":");
        if (requestParts.length != 2) {
            throw new BadCommandException("Follow the command pattern command:arg1,arg2...");
        }

        String args = requestParts[1];
        switch (requestParts[0]) {
            case "getEmployeeTasks" : return employeeService.getEmployeeTasksString(args);
            case "addTask" : return taskService.addTask(args);
            case "getTask" : return taskService.getTaskString(args);
            case "updateTask" : return taskService.updateTask(args);
            case "deleteTask" : return taskService.deleteTask(args);
            case "deleteAll" : employeeService.deleteUsers();
            default : throw new BadCommandException("No such command");
        }
    }

}
