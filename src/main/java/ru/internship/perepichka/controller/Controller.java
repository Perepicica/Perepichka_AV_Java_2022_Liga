package ru.internship.perepichka.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.internship.perepichka.exception.BadCommandException;
import ru.internship.perepichka.service.EmployeeService;
import ru.internship.perepichka.service.TaskService;


@RestController
@RequiredArgsConstructor
public class Controller {

    private final EmployeeService employeeService;
    private final TaskService taskService;

    @GetMapping("api/{command}")
    public String processCommand(@PathVariable String command) {
        String[] requestParts = command.split(":");
        if (requestParts.length != 2) {
            throw new BadCommandException("Follow the command pattern command:arg1,arg2...");
        }

        String args = requestParts[1];
        return switch (requestParts[0]) {
            case "getEmployeeTasks" -> employeeService.getEmployeeTasksString(args);
            case "addTask" -> taskService.addTask(args);
            case "getTask" -> taskService.getTaskString(args);
            case "updateTask" -> taskService.updateTask(args);
            case "deleteTask" -> taskService.deleteTask(args);
            case "deleteAll" -> employeeService.deleteUsers();
            default -> throw new BadCommandException("No such command");
        };
    }

}
